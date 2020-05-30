package dev.aayushgupta.recipecookbook.data.domain

import android.net.Uri
import com.squareup.moshi.JsonClass
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.database.DbRecipe
import dev.aayushgupta.recipecookbook.utils.DataAdapters
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
    var uri: String = "",
    var isFeature: Boolean = false,
    var isLocal: Boolean = true
)

enum class TimeUnit(val displayId: Int) {
    NONE(R.string.label_none),
    MINUTES(R.string.label_time_unit_minutes),
    HOURS(R.string.label_time_unit_hours)
}

enum class MeasureUnit(val displayId: Int) {
    NONE(R.string.label_none),
    TABLE_SPOON(R.string.label_measure_table_spoon),
    TEA_SPOON(R.string.label_nmeasure_tea_spoon),
    CUP(R.string.label_measure_cups),
    GRAMS(R.string.label_measure_grams),
    LITRE(R.string.label_measure_litre),
    PINCH(R.string.label_measure_pinch)
}

enum class FlavorType(val displayId: Int) {
    NONE(R.string.label_none),
    SWEET(R.string.label_flavor_sweet),
    SAVORY(R.string.label_flavor_savory),
    SOUR(R.string.label_flavor_sour),
    SALTY(R.string.label_flavor_salty),
    FRUITY(R.string.label_flavor_fruity)
}

enum class RecipeType(val displayId: Int) {
    NONE(R.string.label_none),
    STARTER(R.string.label_recipe_starter),
    MAIN(R.string.label_recipe_main),
    DESSERT(R.string.label_recipe_dessert)
}

fun List<Recipe>.asDatabaseModel(): List<DbRecipe> {
    return map {
        it.asDatabaseModel()
    }
}

fun Recipe.asDatabaseModel(): DbRecipe {
    return DbRecipe(
        id = this.id,
        title = this.title,
        description = this.description,
        type = this.type.ordinal,
        cuisine = this.cuisine,
        flavor = this.flavor.ordinal,
        cookingTime = tryConvertCookingTime(this.cookingTime),
        ingredients = tryConvertIngredients(this.ingredients),
        steps = tryConvertSteps(this.steps),
        images = tryConvertImages(this.images)
    )
}

fun tryConvertCookingTime(cookingTime: RecipeTime): String {
    return DataAdapters.timeAdapter.toJson(cookingTime)
}

fun tryConvertIngredients(ingredients: List<Ingredient>): String {
    return ingredients.joinToString(separator = "|") {
        DataAdapters.ingredientAdapter.toJson(it)
    }
}

fun tryConvertSteps(steps: List<String>): String {
    return steps.joinToString(separator = "|")
}

fun tryConvertImages(images: List<RecipeImage>): String {
    return images.joinToString(separator = "|") {
        DataAdapters.imageAdapter.toJson(it)
    }
}