package com.mohammedie.bostaalbum.presentation.screens.album

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.mohammedie.bostaalbum.data.model.Photo
import com.mohammedie.bostaalbum.presentation.designsystem.ErrorScreen
import com.mohammedie.bostaalbum.presentation.designsystem.LoadingScreen
import com.mohammedie.bostaalbum.presentation.designsystem.component.ImageLoadingContent
import com.mohammedie.bostaalbum.presentation.designsystem.theme.BostaAlbumTheme
import com.mohammedie.bostaalbum.presentation.screens.album.imageviewer.ImagerViewerDialog

@Composable
fun AlbumRoute(viewModel: AlbumViewModel = hiltViewModel(), albumTitle: String) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    var imageUrl by rememberSaveable { mutableStateOf<String?>(null) }

    AnimatedContent(targetState = uiState, label = "content animation") { uiState ->
        when (uiState) {
            AlbumUiState.Error -> ErrorScreen()

            AlbumUiState.Loading -> LoadingScreen()

            is AlbumUiState.Success -> AlbumScreen(photos = uiState.photos,
                albumTitle = albumTitle,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::changeSearchQuery,
                onItemClick = { imageUrl = it })
        }
    }

    imageUrl?.let {
        ImagerViewerDialog(
            url = it,
            onDismiss = { imageUrl = null }
        )
    }
}

@Composable
fun AlbumScreen(
    photos: List<Photo>,
    albumTitle: String,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onItemClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = albumTitle,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        OutlinedTextField(modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    tint = MaterialTheme.colorScheme.outline,
                    contentDescription = null
                )
            })

        LazyVerticalGrid(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), columns = GridCells.Fixed(3)
        ) {
            items(items = photos, key = Photo::id) { photo ->
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable(role = Role.Image, onClick = { onItemClick(photo.url) }),
                    model = photo.thumbnailUrl,
                    loading = { ImageLoadingContent() },
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAlbumScreen() {
    BostaAlbumTheme {
        AlbumScreen(
            photos = listOf(
                Photo(
                    id = 6358,
                    title = "eros",
                    url = "https://duckduckgo.com/?q=augue",
                    thumbnailUrl = "https://search.yahoo.com/search?p=mei"
                ),
                Photo(
                    id = 8977,
                    title = "duo",
                    url = "https://www.google.com/#q=noster",
                    thumbnailUrl = "https://www.google.com/#q=postea"
                )
            ),
            albumTitle = "potenti",
            searchQuery = "dictas",
            onSearchQueryChange = {},
            onItemClick = {})
    }
}