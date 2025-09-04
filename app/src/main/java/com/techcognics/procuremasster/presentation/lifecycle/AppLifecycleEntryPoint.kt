package com.techcognics.procuremasster.presentation.lifecycle

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppLifecycleEntryPoint {
    fun getAppLifecycleObserver(): AppLifecycleObserver
}