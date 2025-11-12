package com.example.flavi.model.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFirebaseAuth @Inject constructor() {

    fun getIdUser(): String {
        val user = Firebase.auth.currentUser
        return user?.uid ?: ""
    }

    fun getCurrentUser() = Firebase.auth.currentUser

}