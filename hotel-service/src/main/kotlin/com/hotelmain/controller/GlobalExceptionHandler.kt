package com.hotelmain.controller

import com.hotelmain.dto.ErrorResponse
import com.hotelmain.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.core.publisher.Mono

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): Mono<ResponseEntity<ErrorResponse>> {
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message ?: "Not Found")
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse))
    }

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleValidationExceptions(ex: WebExchangeBindException): Mono<ResponseEntity<Map<String, String?>>> {
        val errors = ex.bindingResult
            .fieldErrors
            .associateBy({ it.field }, { it.defaultMessage })
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors))
    }
}