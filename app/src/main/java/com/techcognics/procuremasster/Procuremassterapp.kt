package com.techcognics.procuremasster

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.techcognics.procuremasster.presentation.lifecycle.AppLifecycleEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProcureMassterApp : Application(){
    override fun onCreate(){
        super.onCreate()

        val entryPoint = EntryPointAccessors.fromApplication(
            this,
            AppLifecycleEntryPoint::class.java
        )
        val appLifecycleObserver = entryPoint.getAppLifecycleObserver()

        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)

    }
}

