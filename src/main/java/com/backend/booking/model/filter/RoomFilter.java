package com.backend.booking.model.filter;

import com.backend.booking.validation.RoomFilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@RoomFilterValid
public class RoomFilter {

    private Integer pageSize;
    private Integer pageNumber;

    private Long roomId;
    private String title;
    private Double minPrice;
    private Double maxPrice;
    private Integer guestsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long hotelId;
}
