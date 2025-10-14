package com.techcognics.procuremasster

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.techcognics.procuremasster.data.local.SessionManager
import com.techcognics.procuremasster.presentation.lifecycle.AppLifecycleEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.jvm.java

@HiltAndroidApp
class ProcureMassterApp : Application(){


    @Inject
    lateinit var sessionManager: SessionManager


    override fun onCreate(){
        super.onCreate()

        val entryPoint = EntryPointAccessors.fromApplication(
            this,
            AppLifecycleEntryPoint::class.java
        )
        val appLifecycleObserver = entryPoint.getAppLifecycleObserver()

        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        // Use coroutine for suspend function in Application scope
        GlobalScope.launch {
            sessionManager.clearSession() // this clears DataStore!
        }
    }





}

