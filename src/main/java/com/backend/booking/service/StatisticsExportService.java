package com.backend.booking.service;

import com.backend.booking.model.entity.RoomBookingEvent;
import com.backend.booking.model.entity.UserRegistrationEvent;
import com.backend.booking.repository.RoomBookingEventRepository;
import com.backend.booking.repository.UserRegistrationEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsExportService {

    private final UserRegistrationEventRepository userRegistrationEventRepository;

    private final RoomBookingEventRepository roomBookingEventRepository;

    public void exportStatisticsToCSV(Writer writer) throws IOException {
        // Экспорт событий регистрации
        List<UserRegistrationEvent> registrations = userRegistrationEventRepository.findAll();
        writer.write("UserId,RegistrationTime\n");
        for (UserRegistrationEvent event : registrations) {
            writer.write(event.getUserId() + "," + event.getRegistrationTime() + "\n");
        }

        // Экспорт событий бронирования
        List<RoomBookingEvent> bookings = roomBookingEventRepository.findAll();
        writer.write("Username,CheckInDate,CheckOutDate,RoomId\n");
        for (RoomBookingEvent event : bookings) {
            writer.write(event.getUsername() + "," + event.getCheckInDate() + "," + event.getCheckOutDate() + "," + event.getRoomId() + "\n");
        }

        writer.flush();
        writer.close();
    }
}
