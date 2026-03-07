package com.example.colesexercise.domain.usecase

import com.example.colesexercise.domain.model.Recipe

class SortRecipesByTotalTimeUseCase {

    operator fun invoke(recipes: List<Recipe>): List<Recipe> {
        return recipes.sortedWith(
            compareBy<Recipe> { it.totalTimeMinutes == 0 }
                .thenBy { it.totalTimeMinutes }
        )
    }
}

