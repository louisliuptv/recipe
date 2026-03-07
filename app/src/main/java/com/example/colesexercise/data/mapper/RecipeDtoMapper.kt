package com.example.colesexercise.data.mapper

import com.example.colesexercise.data.model.RecipeDto
import com.example.colesexercise.domain.model.Recipe

private const val BASE_URL = "https://www.coles.com.au"

fun RecipeDto.toRecipe(): Recipe {
    val prepMinutes = this.recipeDetails?.prepTimeAsMinutes ?: 0
    val cookMinutes = this.recipeDetails?.cookTimeAsMinutes ?: 0

    return Recipe(
        title = this.dynamicTitle.orEmpty(),
        description = this.dynamicDescription?.trim().orEmpty(),
        thumbnailUrl = buildImageUrl(this.dynamicThumbnail),
        thumbnailAlt = this.dynamicThumbnailAlt.orEmpty(),
        serves = this.recipeDetails?.amountNumber ?: 0,
        servesLabel = this.recipeDetails?.amountLabel ?: "Serves",
        prepTime = this.recipeDetails?.prepTime.orEmpty(),
        prepTimeMinutes = prepMinutes,
        cookingTime = this.recipeDetails?.cookingTime.orEmpty(),
        cookTimeMinutes = cookMinutes,
        totalTimeMinutes = prepMinutes + cookMinutes,
        ingredients = this.ingredients
            ?.mapNotNull { it.ingredient?.trim()?.ifEmpty { null } }
            ?: emptyList()
    )
}

fun List<RecipeDto>?.toRecipeList(): List<Recipe> {
    return this?.map { it.toRecipe() } ?: emptyList()
}

private fun buildImageUrl(path: String?): String {
    if (path.isNullOrBlank()) return ""
    return "$BASE_URL$path"
}

