package com.hotelmain.dto.hotel

import com.hotelmain.dto.validation.OnCreate
import com.hotelmain.dto.validation.OnUpdate
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class HotelRequest(
    val id: Long? = null,

    @field:NotBlank(message = "Name cannot be blank", groups = [OnCreate::class])
    val name: String,

    @field:NotBlank(message = "Address cannot be blank", groups = [OnCreate::class])
    val address: String,

    @field:NotBlank(message = "City cannot be blank", groups = [OnCreate::class])
    val city: String,

    @field:NotBlank(message = "Country cannot be blank", groups = [OnCreate::class])
    val country: String,

    val description: String? = null,

    @field:Min(value = 0, message = "Rating cannot be less than 0", groups = [OnCreate::class, OnUpdate::class])
    @field:Max(value = 5, message = "Rating cannot be more than 5", groups = [OnCreate::class, OnUpdate::class])
    val rating: Double? = null
)