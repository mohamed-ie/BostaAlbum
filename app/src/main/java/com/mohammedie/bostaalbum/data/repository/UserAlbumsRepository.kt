package com.mohammedie.bostaalbum.data.repository

import com.mohammedie.bostaalbum.data.model.UserAlbums
import kotlinx.coroutines.flow.Flow

interface UserAlbumsRepository {
    fun getRandomUserAlbums(): Flow<UserAlbums>
}