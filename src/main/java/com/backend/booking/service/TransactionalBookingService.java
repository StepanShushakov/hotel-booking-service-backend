package com.backend.booking.service;

import com.backend.booking.mapper.BookingMapper;
import com.backend.booking.model.dto.BookingDto;
import com.backend.booking.model.entity.Booking;
import com.backend.booking.model.entity.Room;
import com.backend.booking.model.entity.UnavailableDates;
import com.backend.booking.model.entity.User;
import com.backend.booking.repository.BookingRepository;
import com.backend.booking.repository.UnavailableDatesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TransactionalBookingService {

    private final BookingRepository bookingRepository;
    private final UnavailableDatesRepository unavailableDatesRepository;
    private final BookingMapper bookingMapper;

    @Transactional
    public BookingDto createBookingBlockAvailableDates(User user, Room room, LocalDate checkIn, LocalDate checkOut) {
        // Создаем запись бронирования
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        bookingRepository.save(booking);

        // Блокируем даты для других бронирований
        UnavailableDates unavailableDates = new UnavailableDates();
        unavailableDates.setRoom(room);
        unavailableDates.setUnavailableFrom(checkIn);
        unavailableDates.setUnavailableTo(checkOut);
        unavailableDatesRepository.save(unavailableDates);

        return bookingMapper.toBookingDto(booking);
    }

}
