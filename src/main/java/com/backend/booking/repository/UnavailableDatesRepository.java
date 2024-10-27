package com.backend.booking.repository;

import com.backend.booking.model.entity.Room;
import com.backend.booking.model.entity.UnavailableDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnavailableDatesRepository extends JpaRepository<UnavailableDates, Long> {
    List<UnavailableDates> findByRoom(Room room);
}
