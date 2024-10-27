package com.backend.booking.repository;

import com.backend.booking.model.entity.UserRegistrationEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationEventRepository extends MongoRepository<UserRegistrationEvent, String> {
}
