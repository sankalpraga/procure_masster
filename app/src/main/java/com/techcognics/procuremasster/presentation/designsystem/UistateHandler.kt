package com.techcognics.procuremasster.presentation.designsystem

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import com.techcognics.procuremasster.presentation.base.UiState

@Composable
fun <T> UiStateHandler(
    state: UiState<T>,
    onSuccess: @Composable ((T) -> Unit)? = null,
    onRetry: (() -> Unit)? = null
) {
    when (state) {
        is UiState.Loading -> {
            // 🔄 Show fullscreen loading overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Error -> {
            // ⚠️ Show snackbar with retry
            Snackbar(
                action = {
                    onRetry?.let {
                        TextButton(onClick = it) { Text("Retry") }
                    }
                }
            ) {
                Text(text = state.message)
            }
        }

        is UiState.Success -> {
            onSuccess?.invoke(state.data)
        }

        else -> {}
    }
}
