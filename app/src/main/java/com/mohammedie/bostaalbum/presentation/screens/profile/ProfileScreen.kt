package com.mohammedie.bostaalbum.presentation.screens.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohammedie.bostaalbum.R
import com.mohammedie.bostaalbum.data.model.Album
import com.mohammedie.bostaalbum.data.model.User
import com.mohammedie.bostaalbum.data.model.UserAlbums
import com.mohammedie.bostaalbum.presentation.designsystem.ErrorScreen
import com.mohammedie.bostaalbum.presentation.designsystem.LoadingScreen
import com.mohammedie.bostaalbum.presentation.designsystem.theme.BostaAlbumTheme

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToAlbum: (albumId: Int, albumTitle: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AnimatedContent(targetState = uiState, label = "content animation") {
        when (it) {
            ProfileUiState.Error ->
                ErrorScreen()

            ProfileUiState.Loading ->
                LoadingScreen()

            is ProfileUiState.Success ->
                ProfileScreen(
                    userAlbums = it.userAlbums,
                    navigateToAlbum = navigateToAlbum
                )
        }
    }
}

@Composable
fun ProfileScreen(
    userAlbums: UserAlbums,
    navigateToAlbum: (albumId: Int, albumTitle: String) -> Unit
) {
    val user = userAlbums.user
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.profile),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = user.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = user.address,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {

            items(items = userAlbums.albums, key = Album::id) { album ->
                AlbumCard(album = album, onClick = { navigateToAlbum(album.id, album.title) })
            }

        }
    }
}

@Composable
private fun AlbumCard(album: Album, onClick: () -> Unit) {
    Text(
        modifier = Modifier
            .clickable(role = Role.Button, onClick = onClick)
            .padding(16.dp)
            .fillMaxWidth(),
        text = album.title
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(DividerDefaults.Thickness)
            .background(DividerDefaults.color)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    BostaAlbumTheme {
        ProfileScreen(userAlbums = UserAlbums(
            user = User(
                id = 4817,
                name = "Seymour Owen",
                address = "inciderint"
            ),
            albums = listOf(Album(id = 0, title = "sumo"))
        ), navigateToAlbum = { _, _ -> })
    }
}