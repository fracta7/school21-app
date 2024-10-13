package com.fracta7.school21.domain.model.participant

import com.fracta7.school21.domain.model.school.participant.Badge
import com.fracta7.school21.domain.model.school.participant.Campus

data class Profile(
  val campus: Campus,
  val className: String,
  val expToNextLevel: Int,
  val expValue: Int,
  val level: Int,
  val login: String,
  val parallelName: String,
  val status: String,
  val friendliness: Float,
  val interest: Float,
  val punctuality: Float,
  val thoroughness: Float,
  val codeReviewPoints: Int,
  val coins: Int,
  val peerReviewPoints: Int,
  val coalitionId: Int,
  val coalitionName: String,
  val rank: Int,
  val badges: List<Badge>
)
