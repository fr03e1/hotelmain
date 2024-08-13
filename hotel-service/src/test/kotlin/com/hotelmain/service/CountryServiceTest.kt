package com.hotelmain.service

import com.hotelmain.domain.Country
import com.hotelmain.exception.NotFoundException
import com.hotelmain.repository.CountryRepository
import com.hotelmain.service.impl.CountryServiceImpl
import com.hotelmain.service.mapping.CityMapper
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.annotation.Import
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class CountryServiceTest {
    @Mock
    private lateinit var countryRepository: CountryRepository
    @InjectMocks
    private lateinit var countryService: CountryServiceImpl

    @Test
    fun getCountry_return_country() {
        val countryId = 1L
        val country = Country(id = countryId, name = "Test Country", code = "123", updatedAt = LocalDateTime.now())
        Mockito.`when`(countryRepository.findById(countryId)).thenReturn(Mono.just(country))

        val result = countryService.getCountry(countryId)

        StepVerifier.create(result)
            .expectNext(country)
            .verifyComplete()
    }

    @Test
    fun getCountry_should_throw_notFoundException_when_country_not_found() {
        val countryId = 1L
        Mockito.`when`(countryRepository.findById(countryId)).thenReturn(Mono.empty())

        val result = countryService.getCountry(countryId)

        StepVerifier.create(result)
            .expectError(NotFoundException::class.java)
            .verify()
    }

    @Test
    fun getCountries_should_return_all_countries() {
        val countries = listOf(
            Country(id = 1L, name = "Country 1", code = "111", updatedAt = LocalDateTime.now()),
            Country(id = 2L, name = "Country 2", code = "222", updatedAt = LocalDateTime.now())
        )
        Mockito.`when`(countryRepository.findAll()).thenReturn(Flux.fromIterable(countries))

        val result = countryService.getCountries()

        StepVerifier.create(result)
            .expectNextSequence(countries)
            .verifyComplete()
    }

    @Test
    fun updateCountry_should__update_and_return_country() {
        val countryId = 1L
        val existingCountry = Country(id = countryId, name = "Old Country", code = "111", updatedAt = LocalDateTime.now())
        val updateCountry = Country(id = countryId, name = "Updated Country", code = "222", updatedAt = LocalDateTime.now())
        Mockito.`when`(countryRepository.findById(countryId)).thenReturn(Mono.just(existingCountry))
        Mockito.`when`(countryRepository.save(existingCountry)).thenReturn(Mono.just(existingCountry))

        val result = countryService.updateCountry(countryId, updateCountry)

        StepVerifier.create(result)
            .assertNext {
                assert(it.name == "Updated Country")
                assert(it.code == "222")
            }
            .verifyComplete()
    }

    @Test
    fun saveCountry_should_save_and_return_country() {
        val country = Country(id = null, name = "New Country", code = "123", updatedAt = LocalDateTime.now())
        val savedCountry = country.copy(id = 1L)
        Mockito.`when`(countryRepository.save(country)).thenReturn(Mono.just(savedCountry))

        val result = countryService.saveCountry(country)

        StepVerifier.create(result)
            .expectNext(savedCountry)
            .verifyComplete()
    }

    @Test
    fun deleteCountry_should_delete_country() {
        val countryId = 1L
        val country = Country(id = countryId, name = "Test Country", code = "123", updatedAt = LocalDateTime.now())
        Mockito.`when`(countryRepository.findById(countryId)).thenReturn(Mono.just(country))
        Mockito.`when`(countryRepository.delete(country)).thenReturn(Mono.empty())

        val result = countryService.deleteCountry(countryId)

        StepVerifier.create(result)
            .verifyComplete()
    }
}