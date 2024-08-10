package com.hotelmain.service.mapping

import com.hotelmain.domain.Country
import com.hotelmain.dto.country.CountryResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CountryMapper {
    fun toCountryResponse(country: Country): CountryResponse
}