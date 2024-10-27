package com.backend.booking.repository;

import com.backend.booking.model.entity.Room;
import com.backend.booking.model.entity.UnavailableDates;
import com.backend.booking.model.filter.RoomFilter;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface RoomSpecification {

    static Specification<Room> withFilter(RoomFilter roomFilter) {
        return Specification.where(byRoomId(roomFilter.getRoomId()))
                .and(byHotelId(roomFilter.getHotelId()))
                .and(byTitleContains(roomFilter.getTitle()))
                .and(byPriceRange(roomFilter.getMinPrice(), roomFilter.getMaxPrice()))
                .and(byGuestsCount(roomFilter.getGuestsCount()))
                .and(byAvailableDates(roomFilter.getCheckInDate(), roomFilter.getCheckOutDate()));
    }

    static Specification<Room> byRoomId(Long roomId) {
        return ((root, query, cb) -> {
            if (roomId == null) {
                return null;
            }
            return cb.equal(root.get("id"), roomId);
        });
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return ((root, query, cb) -> {
            if (hotelId == null) {
                return null;
            }
            return cb.equal(root.get("hotel").get("id"), hotelId);
        });
    }

    static Specification<Room> byTitleContains(String title) {
        return ((root, query, cb) -> {
            if (title == null) {
                return null;
            }
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        });
    }

    static Specification<Room> byPriceRange(Double minPrice, Double maxPrice) {
        return ((root, query, cb) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            if (minPrice == null) {
                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
            if (maxPrice == null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
            return cb.between(root.get("price"), minPrice, maxPrice);
        });
    }

    static Specification<Room> byGuestsCount(Integer guestsCount) {
        return ((root, query, cb) -> {
            if (guestsCount == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get("maxGuests"), guestsCount);
        });
    }

    static Specification<Room> byAvailableDates(LocalDate checkIn, LocalDate checkOut) {
        return (root, query, cb) -> {
            if (checkIn == null || checkOut == null) {
                return null;
            }

            // Подзапрос на недоступные даты
            assert query != null;
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<UnavailableDates> unavailableRoot = subquery.from(UnavailableDates.class);
            subquery.select(unavailableRoot.get("room").get("id"))
                    .where(cb.and(
                            cb.equal(unavailableRoot.get("room").get("id"), root.get("id")),
                            cb.or(
                                    cb.and(
                                            cb.lessThanOrEqualTo(unavailableRoot.get("unavailableFrom"), checkIn),
                                            cb.greaterThanOrEqualTo(unavailableRoot.get("unavailableTo"), checkIn)
                                    ),
                                    cb.and(
                                            cb.lessThanOrEqualTo(unavailableRoot.get("unavailableFrom"), checkOut),
                                            cb.greaterThanOrEqualTo(unavailableRoot.get("unavailableTo"), checkOut)
                                    ),
                                    cb.and(
                                            cb.lessThanOrEqualTo(unavailableRoot.get("unavailableFrom"), checkIn),
                                            cb.greaterThanOrEqualTo(unavailableRoot.get("unavailableTo"), checkOut)
                                    )
                            )
                    ));

            // Фильтрация комнат, которые не имеют пересечений с недоступными датами
            return cb.not(root.get("id").in(subquery));
        };
    }
}
