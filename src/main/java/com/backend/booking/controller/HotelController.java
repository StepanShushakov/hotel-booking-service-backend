package com.backend.booking.controller;

import com.backend.booking.model.dto.response.HotelDtoWithRating;
import com.backend.booking.model.dto.response.HotelDtoWithoutRating;
import com.backend.booking.model.dto.request.HotelCreateOrUpdateDto;
import com.backend.booking.model.filter.HotelFilter;
import com.backend.booking.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDtoWithoutRating> createHotel(@RequestBody HotelCreateOrUpdateDto hotelDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(hotelDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDtoWithoutRating> updateHotel(@PathVariable Long id, @RequestBody HotelCreateOrUpdateDto hotelDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.updateHotel(id, hotelDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDtoWithoutRating> getHotel(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @GetMapping()
    private ResponseEntity<Page<HotelDtoWithoutRating>> getAllHotels(@Valid HotelFilter filter) {
        return ResponseEntity.ok(hotelService.getAllHotels(filter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/rating")
    public ResponseEntity<HotelDtoWithRating> updateRating(@PathVariable Long id, @RequestParam int newMark) {
        if (newMark < 1 || newMark > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        HotelDtoWithRating updatedHotel = hotelService.updateHotelRating(id, newMark);
        return ResponseEntity.ok(updatedHotel);
    }
}
