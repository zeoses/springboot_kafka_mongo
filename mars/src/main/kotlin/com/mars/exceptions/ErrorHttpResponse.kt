package com.mars.exceptions

data class ErrorHttpResponse(
    val status: Int,
    val message: String,
    val timestamp: String
)
