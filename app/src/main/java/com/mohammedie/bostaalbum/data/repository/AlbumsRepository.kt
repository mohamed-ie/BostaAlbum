package com.mohammedie.bostaalbum.data.repository

import com.mohammedie.bostaalbum.data.model.Album
import com.mohammedie.bostaalbum.data.model.Photo
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    fun getAlbums(userId: Int): Flow<List<Album>>
    fun getPhotos(albumId: Int): Flow<List<Photo>>
}