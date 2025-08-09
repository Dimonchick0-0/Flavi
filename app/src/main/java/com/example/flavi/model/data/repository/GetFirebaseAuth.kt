package com.example.flavi.model.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

object GetFirebaseAuth {

    fun getIdUser(): String {
        val user = Firebase.auth.currentUser
        return user?.uid!!
    }

}