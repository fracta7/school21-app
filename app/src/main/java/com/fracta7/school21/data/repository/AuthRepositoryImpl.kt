package com.fracta7.school21.data.repository

// AuthRepository.kt
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.fracta7.school21.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
  private val client: HttpClient,
  @ApplicationContext private val context: Context
): AuthRepository {
  // Initialize EncryptedSharedPreferences
  private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
  private val sharedPrefs = EncryptedSharedPreferences.create(
    "secure_prefs",
    masterKeyAlias,
    context,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
  )

  // Data classes for token responses
  @Serializable
  data class TokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("refresh_expires_in") val refreshExpiresIn: Long,
    @SerialName("token_type") val tokenType: String
  )

  // Authenticate and retrieve tokens
  override suspend fun authenticate(username: String, password: String): Boolean {
    return try {
      val response: TokenResponse = client.post("https://auth.sberclass.ru/auth/realms/EduPowerKeycloak/protocol/openid-connect/token") {
        contentType(ContentType.Application.FormUrlEncoded)
        setBody(
          listOf(
            "username" to username,
            "password" to password,
            "grant_type" to "password",
            "client_id" to "s21-open-api"
          ).formUrlEncode()
        )
      }.body()

      // Store tokens securely
      storeTokens(response.accessToken, response.refreshToken)
      true
    } catch (e: Exception) {
      // Handle exceptions (e.g., network errors, authentication failures)
      false
    }
  }

  // Refresh the access token using the refresh token
  override suspend fun refreshAccessToken(): Boolean {
    val refreshToken = getRefreshToken() ?: return false

    return try {
      val response: TokenResponse = client.post("https://auth.sberclass.ru/auth/realms/EduPowerKeycloak/protocol/openid-connect/token") {
        contentType(ContentType.Application.FormUrlEncoded)
        setBody(
          listOf(
            "grant_type" to "refresh_token",
            "refresh_token" to refreshToken,
            "client_id" to "s21-open-api"
          ).formUrlEncode()
        )
      }.body()

      // Update stored tokens
      storeTokens(response.accessToken, response.refreshToken)
      true
    } catch (e: Exception) {
      // Handle exceptions
      false
    }
  }

  // Store tokens in encrypted storage
  private fun storeTokens(accessToken: String, refreshToken: String) {
    sharedPrefs.edit()
      .putString("access_token", accessToken)
      .putString("refresh_token", refreshToken)
      .apply()
  }

  // Retrieve access token
  override fun getAccessToken(): String? {
    return sharedPrefs.getString("access_token", null)
  }

  // Retrieve refresh token
  private fun getRefreshToken(): String? {
    return sharedPrefs.getString("refresh_token", null)
  }

  // Clear tokens (e.g., on logout)
  override fun clearTokens() {
    sharedPrefs.edit().clear().apply()
  }
}
