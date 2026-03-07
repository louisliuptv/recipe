package com.example.colesexercise.data.model

import com.google.gson.annotations.SerializedName

data class RecipesResponseDto(
    @SerializedName("recipes") val recipes: List<RecipeDto>?
)

data class RecipeDto(
    @SerializedName("dynamicTitle") val dynamicTitle: String?,
    @SerializedName("dynamicDescription") val dynamicDescription: String?,
    @SerializedName("dynamicThumbnail") val dynamicThumbnail: String?,
    @SerializedName("dynamicThumbnailAlt") val dynamicThumbnailAlt: String?,
    @SerializedName("recipeDetails") val recipeDetails: RecipeDetailsDto?,
    @SerializedName("ingredients") val ingredients: List<IngredientDto>?
)

data class RecipeDetailsDto(
    @SerializedName("amountLabel") val amountLabel: String?,
    @SerializedName("amountNumber") val amountNumber: Int?,
    @SerializedName("prepLabel") val prepLabel: String?,
    @SerializedName("prepTime") val prepTime: String?,
    @SerializedName("prepNote") val prepNote: String?,
    @SerializedName("cookingLabel") val cookingLabel: String?,
    @SerializedName("cookingTime") val cookingTime: String?,
    @SerializedName("cookTimeAsMinutes") val cookTimeAsMinutes: Int?,
    @SerializedName("prepTimeAsMinutes") val prepTimeAsMinutes: Int?
)

data class IngredientDto(
    @SerializedName("ingredient") val ingredient: String?
)

