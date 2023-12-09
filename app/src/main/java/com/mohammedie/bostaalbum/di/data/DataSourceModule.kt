package com.mohammedie.bostaalbum.di.data

import com.mohammedie.bostaalbum.data.network.BostaAlbumNetworkDataSource
import com.mohammedie.bostaalbum.data.network.retrofit.RetrofitBostaAlbumNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindsBostaAlbumNetworkDataSource(impl: RetrofitBostaAlbumNetwork): BostaAlbumNetworkDataSource
}