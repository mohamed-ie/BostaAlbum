package com.mohammedie.bostaalbum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mohammedie.bostaalbum.data.utils.NetworkMonitor
import com.mohammedie.bostaalbum.presentation.designsystem.theme.BostaAlbumTheme
import com.mohammedie.bostaalbum.presentation.BostaAlbumApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BostaAlbumTheme {
                BostaAlbumApp(networkMonitor = networkMonitor)
            }
        }
    }
}