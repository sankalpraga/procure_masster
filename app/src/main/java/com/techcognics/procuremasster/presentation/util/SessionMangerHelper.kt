package com.techcognics.procuremasster.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.techcognics.procuremasster.data.local.SessionManager
import com.techcognics.procuremasster.di.SessionManagerEntryPoint
import dagger.hilt.android.EntryPointAccessors

@Composable
fun getSessionManager(): SessionManager{
    val context = LocalContext.current.applicationContext
    return EntryPointAccessors.fromApplication(context, SessionManagerEntryPoint::class.java).sessionManager()
}