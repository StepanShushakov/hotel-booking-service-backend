package com.backend.booking.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String adTitle;
    private String city;
    private String address;
    @Column(name = "distance_to_center")
    private double distanceToCenter;
    private double rating;
    private int numberOfRatings;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;

    public Hotel(Long id) {
        this.id = id;
    }
}
