package com.techcognics.procuremasster.di

import com.techcognics.procuremasster.data.local.SessionManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SessionManagerEntryPoint {

    fun sessionManager(): SessionManager

}

