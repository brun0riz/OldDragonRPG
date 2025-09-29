package com.example.olddragonrpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.olddragonrpg.controller.CharacterCreationViewModel
import com.example.olddragonrpg.ui.theme.OldDragonRPGTheme // Use your actual theme name
import com.example.olddragonrpg.view.CharacterCreationScreen

class MainActivity : ComponentActivity() {

    // This line creates and provides the ViewModel for our activity
    private val viewModel: CharacterCreationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OldDragonRPGTheme { // This is your project's theme Composable
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // We pass the viewModel instance to our main screen
                    CharacterCreationScreen(viewModel = viewModel)
                }
            }
        }
    }
}