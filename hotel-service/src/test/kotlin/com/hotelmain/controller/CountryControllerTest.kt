package com.hotelmain.controller

import com.hotelmain.controller.endpoints.EndpointConstant
import com.hotelmain.domain.Country
import com.hotelmain.dto.country.CountryRequest
import com.hotelmain.dto.country.CountryResponse
import com.hotelmain.exception.NotFoundException
import com.hotelmain.service.CountryService
import com.hotelmain.service.mapping.CountryMapperImpl
import com.hotelmain.util.ResponseUtil
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.test.Test

@WebFluxTest(CountryController::class)
@Import(CountryMapperImpl::class)
class CountryControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var countryService: CountryService

    @MockBean
    private lateinit var responseUtil: ResponseUtil

    @Test
    fun getCountries_should_return_country() {
        val country = Country(id = 1L, name = "Test Country", code = "123")
        val countryResponse = CountryResponse(id = 1L, name = "Test Country", code = "123")

        `when`(countryService.getCountries()).thenReturn(Flux.just(country))

        webTestClient.get().uri(EndpointConstant.COUNTRIES_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CountryResponse::class.java)
            .hasSize(1)
            .contains(countryResponse)
    }

    @Test
    fun getCountries_should_return_countries() {
        val country1 = Country(id = 1L, name = "Test Country", code = "123")
        val country2 = Country(id = 2L, name = "Test Country", code = "123")
        val countryResponse1 = CountryResponse(id = 1L, name = "Test Country", code = "123")
        val countryResponse2 = CountryResponse(id = 2L, name = "Test Country", code = "123")

        `when`(countryService.getCountries()).thenReturn(Flux.fromIterable(listOf(country1, country2)))

        webTestClient.get().uri(EndpointConstant.COUNTRIES_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CountryResponse::class.java)
            .hasSize(2)
            .contains(countryResponse1, countryResponse2)
    }


    @Test
    fun getCountry_should_return_country() {
        val countryResponse = CountryResponse(id = 1L, name = "Test Country", code = "123")
        val country = Country(id = 1L, name = "Test Country", code = "123")
        val countryId = 1L

        `when`(countryService.getCountry(countryId)).thenReturn(Mono.just(country))

        webTestClient.get().uri("${EndpointConstant.COUNTRIES_ENDPOINT}/$countryId")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(CountryResponse::class.java)
            .isEqualTo(countryResponse)
    }

    @Test
    fun getCountry_should_throw_not_found() {
        val countryId = 1L

        `when`(countryService.getCountry(countryId)).thenReturn(Mono.error(NotFoundException("Country", countryId)))

        webTestClient.get().uri("${EndpointConstant.COUNTRIES_ENDPOINT}/$countryId")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Country with id $countryId not found")
    }


    @Test
    fun saveCountry_should_save() {
        val countryRequest = CountryRequest(name = "New Country", code = "123")
        val countryResponse = CountryResponse(id = 1L, name = "New Country", code = "123")
        val savedCountry = Country(id = 1L, name = "New Country", code = "123")

        `when`(countryService.saveCountry(any()))
            .thenReturn(Mono.just(savedCountry))

        webTestClient.post().uri(EndpointConstant.COUNTRIES_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(countryRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CountryResponse::class.java)
            .isEqualTo(countryResponse)
    }

    @Test
    fun updateCountry_throw_not_found() {
        val countryId = 1L
        val countryRequest = CountryRequest("test", "123")

        `when`(countryService.updateCountry(anyLong(), any()))
            .thenReturn(Mono.error(NotFoundException("Country", countryId)))

        webTestClient.put().uri("${EndpointConstant.COUNTRIES_ENDPOINT}/$countryId")
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(countryRequest)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Country with id $countryId not found")
    }

    @Test
    fun updateCountry_should_update() {
        val countryId = 1L
        val countryRequest = CountryRequest(name = "test", code = "123")
        val updatedCountry = Country(id = countryId, name = "test", code = "1234")

        `when`(countryService.updateCountry(anyLong(), any()))
            .thenReturn(Mono.just(updatedCountry))

        webTestClient.put().uri("${EndpointConstant.COUNTRIES_ENDPOINT}/$countryId")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(countryRequest)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(CountryResponse::class.java)
            .isEqualTo(CountryResponse(id = countryId, name = "test", code = "1234"))
    }

    @Test
    fun deleteCountry_throw_not_found() {
        val countryId = 1L

        `when`(countryService.deleteCountry(anyLong()))
            .thenReturn(Mono.error(NotFoundException("Country", countryId)))

        webTestClient.delete().uri("${EndpointConstant.COUNTRIES_ENDPOINT}/$countryId")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Country with id $countryId not found")
    }

    @Test
    fun deleteCountry_should_delete() {
        val countryId = 1L

        `when`(countryService.deleteCountry(anyLong()))
            .thenReturn(Mono.empty())

        webTestClient.delete().uri("${EndpointConstant.COUNTRIES_ENDPOINT}/$countryId")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.message").isEqualTo("Country deleted successfully")
    }
}