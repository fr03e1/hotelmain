package com.hotelmain.domain

import com.fasterxml.jackson.databind.BeanDescription
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Table("hotel")
data class Hotel(
    @Id
    val id: Long? = null,
    val name: String,
    val address: String,
    val city: String,
    val state: String? = null,
    val country: String,
    val description: String? = null,
    val rating: Double? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)