package com.hotelmain.controller

import com.hotelmain.controller.endpoints.EndpointConstant
import com.hotelmain.dto.hotel.HotelRequest
import com.hotelmain.dto.hotel.HotelResponse
import com.hotelmain.dto.validation.OnCreate
import com.hotelmain.dto.validation.OnUpdate
import com.hotelmain.exception.NotFoundException
import com.hotelmain.service.HotelService
import com.hotelmain.service.mapping.HotelMapper
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
            .map { hotelMapper.toDto(it) }

        return ResponseEntity.status(HttpStatus.OK).body(hotels)
    }

    @GetMapping("/{id}")
    fun getHotel(@PathVariable id: Long): Mono<ResponseEntity<HotelResponse>> {
        return hotelService.getHotel(id)
            .map { hotelMapper.toDto(it) }
            .map { dto -> ResponseEntity.status(HttpStatus.OK).body(dto) }
            .switchIfEmpty(Mono.error(NotFoundException("Hotel with id $id not found")));
    }

    @PostMapping
    fun saveHotel(@Validated(OnCreate::class) @RequestBody hotelRequest: HotelRequest): Mono<ResponseEntity<HotelResponse>> {
        return hotelService.saveHotel(hotelMapper.toEntity(hotelRequest))
            .map { hotelMapper.toDto(it) }
            .map { dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto) }
    }

    @PutMapping("/{id}")
    fun updateHotel(
        @PathVariable id: Long,
        @Validated(OnUpdate::class) @RequestBody updatedHotelRequest: HotelRequest
    ): Mono<ResponseEntity<HotelResponse>> {
        return hotelService.updateHotel(id, hotelMapper.toEntity(updatedHotelRequest))
            .map { hotelMapper.toDto(it) }
            .map { dto -> ResponseEntity.status(HttpStatus.OK).body(dto) }
    }

    @DeleteMapping("/{id}")
    fun deleteHotel(@PathVariable id: Long): Mono<ResponseEntity<String>> {
        return hotelService.deleteHotel(id)
            .then(Mono.just(ResponseEntity.status(HttpStatus.OK).body("Hotel deleted successfully")))
    }
}
