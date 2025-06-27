package com.example.flavi.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.flavi.presentation.navigation.NavGraph
import com.example.flavi.presentation.screens.auth.AuthUser
import com.example.flavi.presentation.screens.registration.RegistrationScreen
import com.example.flavi.presentation.ui.theme.FlaviTheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContent {
            FlaviTheme {
                NavGraph()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = Firebase.auth.currentUser
        if (currentUser == null) {
            Log.d("Auth", "Пользователь не заргестрирован")
        } else {
            Log.d("Auth", "Пользователь вошёл в систему ")
        }
    }
}