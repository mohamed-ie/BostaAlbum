package com.mohammedie.bostaalbum.data.network

import com.mohammedie.bostaalbum.data.network.model.albums.NetworkAlbum
import com.mohammedie.bostaalbum.data.network.model.photos.NetworkPhoto
import com.mohammedie.bostaalbum.data.network.model.user.NetworkUser

interface BostaAlbumNetworkDataSource {
    suspend fun getUsers(): List<NetworkUser>
    suspend fun getAlbums(userId: Int): List<NetworkAlbum>
    suspend fun getPhotos(albumId: Int): List<NetworkPhoto>
}