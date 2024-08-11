package com.hotelmain.service

import com.hotelmain.domain.City
import com.hotelmain.dto.city.CityRequest
import com.hotelmain.dto.city.CityResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CityService {
    fun getCities(): Flux<CityResponse>
    fun getCityResponse(id: Long): Mono<CityResponse>
    fun getCity(id: Long): Mono<City>
    fun saveCity(cityRequest: CityRequest): Mono<CityResponse>
    fun updateCity(id: Long, city: City): Mono<CityResponse>
    fun deleteCity(id: Long): Mono<Void>
}