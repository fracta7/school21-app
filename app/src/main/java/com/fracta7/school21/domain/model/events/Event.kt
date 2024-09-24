package com.fracta7.school21.domain.model.events

data class Event(
    val capacity: Int,
    val description: String,
    val endDateTime: String,
    val id: Int,
    val location: String,
    val name: String,
    val organizers: List<String>,
    val registerCount: Int,
    val startDateTime: String,
    val type: String
)