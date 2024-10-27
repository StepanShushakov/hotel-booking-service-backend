package com.backend.booking.service;

import com.backend.booking.mapper.HotelMapper;
import com.backend.booking.model.dto.response.HotelDtoWithRating;
import com.backend.booking.model.dto.response.HotelDtoWithoutRating;
import com.backend.booking.model.dto.request.HotelCreateOrUpdateDto;
import com.backend.booking.model.entity.Hotel;
import com.backend.booking.model.filter.HotelFilter;
import com.backend.booking.repository.HotelRepository;
import com.backend.booking.repository.HotelSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    private Hotel findHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with id " + id + " not found"));
    }

    private HotelDtoWithRating saveHotelAndReturnHotelDtoWithRating(Hotel hotel){
        hotelRepository.save(hotel);
        return hotelMapper.toHotelDtoWithRating(hotel);
    }
    public HotelDtoWithoutRating createHotel(HotelCreateOrUpdateDto hotelDto) {
        Hotel hotel = hotelMapper.toHotel(hotelDto);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toHotelDtoWithoutRating(hotel);
    }

    public HotelDtoWithoutRating updateHotel(Long id, HotelCreateOrUpdateDto hotelDto) {
        Hotel existingHotel = findHotelById(id);
        hotelMapper.updateHotelFromDto(hotelDto, existingHotel);
        hotelRepository.save(existingHotel);
        return hotelMapper.toHotelDtoWithoutRating(existingHotel);
    }

    public HotelDtoWithoutRating getHotelById(Long id) {
        return hotelMapper.toHotelDtoWithoutRating(findHotelById(id));
    }

    public Page<HotelDtoWithoutRating> getAllHotels(HotelFilter filter) {
        Page<Hotel> hotelPage = hotelRepository.findAll(HotelSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
        return hotelPage.map(hotelMapper::toHotelDtoWithoutRating);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    public HotelDtoWithRating updateHotelRating(Long hotelId, int newMark) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        // Рассчитаем новый рейтинг по формуле
        int currentNumberOfRatings = hotel.getNumberOfRatings();
        if (currentNumberOfRatings == 0) {
            hotel.setRating(newMark);
            hotel.setNumberOfRatings(1);
            return saveHotelAndReturnHotelDtoWithRating(hotel);

        }
        double currentRating = hotel.getRating();

        // Шаг 1: Вычисляем сумму всех оценок (totalRating)
        double totalRating = currentRating * currentNumberOfRatings;

        // Шаг 2: Вычитаем текущий рейтинг и добавляем новую оценку
        totalRating = totalRating - currentRating + newMark;

        // Шаг 3: Вычисляем новый рейтинг
        double updatedRating = totalRating / currentNumberOfRatings;

        // Шаг 4: Увеличиваем количество оценок на 1
        currentNumberOfRatings++;

        // Обновляем рейтинг и количество оценок
        hotel.setRating(Math.round(updatedRating * 10.0) / 10.0); // Округление до одного знака после запятой
        hotel.setNumberOfRatings(currentNumberOfRatings);

        // Сохраняем обновленный отель
        return saveHotelAndReturnHotelDtoWithRating(hotel);
    }
}
