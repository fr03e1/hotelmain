package com.hotelmain.controller

import com.hotelmain.controller.endpoints.EndpointConstant
import com.hotelmain.dto.api.ApiResponse
import com.hotelmain.dto.country.CountryRequest
import com.hotelmain.dto.country.CountryResponse
import com.hotelmain.dto.validation.OnCreate
import com.hotelmain.dto.validation.OnUpdate
import com.hotelmain.service.CountryService
import com.hotelmain.service.mapping.CountryMapper
import com.hotelmain.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(EndpointConstant.COUNTRIES_ENDPOINT)
class CountryController(
    private val countryService: CountryService,
    private val countryMapper: CountryMapper
) {

    @GetMapping
    fun getCountries(): ResponseEntity<Flux<CountryResponse>> {
        val countries = countryService.getCountries()
            .map { countryMapper.toCountryResponse(it) }

        return ResponseEntity.status(HttpStatus.OK).body(countries)
    }

    @GetMapping(EndpointConstant.ID_PATH)
    fun getCountry(@PathVariable id: Long): Mono<ResponseEntity<CountryResponse>> {
        return countryService.getCountry(id)
            .map { countryMapper.toCountryResponse(it) }
            .map { ResponseEntity.status(HttpStatus.OK).body(it) }
    }

    @PostMapping
    fun saveCountry(
        @Validated(OnCreate::class) @RequestBody countryRequest: CountryRequest
    ): Mono<ResponseEntity<CountryResponse>> {
        return countryService.saveCountry(countryMapper.toCountry(countryRequest))
            .map { countryMapper.toCountryResponse(it) }
            .map { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @PutMapping(EndpointConstant.ID_PATH)
    fun updateCountry(
        @PathVariable id: Long,
        @Validated(OnUpdate::class) @RequestBody countryRequest: CountryRequest
    ): Mono<ResponseEntity<CountryResponse>> {
        return countryService.updateCountry(id, countryMapper.toCountry(countryRequest))
            .map { countryMapper.toCountryResponse(it) }
            .map { ResponseEntity.status(HttpStatus.OK).body(it) }
    }

    @DeleteMapping(EndpointConstant.ID_PATH)
    fun deleteCountry(@PathVariable id: Long): Mono<ResponseEntity<ApiResponse>> {
        return countryService.deleteCountry(id)
            .then(ResponseUtil.successfulDeletionResponse("Country"))
    }
}