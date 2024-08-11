package com.hotelmain.service.mapping

import com.hotelmain.domain.City
import com.hotelmain.domain.Country
import com.hotelmain.dto.city.CityRequest
import com.hotelmain.dto.city.CityResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [CountryMapper::class])
interface CityMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    fun toCity(cityRequest: CityRequest): City

    @Mapping(source = "city.id", target = "id")
    @Mapping(source = "city.name", target = "name")
    @Mapping(source = "country", target = "country")
    fun toCityResponse(city: City, country: Country): CityResponse

}