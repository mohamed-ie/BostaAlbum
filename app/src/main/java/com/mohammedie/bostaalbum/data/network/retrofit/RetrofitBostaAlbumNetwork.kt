package com.mohammedie.bostaalbum.data.network.retrofit

import com.mohammedie.bostaalbum.BuildConfig
import com.mohammedie.bostaalbum.data.network.BostaAlbumNetworkDataSource
import com.mohammedie.bostaalbum.data.network.model.albums.NetworkAlbum
import com.mohammedie.bostaalbum.data.network.model.photos.NetworkPhoto
import com.mohammedie.bostaalbum.data.network.model.user.NetworkUser
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

private interface RetrofitBostaAlbumNetworkApi {
    @GET(value = "/users")
    suspend fun getUsers(): List<NetworkUser>

    @GET(value = "/albums")
    suspend fun getAlbums(@Query("userId") userId: Int): List<NetworkAlbum>

    @GET(value = "/photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): List<NetworkPhoto>
}

class RetrofitBostaAlbumNetwork @Inject constructor() :
    BostaAlbumNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(RetrofitBostaAlbumNetworkApi::class.java)

    override suspend fun getUsers(): List<NetworkUser> =
        networkApi.getUsers()

    override suspend fun getAlbums(userId: Int): List<NetworkAlbum> =
        networkApi.getAlbums(userId)

    override suspend fun getPhotos(albumId: Int): List<NetworkPhoto> =
        networkApi.getPhotos(albumId)
}