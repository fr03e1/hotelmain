package com.hotelmain.controller

import com.hotelmain.controller.endpoints.EndpointConstant
import com.hotelmain.dto.city.CityRequest
import com.hotelmain.dto.city.CityResponse
import com.hotelmain.dto.validation.OnCreate
import com.hotelmain.dto.validation.OnUpdate
import com.hotelmain.service.CityService
import com.hotelmain.service.mapping.CityMapper
import com.hotelmain.util.ResponseUtil
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
    private val cityMapper: CityMapper
) {

    @GetMapping
    fun getCities(): ResponseEntity<Flux<CityResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getCities())
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

    @PutMapping(EndpointConstant.ID_PATH)
    fun updateCity(
        @PathVariable id: Long,
        @Validated(OnUpdate::class) @RequestBody updateCityRequest: CityRequest
    ): Mono<ResponseEntity<CityResponse>> {
        return cityService.updateCity(id, cityMapper.toCity(updateCityRequest))
            .map { response -> ResponseEntity.status(HttpStatus.OK).body(response) }
    }

    @DeleteMapping(EndpointConstant.ID_PATH)
    fun deleteCity(@PathVariable id: Long): Mono<ResponseEntity<Map<String, String>>> {
        return cityService.deleteCity(id)
            .then(ResponseUtil.successfulDeletionResponse("City"))
    }
}