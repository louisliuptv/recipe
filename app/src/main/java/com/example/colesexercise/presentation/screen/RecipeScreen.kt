package com.example.colesexercise.presentation.screen

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import com.example.colesexercise.presentation.RecipeViewModelStore

@Composable
fun RecipeScreen(
    state: RecipeViewModelStore.State,
    onEvent: (RecipeViewModelStore.Event) -> Unit,
    effect: kotlinx.coroutines.flow.Flow<RecipeViewModelStore.Effect>,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(RecipeViewModelStore.KEY) {
        effect.collect { eff ->
            when (eff) {
                is RecipeViewModelStore.Effect.ShowError -> {
                    snackbarHostState.showSnackbar(eff.message)
                }
                is RecipeViewModelStore.Effect.NavigateBack -> {
                }
            }
        }
    }

    if (state.showDetail && !isLandscape) {
        BackHandler { onEvent(RecipeViewModelStore.Event.NavigateBack) }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("loading_indicator")
                    )
                }

                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("error_text")
                    )
                }

                else -> {
                    if (isLandscape) {
                        if (state.showDetail && state.selectedRecipe != null) {
                            RecipeDetailScreen(recipe = state.selectedRecipe)
                        } else {
                            RecipeListScreen(
                                recipes = state.recipes,
                                onRecipeClick = { recipe ->
                                    onEvent(RecipeViewModelStore.Event.SelectRecipe(recipe))
                                }
                            )
                        }
                    } else if (state.showDetail && state.selectedRecipe != null) {
                        RecipeDetailScreen(recipe = state.selectedRecipe)
                    } else {
                        state.recipes.firstOrNull()?.let { firstRecipe ->
                            RecipeDetailScreen(recipe = firstRecipe)
                        }
                    }
                }
            }
        }
    }
}
