package com.backend.booking.validation;

import com.backend.booking.model.filter.RoomFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class RoomFilterValidValidator implements ConstraintValidator<RoomFilterValid, RoomFilter> {

    @Override
    public boolean isValid(RoomFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }

        if ((value.getCheckInDate() == null && value.getCheckOutDate() != null)
                || (value.getCheckInDate() != null && value.getCheckOutDate() == null)) {
            return false;
        }

        return true;
    }
}
