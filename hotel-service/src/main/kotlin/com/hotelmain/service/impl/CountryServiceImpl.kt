package com.hotelmain.service.impl

import com.hotelmain.domain.Country
import com.hotelmain.repository.CountryRepository
import com.hotelmain.service.CountryService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CountryServiceImpl(private val countryRepository: CountryRepository): CountryService {
    override fun getCounty(id: Long): Mono<Country> {
        return countryRepository.findById(id)
    }
}