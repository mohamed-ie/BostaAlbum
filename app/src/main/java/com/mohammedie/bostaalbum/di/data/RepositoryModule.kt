package com.mohammedie.bostaalbum.di.data

import com.mohammedie.bostaalbum.data.repository.AlbumsRepository
import com.mohammedie.bostaalbum.data.repository.CompositeUserAlbumsRepository
import com.mohammedie.bostaalbum.data.repository.NetworkAlbumsRepository
import com.mohammedie.bostaalbum.data.repository.NetworkUserRepository
import com.mohammedie.bostaalbum.data.repository.UserAlbumsRepository
import com.mohammedie.bostaalbum.data.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun providesUsersRepository(impl: NetworkUserRepository): UsersRepository

    @Binds
    @Singleton
    fun providesAlbumsRepository(impl: NetworkAlbumsRepository): AlbumsRepository

    @Binds
    @Singleton
    fun providesUserAlbumsRepository(impl: CompositeUserAlbumsRepository): UserAlbumsRepository

}