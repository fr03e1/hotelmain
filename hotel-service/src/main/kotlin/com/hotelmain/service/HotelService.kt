package com.hotelmain.service

import com.hotelmain.domain.Hotel
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface HotelService {
    fun getHotels(): Flux<Hotel>
    fun getHotel(id: Long): Mono<Hotel>
    fun saveHotel(hotel: Hotel): Mono<Hotel>
    fun updateHotel(id: Long, updateHotel: Hotel): Mono<Hotel>
    fun deleteHotel(id: Long): Mono<Void>
}