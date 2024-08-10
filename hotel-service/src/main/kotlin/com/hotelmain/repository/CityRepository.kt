package com.hotelmain.repository

import com.hotelmain.domain.City
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CityRepository: ReactiveCrudRepository<City, Long> {
}