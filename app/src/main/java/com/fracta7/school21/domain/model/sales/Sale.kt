package com.fracta7.school21.domain.model.sales

data class Sale(
    val progressPercentage: Int,
    val startDateTime: String,
    val status: String,
    val type: String
)