package com.techcognics.procuremasster.presentation.designsystem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun AppLinkText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Color(0xFF1565C0),
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline,
        modifier = modifier
    )
}
