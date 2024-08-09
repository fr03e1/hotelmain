package com.hotelmain.service.mapping

import com.hotelmain.domain.Hotel
import com.hotelmain.dto.hotel.HotelRequest
import com.hotelmain.dto.hotel.HotelResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface HotelMapper {
    fun toDto(entity: Hotel): HotelResponse

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    fun toEntity(dto: HotelRequest): Hotel
}