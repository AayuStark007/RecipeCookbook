package dev.aayushgupta.recipecookbook.data.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.aayushgupta.recipecookbook.data.domain.*
import dev.aayushgupta.recipecookbook.utils.DataAdapters
import dev.aayushgupta.recipecookbook.utils.DataAdapters.fromJsonSafe
import java.util.*

@Entity(tableName = "recipes")
data class DbRecipe @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var description: String = "",
    var type: Int = RecipeType.NONE.ordinal,
    var cuisine: String = "",
    var flavor: Int = FlavorType.NONE.ordinal,
    @ColumnInfo(name = "cooking_time") var cookingTime: String,
    var ingredients: String,
    var steps: String,
    var images: String
)

fun List<DbRecipe>.asDomainModel(): List<Recipe> {
    return map {
        it.asDomainModel()
    }
}

fun DbRecipe.asDomainModel(): Recipe {
    return Recipe(
        id = this.id,
        title = this.title,
        description = this.description,
        type = RecipeType.values()[this.type],
        cuisine = this.cuisine,
        flavor = FlavorType.values()[this.flavor],
        cookingTime = tryConvertCookingTime(this.cookingTime),
        ingredients = tryConvertIngredients(this.ingredients),
        steps = tryConvertSteps(this.steps),
        images = tryConvertImages(this.images)
    )
}

fun tryConvertCookingTime(timeStr: String): RecipeTime {
    return if (timeStr.isNotBlank()) {
        DataAdapters.timeAdapter.fromJsonSafe(timeStr)
    } else {
        RecipeTime()
    }
}

fun tryConvertIngredients(ingredientStr: String): List<Ingredient> {
    return if (ingredientStr.isNotBlank()) {
        val ingredientStrList = ingredientStr.split("|")
        if (ingredientStrList.isEmpty()) {
            listOf()
        } else {
            ingredientStrList
                .map { DataAdapters.ingredientAdapter.fromJsonSafe(it) }
                .filter { it.name.isNotBlank() }
        }
    } else {
        listOf()
    }
}

fun tryConvertSteps(stepsStr: String): List<String> {
    return if (stepsStr.isNotBlank()) {
        val stepsStrList = stepsStr.split("|")
        if (stepsStrList.isEmpty()) {
            listOf()
        } else {
            stepsStrList
                .filter { it.isNotBlank() }
        }
    } else {
        listOf()
    }
}

fun tryConvertImages(imgStr: String): List<RecipeImage> {
    return if (imgStr.isNotBlank()) {
        val imgStrList = imgStr.split("|")
        if (imgStrList.isEmpty()) {
            listOf()
        } else {
            imgStrList
                .map { DataAdapters.imageAdapter.fromJsonSafe(it) }
                .filter { it.uri != Uri.EMPTY.toString() }
        }
    } else {
        listOf()
    }
}