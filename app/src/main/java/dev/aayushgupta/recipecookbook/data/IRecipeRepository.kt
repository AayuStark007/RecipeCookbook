package dev.aayushgupta.recipecookbook.data

import androidx.lifecycle.LiveData
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.utils.Result

interface IRecipeRepository {
    suspend fun getRecipes(forceUpdate: Boolean = false): Result<List<Recipe>>

    suspend fun refreshRecipes()
    fun observeRecipes(): LiveData<Result<List<Recipe>>>
    fun observeRecipe(recipeId: String): LiveData<Result<Recipe>>

    suspend fun refreshRecipe(recipeId: String)

    suspend fun getRecipe(recipeId: String, forceUpdate: Boolean = false): Result<Recipe>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipeId: String)

    suspend fun deleteAllRecipes()
}