package com.example.colesexercise.domain.repository

import com.example.colesexercise.domain.model.Recipe

interface RecipeRepository {
    suspend fun getRecipes(): Result<List<Recipe>>
}

