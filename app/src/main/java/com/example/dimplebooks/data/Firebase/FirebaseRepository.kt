package com.example.dimplebooks.data.Firebase

<<<<<<< HEAD
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.dimplebooks.data.model.userModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
=======
import com.example.dimplebooks.data.model.userModel
>>>>>>> 51469decd6173ef4653f6a6de1efb2ad7a85a8ad

class FirebaseRepository(private val authService: FirebaseAuthService) {
    suspend fun login(email: String, password: String): userModel? {
        val authResult = authService.login(email, password)
        if (authResult != null) {
            return userModel(
                userId = null,
                username = authResult.displayName ?: "Unknown",
                email = authResult.email ?: "Unknown",
<<<<<<< HEAD
=======
                profileImageUrl = (authResult.photoUrl ?: "https://i.ibb.co/wyhnHry/profile.png").toString()
>>>>>>> 51469decd6173ef4653f6a6de1efb2ad7a85a8ad
            )
        }
        return null
    }

    suspend fun register(email: String, password: String): Boolean {
        return authService.register(email, password)
    }

    fun getCurrentUser() = authService.getCurrentUser()
<<<<<<< HEAD
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


=======
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
>>>>>>> 51469decd6173ef4653f6a6de1efb2ad7a85a8ad
}