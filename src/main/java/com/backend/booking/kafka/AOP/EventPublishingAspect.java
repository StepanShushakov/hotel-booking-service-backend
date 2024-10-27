package com.backend.booking.kafka.AOP;

import com.backend.booking.model.dto.BookingDto;
import com.backend.booking.model.dto.response.UserDetailsDto;
import com.backend.booking.model.entity.RoomBookingEvent;
import com.backend.booking.model.entity.UserRegistrationEvent;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class EventPublishingAspect {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.kafkaUserRegistrationTopic}")
    private String userRegistrationTopic;

    @Value("${app.kafka.kafkaRoomBookingTopic}")
    private String roomBookingTopic;

    @AfterReturning(value = "execution(* com.backend.booking.service.UserService.createUser(..))", returning = "userDetailsDto")
    public void afterUserRegistration(UserDetailsDto userDetailsDto) {
        UserRegistrationEvent event = new UserRegistrationEvent();
        event.setUserId(userDetailsDto.getId());
        event.setRegistrationTime(LocalDateTime.now());
        kafkaTemplate.send(userRegistrationTopic, event);
    }

    @AfterReturning(value = "execution(* com.backend.booking.service.BookingService.createBooking(..))", returning = "bookingDto")
    public void afterRoomBooking(BookingDto bookingDto) {
        RoomBookingEvent event = new RoomBookingEvent();
        event.setUsername(bookingDto.getUsername());
        event.setCheckInDate(bookingDto.getCheckInDate());
        event.setCheckOutDate(bookingDto.getCheckOutDate());
        event.setRoomId(bookingDto.getRoomId());
        kafkaTemplate.send(roomBookingTopic, event);
    }
}
