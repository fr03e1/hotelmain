package com.hotelmain.service

import com.hotelmain.domain.Country
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CountryService {
    fun getCountry(id: Long): Mono<Country>
    fun getCountries(countryIds: List<Long>): Flux<Country>
    fun getCountries(): Flux<Country>
    fun updateCountry(id: Long, updateCountry: Country): Mono<Country>
    fun saveCountry(country: Country): Mono<Country>
    fun deleteCountry(id: Long): Mono<Void>
}