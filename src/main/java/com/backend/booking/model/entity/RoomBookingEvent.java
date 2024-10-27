package com.backend.booking.model.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "room_booking_events")
@Data
public class RoomBookingEvent {
    @Id
    private String id;
    private String username;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
