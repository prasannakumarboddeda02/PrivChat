package com.illegal.privchat.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.illegal.privchat.R
import com.illegal.privchat.presentation.ui.compose.ProfileScreen
import com.illegal.privchat.ui.theme.PrivChatTheme

class ProfileActivity : androidx.activity.ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var signInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser


        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        signInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            PrivChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val user = auth.currentUser
                    if (user != null) {
                        user.displayName?.let { ProfileScreen(userName = it, imageUri = user.photoUrl.toString()
                        ) { signOut() }
                        }
                    }
                }
            }
        }
        updateUI(currentUser)

    }

    private fun signOut() {
        auth.signOut()
        signInClient.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun getUserPhotoUrl(user: FirebaseUser?): String? {
        return if (user != null && user.photoUrl != null) {
            user.photoUrl.toString()
        } else null
    }

    /*private fun getUserName(user: FirebaseUser?): String? {
        return if (user != null) {
            user.displayName
        } else getString(R.string.anonymous)
    }*/
}