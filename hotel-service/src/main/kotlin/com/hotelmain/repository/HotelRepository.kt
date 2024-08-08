package com.hotelmain.repository

import com.hotelmain.domain.Hotel
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface HotelRepository: ReactiveCrudRepository<Hotel, Long> {
}