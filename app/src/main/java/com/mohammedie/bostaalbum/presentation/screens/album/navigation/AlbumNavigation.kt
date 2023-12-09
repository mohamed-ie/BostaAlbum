package com.mohammedie.bostaalbum.presentation.screens.album.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohammedie.bostaalbum.presentation.navigation.Destinations
import com.mohammedie.bostaalbum.presentation.screens.album.AlbumRoute

const val albumIdArg = "albumId"
const val albumTitleArg = "albumTitle"

class AlbumsArgs(val albumId: Int) {
    constructor(savedStateHandle: SavedStateHandle) : this(checkNotNull<String>(savedStateHandle[albumIdArg]).toInt())
}

fun NavGraphBuilder.albumScreen() {
    composable(route = "${Destinations.ALBUM.name}/{$albumIdArg}&{$albumTitleArg}") { navBackStackEntry ->
        AlbumRoute(albumTitle = checkNotNull(navBackStackEntry.arguments?.getString(albumTitleArg)))
    }
}

fun NavController.navigateToAlbum(albumId: Int, albumTitle: String) {
    navigate("${Destinations.ALBUM.name}/$albumId&$albumTitle")
}