package com.backend.booking.controller;

import com.backend.booking.model.dto.request.RoomCreateOrUpdateDto;
import com.backend.booking.model.dto.response.RoomDetailsDto;
import com.backend.booking.model.dto.response.RoomDto;
import com.backend.booking.model.filter.RoomFilter;
import com.backend.booking.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDetailsDto> createRoom(@Valid @RequestBody RoomCreateOrUpdateDto roomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(roomDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDetailsDto> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomCreateOrUpdateDto roomDto) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomDto));
    }

    @GetMapping()
    public ResponseEntity<Page<RoomDetailsDto>> getRooms(@Valid RoomFilter filter) {
        return ResponseEntity.ok(roomService.getAllRooms(filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDetailsDto> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomDetails(id));
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<RoomDto> getRoomForEdit(@PathVariable Long id) {
        RoomDto roomEdit = roomService.getRoomForEdit(id);
        return ResponseEntity.ok(roomEdit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
