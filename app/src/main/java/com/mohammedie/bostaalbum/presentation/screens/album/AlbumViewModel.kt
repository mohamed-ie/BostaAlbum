package com.mohammedie.bostaalbum.presentation.screens.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohammedie.bostaalbum.core.Result
import com.mohammedie.bostaalbum.core.asResult
import com.mohammedie.bostaalbum.core.retryIf
import com.mohammedie.bostaalbum.data.model.Photo
import com.mohammedie.bostaalbum.data.repository.AlbumsRepository
import com.mohammedie.bostaalbum.data.utils.NetworkMonitor
import com.mohammedie.bostaalbum.presentation.screens.album.navigation.AlbumsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    albumsRepository: AlbumsRepository,
    networkMonitor: NetworkMonitor,
) : ViewModel() {
    private val albumId = AlbumsArgs(savedStateHandle).albumId
    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    val uiState: StateFlow<AlbumUiState> = albumUiState(
        searchQuery = searchQuery,
        albumId = albumId,
        albumsRepository = albumsRepository,
        networkMonitor = networkMonitor
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AlbumUiState.Loading
    )

    fun changeSearchQuery(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }
}

private const val SEARCH_QUERY = "searchQuery"

sealed interface AlbumUiState {
    data class Success(val photos: List<Photo>) : AlbumUiState
    data object Loading : AlbumUiState
    data object Error : AlbumUiState
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun albumUiState(
    searchQuery: Flow<String>,
    albumId: Int,
    networkMonitor: NetworkMonitor,
    albumsRepository: AlbumsRepository
) =
    albumsRepository.getPhotos(albumId)
        .combine(searchQuery, ::filterPhotos)
        .asResult()
        .retryIf(networkMonitor.isOnline) { result, isOnline -> result is Result.Error && isOnline }
        .map(::asAlbumUiState)

private fun asAlbumUiState(result: Result<List<Photo>>) = when (result) {
    is Result.Error -> AlbumUiState.Error
    Result.Loading -> AlbumUiState.Loading
    is Result.Success -> AlbumUiState.Success(result.data)
}

private fun filterPhotos(photos: List<Photo>, searchQuery: String) =
    if (searchQuery.isBlank()) photos
    else photos.filter { it.title.contains(searchQuery) }
