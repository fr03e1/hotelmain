package com.hotelmain.service.impl

import com.hotelmain.domain.City
import com.hotelmain.domain.Country
import com.hotelmain.dto.city.CityRequest
import com.hotelmain.dto.city.CityResponse
import com.hotelmain.exception.NotFoundException
import com.hotelmain.repository.CityRepository
import com.hotelmain.service.CityService
import com.hotelmain.service.CountryService
import com.hotelmain.service.mapping.CityMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class CityServiceImpl(
    private val cityRepository: CityRepository,
    private val countryService: CountryService,
    private val cityMapper: CityMapper
) : CityService {
    override fun getCities(): Flux<CityResponse> {
        return cityRepository.findAll().collectList()
            .flatMapMany { cityList ->
                val countryIds = cityList.map { it.countryId }.distinct()
                countryService.getCountries(countryIds)
                    .collectMap({ it.id!! }, { it })
                    .flatMapMany { countryMap ->
                        Flux.fromIterable(cityList)
                            .map { city ->
                                val country = countryMap[city.countryId]
                                    ?: Country(id = city.countryId, name = "Unknown", code = "123")
                                cityMapper.toCityResponse(city, country)
                            }
                    }
            }
    }

    override fun getCityResponse(id: Long): Mono<CityResponse> {
        return getCity(id)
            .flatMap { city ->
                countryService.getCountry(city.countryId)
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
        return countryService.getCountry(cityRequest.countryId)
            .flatMap { country ->
                val city = City(
                    name = cityRequest.name,
                    countryId = country.id!!
                )
                cityRepository.save(city)
                    .map { savedCity -> cityMapper.toCityResponse(savedCity, country) }
            }
    }

    override fun updateCity(id: Long, city: City): Mono<CityResponse> {
        return getCity(id).flatMap { existingCity ->
                existingCity.apply {
                    name = city.name
                    countryId = city.countryId
                    updatedAt = LocalDateTime.now()
                }
                cityRepository.save(existingCity)
            }.flatMap { updateCity ->
                countryService.getCountry(updateCity.countryId)
                    .map { country -> cityMapper.toCityResponse(updateCity, country) }
            }
    }

    override fun deleteCity(id: Long): Mono<Void> {
        return getCity(id).flatMap { hotel ->
            cityRepository.delete(hotel)
        }
    }
}