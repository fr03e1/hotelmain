package com.hotelmain.dto.country

import com.hotelmain.dto.validation.OnCreate
import com.hotelmain.dto.validation.OnUpdate
import jakarta.validation.constraints.NotBlank

data class CountryRequest(
    @field:NotBlank(message = "Name cannot be blank", groups = [OnCreate::class, OnUpdate::class])
    val name: String,

    @field:NotBlank(message = "Code cannot be blank", groups = [OnCreate::class, OnUpdate::class])
    val code: String
)