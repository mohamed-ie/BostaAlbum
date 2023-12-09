package com.mohammedie.bostaalbum.presentation.screens.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohammedie.bostaalbum.presentation.navigation.Destinations
import com.mohammedie.bostaalbum.presentation.screens.profile.ProfileRoute

fun NavGraphBuilder.profileScreen(navigateToAlbum: (albumId: Int, albumTitle: String) -> Unit) {
    composable(route = Destinations.PROFILE.name) {
        ProfileRoute(navigateToAlbum = navigateToAlbum)
    }
}