package com.hotelmain.controller

import com.hotelmain.controller.endpoints.EndpointConstant
import com.hotelmain.dto.api.ApiResponse
import com.hotelmain.dto.hotel.HotelRequest
import com.hotelmain.dto.hotel.HotelResponse
import com.hotelmain.dto.validation.OnCreate
import com.hotelmain.dto.validation.OnUpdate
import com.hotelmain.exception.NotFoundException
import com.hotelmain.service.HotelService
import com.hotelmain.service.mapping.HotelMapper
import com.hotelmain.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(EndpointConstant.HOTELS_ENDPOINT)
class HotelController(
    private val hotelService: HotelService,
    private val hotelMapper: HotelMapper
) {

    @GetMapping
    fun getHotels(): ResponseEntity<Flux<HotelResponse>> {
        val hotels = hotelService.getHotels()
            .map { hotelMapper.toHotelResponse(it) }

        return ResponseEntity.status(HttpStatus.OK).body(hotels)
    }

    @GetMapping(EndpointConstant.ID_PATH)
    fun getHotel(@PathVariable id: Long): Mono<ResponseEntity<HotelResponse>> {
        return hotelService.getHotel(id)
            .map { hotelMapper.toHotelResponse(it) }
            .map { response -> ResponseEntity.status(HttpStatus.OK).body(response) }
            .switchIfEmpty(Mono.error(NotFoundException("Hotel", id)))
    }

    @PostMapping
    fun saveHotel(@Validated(OnCreate::class) @RequestBody hotelRequest: HotelRequest): Mono<ResponseEntity<HotelResponse>> {
        return hotelService.saveHotel(hotelMapper.toHotel(hotelRequest))
            .map { hotelMapper.toHotelResponse(it) }
            .map { response -> ResponseEntity.status(HttpStatus.CREATED).body(response) }
    }

    @PutMapping(EndpointConstant.ID_PATH)
    fun updateHotel(
        @PathVariable id: Long,
        @Validated(OnUpdate::class) @RequestBody updatedHotelRequest: HotelRequest
    ): Mono<ResponseEntity<HotelResponse>> {
        return hotelService.updateHotel(id, hotelMapper.toHotel(updatedHotelRequest))
            .map { hotelMapper.toHotelResponse(it) }
            .map { response -> ResponseEntity.status(HttpStatus.OK).body(response) }
    }

    @DeleteMapping(EndpointConstant.ID_PATH)
    fun deleteHotel(@PathVariable id: Long): Mono<ResponseEntity<ApiResponse>> {
        return hotelService.deleteHotel(id)
            .then(ResponseUtil.successfulDeletionResponse("Hotel"))
    }
}
