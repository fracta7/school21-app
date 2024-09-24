package com.fracta7.school21.domain.model.school.participant

data class Project(
    val completionDateTime: String,
    val courseId: Int,
    val finalPercentage: Int,
    val id: Int,
    val status: String,
    val teamMembers: List<TeamMember>,
    val title: String,
    val type: String
)