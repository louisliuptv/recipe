package com.example.colesexercise.data.datasource

import com.example.colesexercise.data.model.RecipesResponseDto

interface RecipeLocalDataSource {
    suspend fun getRecipes(): RecipesResponseDto
}

