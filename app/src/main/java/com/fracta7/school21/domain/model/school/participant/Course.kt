package com.fracta7.school21.domain.model.school.participant

data class Course(
    val completionDateTime: String,
    val finalPercentage: Int,
    val id: Int,
    val status: String,
    val title: String
)