package com.hotelmain.controller

import com.hotelmain.controller.endpoints.EndpointConstant
import com.hotelmain.dto.HotelDto
import com.hotelmain.service.HotelService
import com.hotelmain.service.mapping.HotelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun getHotels(): Flux<HotelDto> {
        return hotelService.getHotels()
            .map { hotelMapper.toDto(it) }
    }

    @PostMapping
    fun saveHotel(@RequestBody hotelDto: HotelDto): Mono<ResponseEntity<HotelDto>> {
        return hotelService.saveHotel(hotelMapper.toEntity(hotelDto))
            .map { hotelMapper.toDto(it) }
            .map { dto ->
                ResponseEntity.status(HttpStatus.CREATED).body(dto)
            }
    }
}
