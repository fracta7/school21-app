package com.fracta7.school21.domain.model.error

data class Error(
    val code: String,
    val exceptionUUID: String,
    val message: String,
    val status: Int
)