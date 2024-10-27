package com.backend.booking.kafka;

import com.backend.booking.model.entity.RoomBookingEvent;
import com.backend.booking.model.entity.UserRegistrationEvent;
import com.backend.booking.repository.RoomBookingEventRepository;
import com.backend.booking.repository.UserRegistrationEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventListenerService {

    private final UserRegistrationEventRepository userRegistrationEventRepository;
    private final RoomBookingEventRepository roomBookingEventRepository;

    @KafkaListener(topics = "${app.kafka.kafkaUserRegistrationTopic}", groupId = "${app.kafka.kafkaGroupId}")
    public void handleUserRegistrationEvent(UserRegistrationEvent event) {
        userRegistrationEventRepository.save(event);
    }

    @KafkaListener(topics = "${app.kafka.kafkaRoomBookingTopic}", groupId = "${app.kafka.kafkaGroupId}")
    public void handleRoomBookingEvent(RoomBookingEvent event) {
        roomBookingEventRepository.save(event);
    }
}
