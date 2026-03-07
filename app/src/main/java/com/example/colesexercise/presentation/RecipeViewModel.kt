package com.example.colesexercise.presentation

import androidx.lifecycle.viewModelScope
import com.example.colesexercise.core.MviViewModel
import com.example.colesexercise.core.MviViewModelEffect
import com.example.colesexercise.core.MviViewModelEvent
import com.example.colesexercise.core.MviViewModelState
import com.example.colesexercise.domain.model.Recipe
import com.example.colesexercise.domain.repository.RecipeRepository
import com.example.colesexercise.domain.usecase.SortRecipesByTotalTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeViewModelStore {
    companion object {
        const val KEY = "RecipeViewModel"
    }
    sealed class Event : MviViewModelEvent {
        data object LoadRecipes : Event()
        data class SelectRecipe(val recipe: Recipe) : Event()
        data object NavigateBack : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val recipes: List<Recipe> = emptyList(),
        val selectedRecipe: Recipe? = null,
        val showDetail: Boolean = false,
        val error: String? = null
    ) : MviViewModelState

    sealed class Effect : MviViewModelEffect {
        data class ShowError(val message: String) : Effect()
        data object NavigateBack : Effect()
    }
}

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val sortRecipesByTotalTimeUseCase: SortRecipesByTotalTimeUseCase
) : MviViewModel<RecipeViewModelStore.Event, RecipeViewModelStore.State, RecipeViewModelStore.Effect>() {

    override fun setInitialState(): RecipeViewModelStore.State = RecipeViewModelStore.State()

    init {
        setEvent(RecipeViewModelStore.Event.LoadRecipes)
    }

    override fun handleEvents(event: RecipeViewModelStore.Event) {
        when (event) {
            is RecipeViewModelStore.Event.LoadRecipes -> loadRecipes()
            is RecipeViewModelStore.Event.SelectRecipe -> {
                setState { copy(selectedRecipe = event.recipe, showDetail = true) }
            }
            is RecipeViewModelStore.Event.NavigateBack -> {
                setState { copy(showDetail = false) }
                setEffect { RecipeViewModelStore.Effect.NavigateBack }
            }
        }
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            repository.getRecipes().fold(
                onSuccess = { recipes ->
                    val sortedRecipes = sortRecipesByTotalTimeUseCase(recipes)
                    setState {
                        copy(
                            isLoading = false,
                            recipes = sortedRecipes,
                            selectedRecipe = sortedRecipes.firstOrNull()
                        )
                    }
                },
                onFailure = { throwable ->
                    setState {
                        copy(
                            isLoading = false,
                            error = throwable.message ?: "something wrong"
                        )
                    }
                    setEffect { RecipeViewModelStore.Effect.ShowError(throwable.message ?: "something wrong") }
                }
            )
        }
    }
}
