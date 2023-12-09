package com.mohammedie.bostaalbum.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mohammedie.bostaalbum.presentation.screens.album.navigation.albumScreen
import com.mohammedie.bostaalbum.presentation.screens.album.navigation.navigateToAlbum
import com.mohammedie.bostaalbum.presentation.screens.profile.navigation.profileScreen

@Composable
fun BostaAlbumNavHost(
    startDestinations: Destinations = Destinations.PROFILE,
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(navController = navHostController, startDestination = startDestinations.name) {
        profileScreen(navHostController::navigateToAlbum)
        albumScreen()
    }
}