package com.fracta7.school21.domain.repository

interface AuthRepository {
  suspend fun authenticate(username: String, password: String): Boolean
  suspend fun refreshAccessToken(): Boolean
  fun getAccessToken(): String?
  fun clearTokens()
}