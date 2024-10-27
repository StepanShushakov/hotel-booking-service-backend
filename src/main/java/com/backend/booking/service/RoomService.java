package com.backend.booking.service;

import com.backend.booking.mapper.RoomMapper;
import com.backend.booking.model.dto.request.RoomCreateOrUpdateDto;
import com.backend.booking.model.dto.response.RoomDetailsDto;
import com.backend.booking.model.dto.response.RoomDto;
import com.backend.booking.model.entity.Room;
import com.backend.booking.model.entity.UnavailableDates;
import com.backend.booking.model.filter.RoomFilter;
import com.backend.booking.repository.RoomRepository;
import com.backend.booking.repository.RoomSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    private Room findRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

    private void filterUnavailableDatesAfterNow(Room room) {
        // Фильтруем недоступные даты, чтобы вернуть только те, которые начинаются с текущей даты
        List<UnavailableDates> filteredDates = new ArrayList<>();
        List<UnavailableDates> roomUnavailableDates = room.getUnavailableDates();
        if (roomUnavailableDates != null) {
            filteredDates = roomUnavailableDates.stream()
                    .filter(dates -> !dates.getUnavailableTo().isBefore(LocalDate.now())) // Убираем прошедшие даты
                    .toList();
        }
        room.setUnavailableDates(filteredDates);
    }

    public RoomDetailsDto createRoom(RoomCreateOrUpdateDto roomDto) {
        Room room = roomMapper.toRoom(roomDto);
        room = roomRepository.save(room);
        filterUnavailableDatesAfterNow(room);
        return roomMapper.toRoomDetailsDto(room);
    }

    public RoomDetailsDto updateRoom(Long id, RoomCreateOrUpdateDto roomDto) {
        Room existingRoom = findRoomById(id);
        roomMapper.updateRoomFromDto(roomDto, existingRoom);
        roomRepository.save(existingRoom);
        return roomMapper.toRoomDetailsDto(existingRoom);
    }

    public Page<RoomDetailsDto> getAllRooms(RoomFilter filter) {
        Page<Room> roomPage = roomRepository.findAll(RoomSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
        roomPage.forEach(this::filterUnavailableDatesAfterNow);
        return roomPage.map(roomMapper::toRoomDetailsDto);
    }

    public RoomDto getRoomForEdit(Long id) {
        return roomMapper.toRoomEditDto(findRoomById(id));
    }

    public RoomDetailsDto getRoomDetails(Long id) {
        Room room = findRoomById(id);

        filterUnavailableDatesAfterNow(room);

        return roomMapper.toRoomDetailsDto(room);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
