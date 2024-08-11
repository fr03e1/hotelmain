package com.hotelmain.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono

object ResponseUtil {
    const val success = "success"
    fun successfulDeletionResponse(entityName: String = "Entity"): Mono<ResponseEntity<Map<String, String>>> {
        return Mono.just(
            ResponseEntity.status(HttpStatus.OK).body(mapOf(success to "$entityName deleted successfully"))
        )
    }
}