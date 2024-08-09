package com.hotelmain.dto.hotel

data class HotelResponse(
    val id: Long,
    val name: String,
    val address: String,
    val city: String,
    val country: String,
    val description: String?,
    val rating: Double?
)