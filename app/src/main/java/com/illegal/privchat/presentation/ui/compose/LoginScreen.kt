package com.illegal.privchat.presentation.ui.compose

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GoogleLogin(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick) {
        Text(
            text = "Google SignIn",
            modifier = modifier
        )
    }
}
