package com.example.dimplebooks.data.Firebase

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

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
    fun logout(){
        firebaseAuth.signOut()
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
}