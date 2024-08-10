package com.hotelmain.service

import com.hotelmain.domain.Country
import reactor.core.publisher.Mono

interface CountryService {
    fun getCounty(id: Long): Mono<Country>
}