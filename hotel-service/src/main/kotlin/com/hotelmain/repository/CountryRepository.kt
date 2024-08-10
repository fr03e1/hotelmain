package com.hotelmain.repository

import com.hotelmain.domain.Country
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CountryRepository: ReactiveCrudRepository<Country, Long> {
}