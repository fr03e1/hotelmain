package com.hotelmain.service.impl

import com.hotelmain.domain.City
import com.hotelmain.dto.city.CityRequest
import com.hotelmain.dto.city.CityResponse
import com.hotelmain.exception.NotFoundException
import com.hotelmain.repository.CityRepository
import com.hotelmain.repository.CountryRepository
import com.hotelmain.service.CityService
import com.hotelmain.service.mapping.CityMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CityServiceIml(
    private val cityRepository: CityRepository,
    private val countryRepository: CountryRepository,
    private val cityMapper: CityMapper
) : CityService {
    override fun getCities(): Flux<City> {
        return cityRepository.findAll()
    }

    override fun getCityResponse(id: Long): Mono<CityResponse> {
        return getCity(id)
            .flatMap { city ->
                countryRepository.findById(city.countryId)
                    .map { country ->
                        cityMapper.toCityResponse(city, country)
                    }
            }
    }

    override fun getCity(id: Long): Mono<City> {
        return cityRepository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("City", id)))
    }

    override fun saveCity(cityRequest: CityRequest): Mono<CityResponse> {
        return countryRepository.findById(cityRequest.countryId)
            .switchIfEmpty(Mono.error(NotFoundException("County", cityRequest.countryId)))
            .flatMap { country ->
                val city = City(
                    name = cityRequest.name,
                    countryId = country.id!!
                )
                cityRepository.save(city)
                    .map { savedCity -> cityMapper.toCityResponse(savedCity, country) }
            }
    }
}