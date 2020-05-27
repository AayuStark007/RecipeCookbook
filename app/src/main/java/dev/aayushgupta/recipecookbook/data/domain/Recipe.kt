package dev.aayushgupta.recipecookbook.data.domain

import android.net.Uri
import com.squareup.moshi.JsonClass
import java.util.*

/*
    Domain representation of the recipe model.
    This is the data used by the UI to handle recipes.
    Needs to be converted to the database model before saving to the database.
 */
@JsonClass(generateAdapter = true)
data class Recipe(
    var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var description: String = "",
    var type: RecipeType = RecipeType.NONE,
    var cuisine: String = "",
    var flavor: FlavorType = FlavorType.NONE,
    var cookingTime: RecipeTime = RecipeTime(),
    var ingredients: List<Ingredient> = listOf(),
    var steps: List<String> = listOf(),
    var images: List<RecipeImage> = listOf()
)

@JsonClass(generateAdapter = true)
data class Ingredient(
    var name: String = "",
    var quantity: Float = 0F,
    var unit: MeasureUnit = MeasureUnit.NONE
)

@JsonClass(generateAdapter = true)
data class RecipeTime(
    var value: Float = 0F,
    var unit: TimeUnit = TimeUnit.NONE
)

@JsonClass(generateAdapter = true)
data class RecipeImage(
    var uri: String = Uri.EMPTY.toString(),
    var isFeature: Boolean = false,
    var isLocal: Boolean = true
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