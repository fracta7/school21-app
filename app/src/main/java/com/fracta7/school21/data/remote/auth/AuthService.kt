package com.fracta7.school21.data.remote.auth

// AuthService.kt
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.fracta7.school21.domain.repository.AuthRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class AuthService : Service() {

  // Inject your repository or dependencies (Dagger Hilt, Ktor, etc.)
  @Inject
  lateinit var authRepository: AuthRepository

  private val serviceJob = Job()
  private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    // Handle intent and perform tasks (e.g., token refresh or login)
    when (intent?.action) {
      "ACTION_LOGIN" -> {
        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        if (username != null && password != null) {
          login(username, password)
        }
      }
      "ACTION_REFRESH_TOKEN" -> {
        refreshAccessToken()
      }
    }

    return START_NOT_STICKY // You can adjust the return value based on the desired behavior
  }

  override fun onBind(intent: Intent?): IBinder? {
    // Return null if this is not a bound service
    return null
  }

  override fun onDestroy() {
    super.onDestroy()
    serviceJob.cancel() // Cancel coroutines when the service is destroyed
  }

  private fun login(username: String, password: String) {
    serviceScope.launch {
      val success = authRepository.authenticate(username, password)
      // Handle success or failure (e.g., send a broadcast or notify UI)
      if (success) {
        // Broadcast or notify UI
        val intent = Intent("ACTION_LOGIN_SUCCESS")
        sendBroadcast(intent)
      } else {
        // Broadcast or notify UI
        val intent = Intent("ACTION_LOGIN_FAILED")
        sendBroadcast(intent)
      }
      stopSelf() // Stop service after completing the task
    }
  }

  private fun refreshAccessToken() {
    serviceScope.launch {
      val success = authRepository.refreshAccessToken()
      if (success) {
        // Notify UI or broadcast token refresh success
        val intent = Intent("ACTION_TOKEN_REFRESH_SUCCESS")
        sendBroadcast(intent)
      } else {
        // Notify UI or broadcast token refresh failure
        val intent = Intent("ACTION_TOKEN_REFRESH_FAILED")
        sendBroadcast(intent)
      }
      stopSelf() // Stop service after completing the task
    }
  }
}
