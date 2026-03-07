package com.example.colesexercise.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.colesexercise.domain.model.Recipe

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        RecipeHeader(
            title = recipe.title,
            description = recipe.description,
            thumbnailUrl = recipe.thumbnailUrl,
            thumbnailAlt = recipe.thumbnailAlt
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        RecipeInfoSection(
            servesLabel = recipe.servesLabel,
            serves = recipe.serves,
            prepTime = recipe.prepTime,
            cookingTime = recipe.cookingTime
        )

        HorizontalDivider()

        Spacer(modifier = Modifier.height(24.dp))

        IngredientsSection(ingredients = recipe.ingredients)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun RecipeHeader(
    title: String,
    description: String,
    thumbnailUrl: String,
    thumbnailAlt: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 34.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { heading() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (description.isNotEmpty()) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (thumbnailUrl.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = thumbnailAlt.ifEmpty { title },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }
    }
}

@Composable
private fun RecipeInfoSection(
    servesLabel: String,
    serves: Int,
    prepTime: String,
    cookingTime: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .semantics {
                contentDescription = buildString {
                    append("$servesLabel $serves. ")
                    append("Prep time $prepTime. ")
                    append("Cooking time $cookingTime.")
                }
            },
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        RecipeInfoItem(
            label = servesLabel,
            value = serves.toString()
        )
        RecipeInfoItem(
            label = "Prep",
            value = prepTime
        )
        RecipeInfoItem(
            label = "Cooking",
            value = cookingTime
        )
    }
}

@Composable
private fun IngredientsSection(
    ingredients: List<String>,
    modifier: Modifier = Modifier
) {
    if (ingredients.isNotEmpty()) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { heading() }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ingredients.forEach { ingredient ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = ">",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = ingredient,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun RecipeInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
