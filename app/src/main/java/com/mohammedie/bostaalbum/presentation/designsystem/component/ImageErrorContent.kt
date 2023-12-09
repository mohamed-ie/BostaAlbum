package com.mohammedie.bostaalbum.presentation.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohammedie.bostaalbum.presentation.designsystem.icons.BostaAlbumIcons

@Composable
fun ImageErrorContent() {
    Image(
        modifier = Modifier.fillMaxSize(),
        imageVector = BostaAlbumIcons.BrokenImage,
        contentDescription = null
    )
}