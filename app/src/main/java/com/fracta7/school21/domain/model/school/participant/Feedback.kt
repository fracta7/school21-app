package com.fracta7.school21.domain.model.school.participant

data class Feedback(
    val averageVerifierFriendliness: Float,
    val averageVerifierInterest: Float,
    val averageVerifierPunctuality: Float,
    val averageVerifierThoroughness: Float
)