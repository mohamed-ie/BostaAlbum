package com.mohammedie.bostaalbum.data.repository

import com.mohammedie.bostaalbum.data.model.User
import com.mohammedie.bostaalbum.data.network.BostaAlbumNetworkDataSource
import com.mohammedie.bostaalbum.data.network.model.user.NetworkUser
import com.mohammedie.bostaalbum.data.network.model.user.asUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkUserRepository @Inject constructor(
    private val networkDataSource: BostaAlbumNetworkDataSource
) : UsersRepository {
    override fun getUsers(): Flow<List<User>> = flow {
        val users = networkDataSource.getUsers()
            .map(NetworkUser::asUser)
        emit(users)
    }
}