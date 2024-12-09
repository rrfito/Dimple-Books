package com.example.dimplebooks.data.Firebase

import com.example.dimplebooks.data.model.userModel

class FirebaseRepository(private val authService: FirebaseAuthService) {
    suspend fun login(email: String, password: String): userModel? {
        val authResult = authService.login(email, password)
        if (authResult != null) {
            return userModel(
                userId = null,
                username = authResult.displayName ?: "Unknown",
                email = authResult.email ?: "Unknown",
                profileImageUrl = (authResult.photoUrl ?: "https://i.ibb.co/wyhnHry/profile.png").toString()
            )
        }
        return null
    }

    suspend fun register(email: String, password: String): Boolean {
        return authService.register(email, password)
    }

    fun getCurrentUser() = authService.getCurrentUser()
    fun Logout() = authService.logout()

    suspend fun updatePhoto(photoUrl: String): Boolean {
        return authService.updatePhotoUser(photoUrl)
    }

    suspend fun updateUsername(username: String): Boolean {
        return authService.updateUsername(username)
    }

    suspend fun updateEmail(email: String): Boolean {
        return authService.updateEmail(email)
    }
}