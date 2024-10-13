package com.fracta7.school21.presentation.profile

import com.fracta7.school21.domain.model.participant.Profile
import com.fracta7.school21.domain.model.school.participant.Workstation

data class ProfileState(
  var profile: Profile? = null,
  var workstation: Workstation? = null,
  var present: Boolean = false,
  var presentLoading: Boolean = true,
  var xpPercent: Float = 0.0f
)
