package dev.aayushgupta.recipecookbook.data

import java.util.*


data class Recipe(
    var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var imageUrl: String = "",
    var preparationTime: PreparationTime = PreparationTime(),
    var ingredients: List<Ingredient> = listOf(),
    var steps: List<String> = listOf(),
    var cuisine: String = "",
    var flavorType: FlavorType = FlavorType.NONE,
    var recipeType: RecipeType = RecipeType.NONE
)

data class Ingredient(
    var name: String = "",
    var quantity: Float = 0F,
    var unit: MeasureUnit = MeasureUnit.NONE
)

data class PreparationTime(
    var value: Float = 0F,
    var unit: TimeUnit = TimeUnit.NONE
)

enum class TimeUnit {
    NONE,
    MINUTES,
    HOURS
}

enum class MeasureUnit {
    NONE,
    TABLE_SPOON,
    TEA_SPOON,
    CUP,
    GRAMS,
    LITRE,
    PINCH
}

enum class FlavorType {
    NONE,
    SWEET,
    SAVORY,
    SOUR,
    SALTY,
    FRUITY
}

enum class RecipeType {
    NONE,
    STARTER,
    MAIN,
    DESSERT
}