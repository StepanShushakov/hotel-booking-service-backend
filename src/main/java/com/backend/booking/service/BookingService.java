package com.backend.booking.service;

import com.backend.booking.exception.RoomUnavailableException;
import com.backend.booking.mapper.BookingMapper;
import com.backend.booking.model.dto.BookingDto;
import com.backend.booking.model.entity.Room;
import com.backend.booking.model.entity.UnavailableDates;
import com.backend.booking.repository.BookingRepository;
import com.backend.booking.repository.RoomRepository;
import com.backend.booking.repository.UnavailableDatesRepository;
import com.backend.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final UnavailableDatesRepository unavailableDatesRepository;
    private final BookingMapper bookingMapper;
    private final TransactionalBookingService transactionalBookingService;

    public Page<BookingDto> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable)
                .map(bookingMapper::toBookingDto);
    }

    public boolean areDatesAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        List<UnavailableDates> unavailableDates = unavailableDatesRepository.findByRoom(room);

        for (UnavailableDates dates : unavailableDates) {
            // Проверка на пересечение: если checkIn или checkOut попадают в диапазон недоступных дат
            if ((checkIn.isBefore(dates.getUnavailableTo()) || checkIn.equals(dates.getUnavailableTo()))
                    && (checkOut.isAfter(dates.getUnavailableFrom()) || checkOut.equals(dates.getUnavailableFrom()))) {
                return false;  // Нашли пересечение
            }
        }

        return true; // Пересечений не найдено
    }

    @SneakyThrows
    public BookingDto createBooking(BookingDto bookingDto, String username) {
        Long roomId = bookingDto.getRoomId();
        Room room = roomRepository.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room with id" + roomId + " not found"));
        LocalDate checkIn = bookingDto.getCheckInDate();
        LocalDate checkOut = bookingDto.getCheckOutDate();
        // Проверка доступности комнат
        if (!areDatesAvailable(room, checkIn, checkOut)) {
            throw new RoomUnavailableException("The room is not available on the selected dates");
        }

        return transactionalBookingService.createBookingBlockAvailableDates(userRepository.findAllByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username " + username + " not found")),
                room,
                checkIn,
                checkOut);
    }
}
