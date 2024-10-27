package com.backend.booking.validation;

import com.backend.booking.model.filter.HotelFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class HotelFilterValidValidator implements ConstraintValidator<HotelFilterValid, HotelFilter> {

    @Override
    public boolean isValid(HotelFilter value, ConstraintValidatorContext context) {
        return !ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize());
    }
}
