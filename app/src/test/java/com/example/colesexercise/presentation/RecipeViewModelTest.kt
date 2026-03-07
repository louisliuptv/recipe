package com.example.colesexercise.presentation

import app.cash.turbine.test
import com.example.colesexercise.domain.model.Recipe
import com.example.colesexercise.domain.repository.RecipeRepository
import com.example.colesexercise.domain.usecase.SortRecipesByTotalTimeUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: RecipeRepository
    private lateinit var sortRecipesByTotalTimeUseCase: SortRecipesByTotalTimeUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        sortRecipesByTotalTimeUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading false with empty data`() = runTest {
        coEvery { repository.getRecipes() } returns Result.success(emptyList())
        every { sortRecipesByTotalTimeUseCase(any()) } returns emptyList()

        val viewModel = RecipeViewModel(repository, sortRecipesByTotalTimeUseCase)

        val initialState = viewModel.viewState.value
        assertFalse(initialState.isLoading)
        assertTrue(initialState.recipes.isEmpty())
        assertNull(initialState.selectedRecipe)
        assertFalse(initialState.showDetail)
        assertNull(initialState.error)
    }

    @Test
    fun `load recipes success updates state with sorted recipes`() = runTest {
        val recipes = listOf(
            createRecipe("Recipe B", totalTime = 60),
            createRecipe("Recipe A", totalTime = 30)
        )
        val sortedRecipes = listOf(
            createRecipe("Recipe A", totalTime = 30),
            createRecipe("Recipe B", totalTime = 60)
        )
        coEvery { repository.getRecipes() } returns Result.success(recipes)
        every { sortRecipesByTotalTimeUseCase(recipes) } returns sortedRecipes

        val viewModel = RecipeViewModel(repository, sortRecipesByTotalTimeUseCase)
        advanceUntilIdle()

        val state = viewModel.viewState.value
        assertFalse(state.isLoading)
        assertEquals(2, state.recipes.size)
        assertEquals("Recipe A", state.recipes[0].title)
        assertEquals("Recipe B", state.recipes[1].title)
        assertNotNull(state.selectedRecipe)
        assertEquals("Recipe A", state.selectedRecipe?.title)
        assertFalse(state.showDetail)
        assertNull(state.error)
    }

    @Test
    fun `load recipes failure updates state with error`() = runTest {
        coEvery { repository.getRecipes() } returns Result.failure(RuntimeException("Failed to load"))

        val viewModel = RecipeViewModel(repository, sortRecipesByTotalTimeUseCase)
        advanceUntilIdle()

        val state = viewModel.viewState.value
        assertFalse(state.isLoading)
        assertEquals("Failed to load", state.error)
        assertTrue(state.recipes.isEmpty())
    }

    @Test
    fun `load recipes failure emits ShowError effect`() = runTest {
        coEvery { repository.getRecipes() } returns Result.failure(RuntimeException("Network error"))

        val viewModel = RecipeViewModel(repository, sortRecipesByTotalTimeUseCase)

        viewModel.effect.test {
            advanceUntilIdle()
            val effect = awaitItem()
            assertTrue(effect is RecipeViewModelStore.Effect.ShowError)
            assertEquals("Network error", (effect as RecipeViewModelStore.Effect.ShowError).message)
        }
    }

    @Test
    fun `select recipe updates selected recipe and shows detail`() = runTest {
        val recipes = listOf(
            createRecipe("Recipe A", totalTime = 30),
            createRecipe("Recipe B", totalTime = 60)
        )
        coEvery { repository.getRecipes() } returns Result.success(recipes)
        every { sortRecipesByTotalTimeUseCase(recipes) } returns recipes

        val viewModel = RecipeViewModel(repository, sortRecipesByTotalTimeUseCase)
        advanceUntilIdle()

        assertEquals("Recipe A", viewModel.viewState.value.selectedRecipe?.title)
        assertFalse(viewModel.viewState.value.showDetail)

        viewModel.setEvent(RecipeViewModelStore.Event.SelectRecipe(recipes[1]))
        advanceUntilIdle()

        assertEquals("Recipe B", viewModel.viewState.value.selectedRecipe?.title)
        assertTrue(viewModel.viewState.value.showDetail)
    }

    private fun createRecipe(title: String, totalTime: Int = 0): Recipe {
        return Recipe(
            title = title,
            description = "Description",
            thumbnailUrl = "https://example.com/img.jpg",
            thumbnailAlt = "Alt",
            serves = 4,
            servesLabel = "Serves",
            prepTime = "15m",
            prepTimeMinutes = 15,
            cookingTime = "${totalTime - 15}m",
            cookTimeMinutes = totalTime - 15,
            totalTimeMinutes = totalTime,
            ingredients = listOf("ingredient")
        )
    }
}


