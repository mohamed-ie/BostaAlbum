package com.mohammedie.bostaalbum.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohammedie.bostaalbum.core.Result
import com.mohammedie.bostaalbum.core.asResult
import com.mohammedie.bostaalbum.core.retryIf
import com.mohammedie.bostaalbum.data.model.UserAlbums
import com.mohammedie.bostaalbum.data.repository.UserAlbumsRepository
import com.mohammedie.bostaalbum.data.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    userAlbumsRepository: UserAlbumsRepository,
    networkMonitor: NetworkMonitor
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ProfileUiState> = userAlbumsRepository.getRandomUserAlbums()
        .asResult()
        .retryIf(networkMonitor.isOnline) { result, isOnline -> result is Result.Error && isOnline }
        .map(::asProfileUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ProfileUiState.Loading
        )
}

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val userAlbums: UserAlbums) : ProfileUiState
    data object Error : ProfileUiState
}

private fun asProfileUiState(result: Result<UserAlbums>) = when (result) {
    is Result.Error -> ProfileUiState.Error
    Result.Loading -> ProfileUiState.Loading
    is Result.Success -> ProfileUiState.Success(result.data)
}