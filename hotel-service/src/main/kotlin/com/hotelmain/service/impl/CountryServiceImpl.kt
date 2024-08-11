package com.hotelmain.service.impl

import com.hotelmain.domain.Country
import com.hotelmain.exception.NotFoundException
import com.hotelmain.repository.CountryRepository
import com.hotelmain.service.CountryService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class CountryServiceImpl(private val countryRepository: CountryRepository) : CountryService {
    override fun getCountry(id: Long): Mono<Country> {
        return countryRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Country", id)))
    }

    override fun getCountries(countryIds: List<Long>): Flux<Country> {
        return countryRepository.findAllById(countryIds)
    }

    override fun getCountries(): Flux<Country> {
        return countryRepository.findAll()
    }

    override fun updateCountry(id: Long, updateCountry: Country): Mono<Country> {
        return getCountry(id).flatMap { existingCountry ->
            existingCountry.apply {
                name = updateCountry.name
                code = updateCountry.code
                updatedAt = LocalDateTime.now()
            }
            countryRepository.save(existingCountry)
        }
    }

    override fun saveCountry(country: Country): Mono<Country> {
        return countryRepository.save(country)
    }

    override fun deleteCountry(id: Long): Mono<Void> {
        return getCountry(id).flatMap { hotel ->
            countryRepository.delete(hotel)
        }
    }
}