package com.example.colesexercise.domain.usecase

import com.example.colesexercise.domain.model.Recipe
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SortRecipesByTotalTimeUseCaseTest {

    private lateinit var useCase: SortRecipesByTotalTimeUseCase

    @Before
    fun setUp() {
        useCase = SortRecipesByTotalTimeUseCase()
    }

    @Test
    fun `sort recipes ascending by total time`() {
        val recipes = listOf(
            createRecipe("Recipe C", totalTime = 50),
            createRecipe("Recipe A", totalTime = 15),
            createRecipe("Recipe B", totalTime = 30),
        )

        val result = useCase(recipes)

        assertEquals("Recipe A", result[0].title)
        assertEquals("Recipe B", result[1].title)
        assertEquals("Recipe C", result[2].title)
    }

    @Test
    fun `recipes with equal total time maintain relative order`() {
        val recipes = listOf(
            createRecipe("Recipe B", totalTime = 30),
            createRecipe("Recipe A", totalTime = 30),
        )

        val result = useCase(recipes)

        assertEquals(2, result.size)
        assertEquals(30, result[0].totalTimeMinutes)
        assertEquals(30, result[1].totalTimeMinutes)
    }

    @Test
    fun `empty list returns empty list`() {
        val result = useCase(emptyList())
        assertTrue(result.isEmpty())
    }


    @Test
    fun `mixed zero and non-zero total times sorted correctly`() {
        val recipes = listOf(
            createRecipe("Zero1", totalTime = 0),
            createRecipe("Fast", totalTime = 10),
            createRecipe("Zero2", totalTime = 0),
            createRecipe("Slow", totalTime = 90),
            createRecipe("Medium", totalTime = 30),
        )

        val result = useCase(recipes)

        assertEquals("Fast", result[0].title)
        assertEquals("Medium", result[1].title)
        assertEquals("Slow", result[2].title)
        assertEquals("Zero1", result[3].title)
        assertEquals("Zero2", result[4].title)
    }

    private fun createRecipe(
        title: String,
        totalTime: Int = 0
    ): Recipe {
        val prepMinutes = totalTime / 3
        val cookMinutes = totalTime - prepMinutes

        return Recipe(
            title = title,
            description = "Test description",
            thumbnailUrl = "https://example.com/image.jpg",
            thumbnailAlt = "Alt text",
            serves = 4,
            servesLabel = "Serves",
            prepTime = "${prepMinutes}m",
            prepTimeMinutes = prepMinutes,
            cookingTime = "${cookMinutes}m",
            cookTimeMinutes = cookMinutes,
            totalTimeMinutes = totalTime,
            ingredients = listOf("ingredient 1", "ingredient 2")
        )
    }
}

