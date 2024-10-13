package com.fracta7.school21.domain.repository

import com.fracta7.school21.domain.model.participant.Profile
import com.fracta7.school21.domain.model.school.participant.ExperienceRange
import com.fracta7.school21.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {
  fun getXPRange(lvl: Int): ExperienceRange

  suspend fun getProfile(login: String): Flow<Resource<Profile>>
}