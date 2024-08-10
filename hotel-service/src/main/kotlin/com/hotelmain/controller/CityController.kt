package com.hotelmain.controller

import com.hotelmain.controller.endpoints.EndpointConstant
import com.hotelmain.dto.city.CityRequest
import com.hotelmain.dto.city.CityResponse
import com.hotelmain.dto.validation.OnCreate
import com.hotelmain.service.CityService
import com.hotelmain.service.CountryService
import com.hotelmain.service.mapping.CityMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(EndpointConstant.CITIES_ENDPOINT)
class CityController(
    private val cityService: CityService,
    private val countryService: CountryService,
    private val cityMapper: CityMapper
) {

    @GetMapping
    fun getCities(): ResponseEntity<Flux<CityResponse>> {
        val cities = cityService.getCities()
            .flatMap { city ->
                countryService.getCounty(city.countryId)
                    .map { country -> cityMapper.toCityResponse(city, country) }
            }

        return ResponseEntity.status(HttpStatus.OK).body(cities)
    }

    @GetMapping(EndpointConstant.ID_PATH)
    fun getCity(@PathVariable id: Long): Mono<ResponseEntity<CityResponse>> {
        return cityService.getCityResponse(id)
            .map { ResponseEntity.status(HttpStatus.OK).body(it) }
    }

    @PostMapping
    fun saveCity(
        @Validated(OnCreate::class) @RequestBody cityRequest: CityRequest
    ): Mono<ResponseEntity<CityResponse>> {
        return cityService.saveCity(cityRequest)
            .map { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }
}