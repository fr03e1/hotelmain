package com.hotelmain.service.impl

import com.hotelmain.domain.Hotel
import com.hotelmain.repository.HotelRepository
import com.hotelmain.service.HotelService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class HotelServiceImpl(private val hotelRepository: HotelRepository): HotelService {
    override fun getHotels(): Flux<Hotel> {
        return hotelRepository.findAll()
    }

    override fun saveHotel(hotel: Hotel): Mono<Hotel> {
        return hotelRepository.save(hotel)
    }
}