package dev.aayushgupta.recipecookbook.utils

import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.data.domain.RecipeImage
import dev.aayushgupta.recipecookbook.data.domain.RecipeTime
import dev.aayushgupta.recipecookbook.data.domain.TimeUnit
import java.util.*

fun provideSampleRecipes(width: Int = 200, height: Int = 200): List<Recipe> {
    return listOf(
        Recipe(
            title = "Chicken Tikka", cuisine = "Indian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Paneer Tikka", cuisine = "Indian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Gobhi Tikka", cuisine = "Indian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Pasta", cuisine = "Italian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Samovar", cuisine = "Persian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Yakhni Pulao", cuisine = "Iranian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Vanilla Ice Cream", cuisine = "Contemporary",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Bhindi", cuisine = "Indian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Puliogare", cuisine = "South Indian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Noodles", cuisine = "Chinese",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Fried Rice", cuisine = "Chinese",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        ),

        Recipe(
            title = "Ilish Macch", cuisine = "Bengali",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                )
            )
        )

    )
}