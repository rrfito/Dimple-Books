package com.example.dimplebooks.data.model

import com.google.firebase.auth.FirebaseUser

data class userModel(
    var userId : String? = null,
    var username : String? = null,
    var email : String? = null,
    var profileImageUrl : String? = null,
)


