package com.hotelmain.util

import com.hotelmain.dto.api.ApiResponse
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono

object ResponseUtil {
    fun successfulDeletionResponse(entityName: String = "Entity"): Mono<ResponseEntity<ApiResponse>> {
        val response = ApiResponse.Success("$entityName deleted successfully")
        return Mono.just(ResponseEntity.ok(response))
    }
}