package com.backend.booking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoomFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomFilterValid {

    String message() default "Pagination fields must be specified! If you specify checkInDate or checkOutDate, then both fields must be specified!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
