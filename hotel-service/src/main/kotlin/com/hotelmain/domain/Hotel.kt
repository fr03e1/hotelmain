package com.hotelmain.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("hotel")
data class Hotel(
    @Id
    var id: Long? = null,
    var name: String,
    var address: String,
    var city: String,
    var country: String,
    var description: String? = null,
    var rating: Double? = null,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
)