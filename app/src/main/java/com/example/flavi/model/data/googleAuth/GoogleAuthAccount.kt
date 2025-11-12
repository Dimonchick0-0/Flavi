package com.example.flavi.model.data.googleAuth

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.flavi.view.state.AuthUserState
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleAuthAccount @Inject constructor() {

    private var checkRegistered by mutableStateOf(false)
    var exceptionDataUser by mutableStateOf(false)
    private lateinit var auth: FirebaseAuth
    private val executor = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val scope = CoroutineScope(executor)

    fun launchCredentialManager(
        context: Context,
        emittedState: () -> Unit,
        showException: () -> Unit
    ) {
        val credentialManager = CredentialManager.create(context = context)
        auth = Firebase.auth

        val googleidOption = GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID).build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleidOption)
            .build()

        scope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )
                handleSignIn(
                    credential = result.credential,
                    auth = auth
                )
                if (checkRegistered) {
                    emittedState()
                }
            } catch (e: Exception) {
                Log.e("Auth", "Couldn't retrieve user's credentials: ${e.localizedMessage}")
                showException()
                // Здесь отобразить ошибку "Не удалось получить учетные данные пользователя. Попробуйте ещё раз"
            }

        }
    }

    private fun handleSignIn(credential: Credential, auth: FirebaseAuth) {

        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            firebaseAuthWithGoogle(googleIdTokenCredential.idToken, auth)
            checkRegistered = true
        } else {
            Log.d("Auth", "Credential is not of type Google ID!")
        }

    }

    private fun firebaseAuthWithGoogle(idToken: String, auth: FirebaseAuth) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth
            .signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Auth", "signInWithCredential:success")
                } else {
                    Log.w("Auth", "signInWithCredential:failure", task.exception)
                }
        }
    }

    companion object {
        private const val WEB_CLIENT_ID =
            "31241286202-pfdltvl62s3ogtoe7ce175g2fnc1rg1f.apps.googleusercontent.com"
    }
}