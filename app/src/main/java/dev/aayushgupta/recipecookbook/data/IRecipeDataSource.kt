package dev.aayushgupta.recipecookbook.data

import androidx.lifecycle.LiveData
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.utils.Result

interface IRecipeDataSource {
    fun observeRecipes(): LiveData<Result<List<Recipe>>>
    fun observeRecipe(recipeId: String): LiveData<Result<Recipe>>

    suspend fun refreshRecipe(recipeId: String)

    suspend fun refreshRecipes()

    suspend fun getRecipes(): Result<List<Recipe>>

    suspend fun getRecipe(recipeId: String): Result<Recipe>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteAllRecipes()

    suspend fun deleteRecipe(recipeId: String)
}