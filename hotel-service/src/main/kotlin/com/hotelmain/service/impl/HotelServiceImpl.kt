package com.hotelmain.service.impl

import com.hotelmain.domain.Hotel
import com.hotelmain.exception.NotFoundException
import com.hotelmain.repository.HotelRepository
import com.hotelmain.service.HotelService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class HotelServiceImpl(private val hotelRepository: HotelRepository) : HotelService {
    override fun getHotels(): Flux<Hotel> {
        return hotelRepository.findAll()
    }

    override fun getHotel(id: Long): Mono<Hotel> {
        return hotelRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Hotel", id)))
    }

    override fun saveHotel(hotel: Hotel): Mono<Hotel> {
        return hotelRepository.save(hotel)
    }

    override fun updateHotel(id: Long, updateHotel: Hotel): Mono<Hotel> {
        return getHotel(id).flatMap { existingHotel ->
                existingHotel.apply {
                    name = updateHotel.name
                    address = updateHotel.address
                    city = updateHotel.city
                    country = updateHotel.country
                    description = updateHotel.description
                    rating = updateHotel.rating
                    updatedAt = LocalDateTime.now()
                }
                hotelRepository.save(existingHotel)
            }
    }

    override fun deleteHotel(id: Long): Mono<Void> {
        return getHotel(id).flatMap { hotel ->
                hotelRepository.delete(hotel)
            }
    }
}