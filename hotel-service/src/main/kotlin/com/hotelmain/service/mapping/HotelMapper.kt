package com.hotelmain.service.mapping

import com.hotelmain.domain.Hotel
import com.hotelmain.dto.HotelDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.stereotype.Service

@Mapper(componentModel = "spring")
interface HotelMapper {
    fun toDto(entity: Hotel): HotelDto
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    fun toEntity(dto: HotelDto): Hotel
}