package com.example.dimplebooks.data.Firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.dimplebooks.data.model.userModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class FirebaseRepository(private val authService: FirebaseAuthService) {
    suspend fun login(email: String, password: String): userModel? {
        val authResult = authService.login(email, password)
        if (authResult != null) {
            return userModel(
                userId = null,
                username = authResult.displayName ?: "Unknown",
                email = authResult.email ?: "Unknown",
            )
        }
        return null
    }

    suspend fun register(email: String, password: String): Boolean {
        return authService.register(email, password)
    }

    fun getCurrentUser() = authService.getCurrentUser()
     fun Logout(context: Context){
        return authService.logout(context)
    }



    suspend fun updateUsernameAndEmail(username: String,email: String): Boolean {
        return authService.updateUsernameAndEmail(username,email)
    }
    suspend fun checkpassword(password: String) : Boolean{
        return authService.checkpassword(password)
    }
    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        return authService.getGoogleSignInClient(context)
    }

    suspend fun loginWithGoogle(data: Intent?): userModel? {
        val authResult = authService.loginWithGoogle(data)
        if (authResult != null) {
            return userModel(
                userId = authResult.uid,
                username = authResult.displayName ?: "Unknown",
                email = authResult.email ?: "Unknown",
            )
        }
        return null
    }


}