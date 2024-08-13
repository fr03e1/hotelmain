package com.hotelmain.service.mapping

import com.hotelmain.domain.Country
import com.hotelmain.dto.country.CountryRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.mapstruct.factory.Mappers
import java.time.LocalDateTime
import kotlin.test.Test

class CountryMapperTest {
    private val countryMapper: CountryMapper = Mappers.getMapper(CountryMapper::class.java)

    @Test
    fun should_map_CountryRequest_to_Country_correctly() {
        val countryRequest = CountryRequest(name = "Test Country", code = "123")

        val country = countryMapper.toCountry(countryRequest)

        assertEquals(countryRequest.name, country.name)
        assertEquals(countryRequest.code, country.code)
        assertEquals(LocalDateTime.now().minute, country.createdAt.minute)
        assertEquals(LocalDateTime.now().minute, country.updatedAt.minute)
    }

    @Test
    fun should_map_Country_to_CountryRequest_correctly() {
        val country = Country(
            id = 1L,
            name = "Test Country",
            code = "123",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val countryResponse = countryMapper.toCountryResponse(country)

        assertEquals(country.id, countryResponse.id)
        assertEquals(country.name, countryResponse.name)
        assertEquals(country.code, countryResponse.code)
    }
}