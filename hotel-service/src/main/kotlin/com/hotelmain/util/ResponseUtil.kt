package com.hotelmain.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono

object ResponseUtil {
    fun successfulDeletionResponse(entityName: String = "Entity"): Mono<ResponseEntity<String>> {
        return Mono.just(
            ResponseEntity.status(HttpStatus.OK).body("$entityName deleted successfully")
        )
    }
}