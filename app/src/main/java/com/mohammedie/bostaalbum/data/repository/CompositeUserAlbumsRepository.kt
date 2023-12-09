package com.mohammedie.bostaalbum.data.repository

import com.mohammedie.bostaalbum.data.model.UserAlbums
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CompositeUserAlbumsRepository @Inject constructor(
    private val usersRepository: UsersRepository,
    private val albumsRepository: AlbumsRepository
) : UserAlbumsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getRandomUserAlbums(): Flow<UserAlbums> = usersRepository.getUsers()
        .flatMapLatest { users ->
            val user = users.random()
            albumsRepository.getAlbums(user.id)
                .map { albums -> UserAlbums(user = user, albums = albums) }
        }
}