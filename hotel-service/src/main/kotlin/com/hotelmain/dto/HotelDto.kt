package com.hotelmain.dto

data class HotelDto(
    val name: String,
    val address: String,
    val city: String,
    val state: String? = null,
    val country: String,
    val description: String? = null,
    val rating: Double? = null
)
