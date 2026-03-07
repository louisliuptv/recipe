package com.example.colesexercise.data.datasource

import android.content.Context
import com.example.colesexercise.data.model.RecipesResponseDto
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : RecipeLocalDataSource {

    override suspend fun getRecipes(): RecipesResponseDto = withContext(Dispatchers.IO) {
        val jsonString = context.assets
            .open("recipesSample.json")
            .bufferedReader()
            .use { it.readText() }
        gson.fromJson(jsonString, RecipesResponseDto::class.java)
    }
}

