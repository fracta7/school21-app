package com.fracta7.school21.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.fracta7.school21.domain.repository.AppRepository
import com.fracta7.school21.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
  private val repository: AppRepository
): ViewModel(){
  var state by mutableStateOf(ProfileState())

  suspend fun getParticipantProfile(login: String){
    repository.getProfile(login).collect{ result ->
      state = when(result){
        is Resource.Loading -> {
          state.copy(profile = null)
        }
        is Resource.Error -> {
          state.copy(profile = null)
        }
        is Resource.Success -> {
          state.copy(profile = result.data)
        }
      }
    }
  }

  fun getLevelInfo() {
    val xpRange = repository.getXPRange(state.profile!!.level)
    val totalXp = xpRange.end - xpRange.start
    val percentage = (state.profile!!.expValue - xpRange.start).toFloat() / totalXp.toFloat()
    state = state.copy(xpPercent = percentage)
  }

  suspend fun getWorkstation(login: String) {
    repository.getWorkstation(login).collect { result ->
      state = when (result) {
        is Resource.Success -> {
          state.copy(workstation = result.data, present = true, presentLoading = false)
        }
        is Resource.Error -> {
          state.copy(workstation = null, present = false, presentLoading = false)
        }
        is Resource.Loading -> {
          state.copy(presentLoading = true)
        }
      }
    }
  }
}