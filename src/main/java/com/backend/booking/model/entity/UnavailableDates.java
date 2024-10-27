package com.backend.booking.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class UnavailableDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate unavailableFrom;
    private LocalDate unavailableTo;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

}
