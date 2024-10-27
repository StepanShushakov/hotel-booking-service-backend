package com.backend.booking.repository;

import com.backend.booking.model.entity.RoomBookingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomBookingEventRepository extends MongoRepository<RoomBookingEvent, String> {
}
