package dev.aayushgupta.recipecookbook.utils

import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.data.domain.RecipeTime
import dev.aayushgupta.recipecookbook.data.domain.TimeUnit

fun provideSampleRecipes(): List<Recipe> {
    return listOf(
        Recipe(title = "Chicken Tikka", cuisine = "Indian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Paneer Tikka", cuisine = "Indian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Gobhi Tikka", cuisine = "Indian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Pasta", cuisine = "Italian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Samovar", cuisine = "Persian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Yakhni Pulao", cuisine = "Iranian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Vanilla Ice Cream", cuisine = "Contemporary",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Bhindi", cuisine = "Indian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Puliogare", cuisine = "South Indian",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Noodles", cuisine = "Chinese",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Fried Rice", cuisine = "Chinese",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES)),

        Recipe(title = "Ilish Macch", cuisine = "Bengali",
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES))

    )
}