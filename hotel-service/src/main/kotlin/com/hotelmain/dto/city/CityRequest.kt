package com.hotelmain.dto.city

import com.hotelmain.dto.validation.OnCreate
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class CityRequest(
    @field:NotBlank(message = "Name cannot be blank", groups = [OnCreate::class])
    val name: String,

    @field:Min(value = 1, message = "Country id must be greater than 0", groups = [OnCreate::class])
    val countryId: Long
)