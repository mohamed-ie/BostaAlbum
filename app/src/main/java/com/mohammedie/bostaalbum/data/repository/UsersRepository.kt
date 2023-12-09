package com.mohammedie.bostaalbum.data.repository

import com.mohammedie.bostaalbum.data.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers(): Flow<List<User>>
}