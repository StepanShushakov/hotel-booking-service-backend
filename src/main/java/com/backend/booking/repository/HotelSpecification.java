package com.backend.booking.repository;

import com.backend.booking.model.entity.Hotel;
import com.backend.booking.model.filter.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter hotelFilter) {
        return Specification.where(byCity(hotelFilter.getCity()))
                .and(byLessMaxDistanceToCenter(hotelFilter.getMaxDistanceToCenter()))
                .and(byRatingRange(hotelFilter.getMinRating(), hotelFilter.getMaxRating()))
                .and(byNameEqual(hotelFilter.getName()))
                .and(byAdTitleContains(hotelFilter.getAdTitle()))
                .and(byAddressContains(hotelFilter.getAddress()))
                .and(byNumberOfRatingsMore(hotelFilter.getMinNumberOfRatings()));
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, cb) -> {
            if (city == null) {
                return null;
            }

            return cb.equal(root.get("city"), city);
        };
    }

    static Specification<Hotel> byLessMaxDistanceToCenter(Double distance) {
        return ((root, query, cb) -> {
            if (distance == null) {
                return null;
            }
            return cb.lessThanOrEqualTo(root.get("distanceToCenter"), distance);
        });
    }

    static Specification<Hotel> byRatingRange(Double minRating, Double maxRating) {
        return ((root, query, cb) -> {
            if (minRating == null && maxRating == null) {
                return null;
            }
            if (minRating == null) {
                return cb.lessThanOrEqualTo(root.get("rating"), maxRating);
            }
            if (maxRating == null) {
                return cb.greaterThanOrEqualTo(root.get("rating"), minRating);
            }
            return cb.between(root.get("rating"), minRating, maxRating);
        });
    }

    static Specification<Hotel> byNameEqual(String name) {
        return ((root, query, cb) -> {
            if (name == null) {
                return null;
            }
            return cb.equal(root.get("name"), name);
        });
    }

    static Specification<Hotel> byAdTitleContains(String adTitle) {
        return ((root, query, cb) -> {
            if (adTitle == null) {
                return null;
            }
            return cb.like(cb.lower(root.get("adTitle")), "%" + adTitle.toLowerCase() + "%");
        });
    }

    static Specification<Hotel> byAddressContains(String address) {
        return ((root, query, cb) -> {
            if (address == null) {
                return null;
            }
            return cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%");
        });
    }
    static Specification<Hotel> byNumberOfRatingsMore(Integer numbers) {
        return ((root, query, cb) -> {
            if (numbers == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get("numberOfRatings"), numbers);
        });
    }
}
