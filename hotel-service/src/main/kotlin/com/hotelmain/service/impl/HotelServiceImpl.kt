package com.hotelmain.service.impl

import com.hotelmain.domain.Hotel
import com.hotelmain.exception.NotFoundException
import com.hotelmain.repository.HotelRepository
import com.hotelmain.service.HotelService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class HotelServiceImpl(private val hotelRepository: HotelRepository) : HotelService {
    override fun getHotels(): Flux<Hotel> {
        return hotelRepository.findAll()
    }

    override fun getHotel(id: Long): Mono<Hotel> {
        return hotelRepository.findById(id)
    }

    override fun saveHotel(hotel: Hotel): Mono<Hotel> {
        return hotelRepository.save(hotel)
    }

    override fun updateHotel(id: Long, updateHotel: Hotel): Mono<Hotel> {
        return hotelRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Hotel", id)))
            .flatMap { existingHotel ->
                existingHotel.name = updateHotel.name
                existingHotel.address = updateHotel.address
                existingHotel.city = updateHotel.city
                existingHotel.country = updateHotel.country
                existingHotel.description = updateHotel.description
                existingHotel.rating = updateHotel.rating
                hotelRepository.save(existingHotel)
            }
    }

    override fun deleteHotel(id: Long): Mono<Void> {
        return hotelRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Hotel", id)))
            .flatMap { hotel ->
                hotelRepository.delete(hotel)
            }
    }
}