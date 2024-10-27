package com.backend.booking.model.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "user_registration_events")
@Data
public class UserRegistrationEvent {
    @Id
    private String id;
    private Long userId;
    private LocalDateTime registrationTime;
}
