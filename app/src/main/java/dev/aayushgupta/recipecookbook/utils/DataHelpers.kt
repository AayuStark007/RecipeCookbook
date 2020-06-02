package dev.aayushgupta.recipecookbook.utils

import dev.aayushgupta.recipecookbook.data.domain.*
import java.util.*

fun getRandomRecipeImage(width: Int = 200, height: Int = 200): RecipeImage {
    return RecipeImage(
        uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
        isLocal = false
    )
}

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
            description = "Noodles", type = RecipeType.MAIN,
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            flavor = FlavorType.SAVORY,
            ingredients = listOf(
                Ingredient("pepper", 1F, MeasureUnit.TABLE_SPOON),
                Ingredient("chili", 1F, MeasureUnit.PINCH),
                Ingredient("noodles", 1F, MeasureUnit.CUP)
            ),
            steps = listOf(
                "Boil noodles",
                "add chili",
                "add peppers",
                "serve hot"
            ),
            images = listOf(
                RecipeImage(
                    uri = "https://picsum.photos/seed/${UUID.randomUUID()}/$width/$height",
                    isLocal = false
                ),
                RecipeImage("url1", isFeature = false, isLocal = false),
                RecipeImage("url2", isFeature = false, isLocal = false),
                RecipeImage("url3", isFeature = false, isLocal = false)
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