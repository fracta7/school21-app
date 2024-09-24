package com.fracta7.school21.domain.model.school.participant

data class Participant(
    val campus: Campus,
    val className: String,
    val expToNextLevel: Int,
    val expValue: Int,
    val level: Int,
    val login: String,
    val parallelName: String,
    val status: String
)