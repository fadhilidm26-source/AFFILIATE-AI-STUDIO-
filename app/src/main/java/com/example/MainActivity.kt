package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.BottomNavBar
import com.example.ui.components.TopStudioBar
import com.example.ui.components.VideoPlayerModal
import com.example.ui.screens.character.NewCharacterDialog
import com.example.ui.screens.create.CreateVideoScreen
import com.example.ui.screens.dance.DanceReferenceScreen
import com.example.ui.screens.gallery.GalleryScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.settings.SettingsDialog
import com.example.ui.screens.tools.ProductToolsScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AppNavTab
import com.example.viewmodel.StudioViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: StudioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainStudioApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainStudioApp(viewModel: StudioViewModel) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val activeProjectModal by viewModel.activeProjectModal.collectAsStateWithLifecycle()
    val showSettings by viewModel.showSettingsDialog.collectAsStateWithLifecycle()
    val showNewChar by viewModel.showNewCharacterDialog.collectAsStateWithLifecycle()
    val activeProvider by viewModel.settingsRepository.provider.collectAsStateWithLifecycle()

    // Handle Android system back gesture to navigate back to Home
    if (currentTab != AppNavTab.HOME) {
        BackHandler {
            viewModel.selectTab(AppNavTab.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopStudioBar(
                activeProvider = activeProvider,
                onOpenSettings = { viewModel.openSettings() }
            )
        },
        bottomBar = {
            BottomNavBar(
                currentTab = currentTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkBackground)
        ) {
            when (currentTab) {
                AppNavTab.HOME -> HomeScreen(viewModel = viewModel)
                AppNavTab.CREATE -> CreateVideoScreen(viewModel = viewModel)
                AppNavTab.TOOLS -> ProductToolsScreen(viewModel = viewModel)
                AppNavTab.DANCE -> DanceReferenceScreen(viewModel = viewModel)
                AppNavTab.GALLERY -> GalleryScreen(viewModel = viewModel)
            }
        }
    }

    // Fullscreen 9:16 Video Player Dialog
    activeProjectModal?.let { project ->
        VideoPlayerModal(
            project = project,
            onDismiss = { viewModel.closeProjectModal() },
            onShare = { viewModel.shareProject(it) },
            onSaveToGallery = { viewModel.saveToGallery(it) },
            onDelete = { viewModel.deleteProject(it) }
        )
    }

    // Settings Dialog
    if (showSettings) {
        SettingsDialog(
            viewModel = viewModel,
            onDismiss = { viewModel.closeSettings() }
        )
    }

    // New Character Dialog
    if (showNewChar) {
        NewCharacterDialog(
            viewModel = viewModel,
            onDismiss = { viewModel.closeNewCharacter() }
        )
    }
}
