package com.mohammedie.bostaalbum.presentation.screens.album.imageviewer

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import coil.compose.SubcomposeAsyncImage
import com.mohammedie.bostaalbum.R
import com.mohammedie.bostaalbum.presentation.designsystem.component.ImageErrorContent
import com.mohammedie.bostaalbum.presentation.designsystem.component.ImageLoadingContent
import com.mohammedie.bostaalbum.presentation.designsystem.icons.BostaAlbumIcons
import java.io.File

@Composable
fun ImagerViewerDialog(
    url: String,
    maxScale: Float = 3f,
    doubleTapScale: Float = 1.5f,
    minScale: Float = .5f,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var size by remember { mutableStateOf(IntSize.Zero) }
    var scale by remember { mutableFloatStateOf(1f) }
    var transformationX by remember { mutableFloatStateOf(0f) }
    var transformationY by remember { mutableFloatStateOf(0f) }
    val zoom by remember { derivedStateOf { "${((scale / 1f) * 100).toInt()}%" } }

    var drawable by remember { mutableStateOf<Drawable?>(null) }
    val isDrawableLoaded by remember { derivedStateOf { drawable != null } }

    fun updateTransformation(panChange: Offset = Offset(transformationX, transformationY)) {
        if (scale <= 1f) {
            transformationX = 0f
            transformationY = 0f
            return
        }

        val maxX = (size.width * (scale - 1)) / 2
        val minX = -maxX
        transformationX = maxOf(minX, minOf(maxX, transformationX + panChange.x))

        val maxY = (size.height * (scale - 1)) / 2
        val minY = -maxY
        transformationY = maxOf(minY, minOf(maxY, transformationY + panChange.y))

    }

    val state = rememberTransformableState { zoomChange, panChange, _ ->
        val currentScale = scale * zoomChange
        if (currentScale in minScale..maxScale)
            scale = currentScale
        updateTransformation(panChange)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.background(Color.Gray.copy(.8f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .zIndex(1f)
                    .fillMaxWidth()
            ) {

                FilledTonalIconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = BostaAlbumIcons.Close,
                        contentDescription = stringResource(id = R.string.close)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(visible = isDrawableLoaded) {
                    FilledTonalIconButton(onClick = { drawable?.let { share(context, it) } }) {
                        Icon(
                            imageVector = BostaAlbumIcons.Share,
                            contentDescription = stringResource(id = R.string.share)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .pointerInput(onDismiss) { detectTapGestures { onDismiss() } },
                contentAlignment = Alignment.Center
            ) {

                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onSizeChanged { size = it }
                        .pointerInput(Unit) {
                            detectTapGestures(onDoubleTap = {
                                scale = if (scale >= doubleTapScale) {
                                    updateTransformation()
                                    1f
                                } else doubleTapScale
                            })
                        }
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = transformationX,
                            translationY = transformationY
                        )
                        .transformable(state = state),
                    model = url,
                    error = { ImageErrorContent() },
                    loading = { ImageLoadingContent() },
                    onSuccess = { drawable = it.result.drawable },
                    contentDescription = null
                )
            }

            AnimatedVisibility(
                visible = isDrawableLoaded,
                enter = expandVertically(expandFrom = Alignment.Bottom),
                exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
            ) {
                ZoomController(
                    zoom = zoom,
                    onZoomIn = { scale = minOf(maxScale, scale + .25f) },
                    onZoomOut = {
                        scale = maxOf(minScale, scale - .25f).also { updateTransformation() }
                    })
            }
        }
    }
}

@Composable
private fun ZoomController(
    zoom: String,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit
) {
    Row(
        modifier = Modifier.zIndex(1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalIconButton(onClick = onZoomOut) {
            Icon(
                imageVector = BostaAlbumIcons.ZoomOut,
                contentDescription = stringResource(id = R.string.zoom_out)
            )
        }

        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

        Text(
            modifier = Modifier.padding(16.dp),
            text = zoom,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

        FilledTonalIconButton(onClick = onZoomIn) {
            Icon(
                imageVector = BostaAlbumIcons.ZoomIn,
                contentDescription = stringResource(id = R.string.zoom_in)
            )
        }
    }
}

private fun share(context: Context, drawable: Drawable) {
    val bitmap = drawable.toBitmap()

    val outputDir = File(context.cacheDir, "/images").apply { if (!exists()) mkdir() }
    val outputFile: File =
        File.createTempFile("shared", ".jpg", outputDir)

    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputFile.outputStream())

    val uri = FileProvider.getUriForFile(context, context.packageName, outputFile)

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/*"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        putExtra(Intent.EXTRA_STREAM, uri)
    }

    try {
        context.startActivity(
            Intent.createChooser(
                intent,
                context.getString(R.string.share_with)
            )
        )
    } catch (_: Exception) {
        Toast.makeText(context, R.string.failed_to_share, Toast.LENGTH_SHORT).show()
    }
}