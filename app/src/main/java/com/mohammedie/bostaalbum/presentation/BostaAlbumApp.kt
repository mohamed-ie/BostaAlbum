package com.mohammedie.bostaalbum.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohammedie.bostaalbum.R
import com.mohammedie.bostaalbum.data.utils.NetworkMonitor
import com.mohammedie.bostaalbum.presentation.navigation.BostaAlbumNavHost

@Composable
fun BostaAlbumApp(networkMonitor: NetworkMonitor) {
    val isOnline by networkMonitor.isOnline.collectAsStateWithLifecycle(initialValue = true)
    val snackbarHostState = remember { SnackbarHostState() }
    val internetConnectionMessage = stringResource(R.string.offline)

    LaunchedEffect(key1 = isOnline, block = {
        if (!isOnline)
            snackbarHostState.showSnackbar(
                message = internetConnectionMessage,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BostaAlbumNavHost()
        }
    }
}