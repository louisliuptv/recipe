package com.example.colesexercise.data.repository

import com.example.colesexercise.data.datasource.RecipeLocalDataSource
import com.example.colesexercise.data.mapper.toRecipeList
import com.example.colesexercise.domain.model.Recipe
import com.example.colesexercise.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val localDataSource: RecipeLocalDataSource
) : RecipeRepository {

    override suspend fun getRecipes(): Result<List<Recipe>> {
        return try {
            val response = localDataSource.getRecipes()
            val recipes = response.recipes.toRecipeList()
            Result.success(recipes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
