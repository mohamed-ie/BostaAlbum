package com.mohammedie.bostaalbum.data.repository

import com.mohammedie.bostaalbum.data.model.Album
import com.mohammedie.bostaalbum.data.model.Photo
import com.mohammedie.bostaalbum.data.network.BostaAlbumNetworkDataSource
import com.mohammedie.bostaalbum.data.network.model.albums.NetworkAlbum
import com.mohammedie.bostaalbum.data.network.model.albums.asAlbum
import com.mohammedie.bostaalbum.data.network.model.photos.NetworkPhoto
import com.mohammedie.bostaalbum.data.network.model.photos.asPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkAlbumsRepository @Inject constructor(
    private val networkDataSource: BostaAlbumNetworkDataSource
) : AlbumsRepository {
    override fun getAlbums(userId: Int): Flow<List<Album>> = flow {
        val albums = networkDataSource.getAlbums(userId)
            .map(NetworkAlbum::asAlbum)
        emit(albums)
    }

    override fun getPhotos(albumId: Int): Flow<List<Photo>> = flow {
        val photos = networkDataSource.getPhotos(albumId)
            .map(NetworkPhoto::asPhoto)
        emit(photos)
    }

}