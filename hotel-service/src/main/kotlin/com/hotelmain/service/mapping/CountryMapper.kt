package com.hotelmain.service.mapping

import com.hotelmain.domain.Country
import com.hotelmain.dto.country.CountryRequest
import com.hotelmain.dto.country.CountryResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CountryMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    fun toCountry(countryRequest: CountryRequest): Country
    fun toCountryResponse(country: Country): CountryResponse
}