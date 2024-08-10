package com.hotelmain.dto.city

import com.hotelmain.dto.country.CountryResponse

data class CityResponse(
    val id: Long,
    val name: String,
    val country: CountryResponse
)
