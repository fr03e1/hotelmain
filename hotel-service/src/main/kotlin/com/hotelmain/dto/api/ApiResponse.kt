package com.hotelmain.dto.api


sealed class ApiResponse {
    data class Success(val message: String) : ApiResponse()
    data class Error(val message: String) : ApiResponse()
}