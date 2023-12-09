package com.mohammedie.bostaalbum.di.data

import com.mohammedie.bostaalbum.data.utils.ConnectivityManagerNetworkMonitor
import com.mohammedie.bostaalbum.data.utils.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UtilsModule {

    @Singleton
    @Binds
    fun bindsNetworkMonitor(impl: ConnectivityManagerNetworkMonitor): NetworkMonitor
}