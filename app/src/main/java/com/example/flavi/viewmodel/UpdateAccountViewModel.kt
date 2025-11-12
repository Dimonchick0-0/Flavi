package com.example.flavi.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateAccountViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
): ViewModel() {

    var showIndicatorIfProfileIsUpdated by mutableStateOf(false)
    var showDialogIfProfileIsUpdated by mutableStateOf(false)

    fun getCurrentUser() = userRepositoryImpl.getCurrentUser()!!

    fun updateProfileUserName(name: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = name
            showIndicatorIfProfileIsUpdated = true
        }

        getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showIndicatorIfProfileIsUpdated = false
                showDialogIfProfileIsUpdated = true
            }
        }
    }

    fun updateProfileUserEmail(email: String) {
        val user = getCurrentUser()

        user.updateEmail(email).addOnCompleteListener { task ->
            showIndicatorIfProfileIsUpdated = true
            if (task.isSuccessful) {
                Log.d("Auth", "Dsadasdadasdsadsadaa")
                showIndicatorIfProfileIsUpdated = false
                showDialogIfProfileIsUpdated = true
            }
        }

//        try {
//
//        } catch (e: FirebaseAuthInvalidCredentialsException) {
//            Log.e("Auth", e.message!!)
//        } catch (e: FirebaseAuthRecentLoginRequiredException) {
//            Log.e("Auth", e.message!!)
//        }
    }

}
