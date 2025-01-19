package com.example.dimplebooks.data.Firebase

<<<<<<< HEAD
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
=======
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
>>>>>>> 51469decd6173ef4653f6a6de1efb2ad7a85a8ad
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
<<<<<<< HEAD
import com.google.android.gms.auth.api.Auth

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
=======
>>>>>>> 51469decd6173ef4653f6a6de1efb2ad7a85a8ad

class FirebaseAuthService {
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun login(email : String , password : String): FirebaseUser?{
        return try{
            val authResult = FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email,password)
                .await()
            authResult.user
        }catch (e:Exception){
            null
        }
    }
    suspend fun register(email: String,password: String) : Boolean{
        return try{
            firebaseAuth.createUserWithEmailAndPassword(email,password)
            true
        }catch (e: Exception){
            false
        }
    }
    fun getCurrentUser() = firebaseAuth.currentUser
<<<<<<< HEAD
    fun logout(context: Context){
        firebaseAuth.signOut()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)
        googleSignInClient.signOut()
=======
    fun logout(){
        firebaseAuth.signOut()
>>>>>>> 51469decd6173ef4653f6a6de1efb2ad7a85a8ad
    }
    suspend fun updatePhotoUser(photoUrl: String): Boolean {
        val user = firebaseAuth.currentUser ?: return false
        return try {
            val profileUpdates = userProfileChangeRequest {
                photoUri = Uri.parse(photoUrl)
            }
            user.updateProfile(profileUpdates).await()
            true
        } catch (e: Exception) {
            false
        }
    }
<<<<<<< HEAD
    @SuppressLint("SuspiciousIndentation")
    suspend fun checkpassword(password: String): Boolean{
        val user = firebaseAuth.currentUser ?: return false
        return try{

         val check = EmailAuthProvider.getCredential(user.email!!,password)
            user.reauthenticate(check).await()
            user.sendEmailVerification()
            true
        }catch (e: Exception){
            false
        }

    }


    suspend fun updateUsernameAndEmail(username: String, email: String): Boolean {
        val user = firebaseAuth.currentUser ?: return false
        return try {
                val profileUpdates = userProfileChangeRequest {
                    displayName = username
                }
                user.verifyBeforeUpdateEmail(email).await()

                user.updateProfile(profileUpdates).await()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error updating username and email", e)
            false
        }
    }
    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(com.example.dimplebooks.R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    suspend fun loginWithGoogle(data: Intent?): FirebaseUser? {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            authResult.user
        } catch (e: Exception) {
            Log.e(TAG, "Error updating username and email", e)
            null
        }
    }




=======

    suspend fun updateUsername(username: String): Boolean {
        val user = firebaseAuth.currentUser ?: return false
        return try {
            val profileUpdates = userProfileChangeRequest {
                displayName = username
            }
            user.updateProfile(profileUpdates).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateEmail(email: String): Boolean {
        val user = firebaseAuth.currentUser ?: return false
        return try {
            user.updateEmail(email).await()
            true
        } catch (e: Exception) {
            false
        }
    }
>>>>>>> 51469decd6173ef4653f6a6de1efb2ad7a85a8ad
}