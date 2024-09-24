package com.fracta7.school21.domain.model.project

data class Project(
    val courseId: Int,
    val description: String,
    val durationHours: Int,
    val projectId: Int,
    val startConditions: List<StartCondition>,
    val title: String,
    val type: String,
    val xp: Int
)