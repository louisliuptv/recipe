package com.example.colesexercise.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.colesexercise.presentation.screen.RecipeScreen
import com.example.colesexercise.presentation.theme.ColesExerciseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ColesExerciseTheme {
                val viewModel: RecipeViewModel = hiltViewModel()
                val state by viewModel.viewState.collectAsState()

                RecipeScreen(
                    state = state,
                    onEvent = { event -> viewModel.setEvent(event) },
                    effect = viewModel.effect
                )
            }
        }
    }
}

