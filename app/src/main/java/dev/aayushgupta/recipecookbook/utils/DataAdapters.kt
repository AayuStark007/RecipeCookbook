package dev.aayushgupta.recipecookbook.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dev.aayushgupta.recipecookbook.data.domain.Ingredient
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.data.domain.RecipeImage
import dev.aayushgupta.recipecookbook.data.domain.RecipeTime

object DataAdapters {
    private val moshi: Moshi = Moshi.Builder().build()

    val recipeAdapter: JsonAdapter<Recipe> = moshi.adapter(Recipe::class.java)
    val ingredientAdapter: JsonAdapter<Ingredient> = moshi.adapter(Ingredient::class.java)
    val timeAdapter: JsonAdapter<RecipeTime> = moshi.adapter(RecipeTime::class.java)
    val imageAdapter: JsonAdapter<RecipeImage> = moshi.adapter(RecipeImage::class.java)

    fun JsonAdapter<Recipe>.fromJsonSafe(value: String): Recipe {
        return if (value.isNotBlank()) {
            val result = try {
                fromJson(value)
            } catch (e: Throwable) {
                Recipe()
            }
            result ?: Recipe()
        } else {
            Recipe()
        }
    }

    fun JsonAdapter<Ingredient>.fromJsonSafe(value: String): Ingredient {
        return if (value.isNotBlank()) {
            val result = try {
                fromJson(value)
            } catch (e: Throwable) {
                Ingredient()
            }
            result ?: Ingredient()
        } else {
            Ingredient()
        }
    }

    fun JsonAdapter<RecipeTime>.fromJsonSafe(value: String): RecipeTime {
        return if (value.isNotBlank()) {
            val result = try {
                fromJson(value)
            } catch (e: Throwable) {
                RecipeTime()
            }
            result ?: RecipeTime()
        } else {
            RecipeTime()
        }
    }

    fun JsonAdapter<RecipeImage>.fromJsonSafe(value: String): RecipeImage {
        return if (value.isNotBlank()) {
            val result = try {
                fromJson(value)
            } catch (e: Throwable) {
                RecipeImage()
            }
            result ?: RecipeImage()
        } else {
            RecipeImage()
        }
    }
}