package com.example.colesexercise.domain.model

data class Recipe(
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val thumbnailAlt: String,
    val serves: Int,
    val servesLabel: String,
    val prepTime: String,
    val prepTimeMinutes: Int,
    val cookingTime: String,
    val cookTimeMinutes: Int,
    val totalTimeMinutes: Int,
    val ingredients: List<String>
)

