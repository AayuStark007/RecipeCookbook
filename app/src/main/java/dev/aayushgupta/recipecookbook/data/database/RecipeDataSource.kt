package dev.aayushgupta.recipecookbook.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import dev.aayushgupta.recipecookbook.data.IRecipeDataSource
import dev.aayushgupta.recipecookbook.utils.Result
import dev.aayushgupta.recipecookbook.utils.Result.Success
import dev.aayushgupta.recipecookbook.utils.Result.Error
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.data.domain.asDatabaseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeDataSource internal constructor(
    private val recipeDao: RecipeDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRecipeDataSource {

    override fun observeRecipes(): LiveData<Result<List<Recipe>>> {
        return Transformations.map(recipeDao.observeRecipes()) {
            it.asDomainModel()
        }.map {
            Success(it)
        }
    }

    override fun observeRecipe(recipeId: String): LiveData<Result<Recipe>> {
        return Transformations.map(recipeDao.observeRecipeById(recipeId)){
            it.asDomainModel()
        }.map {
            Success(it)
        }
    }

    override suspend fun refreshRecipe(recipeId: String) {
        // NO-OP
    }

    override suspend fun refreshRecipes() {
        // NO-OP
    }

    override suspend fun getRecipes(): Result<List<Recipe>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(recipeDao.getRecipes().asDomainModel())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getRecipe(recipeId: String): Result<Recipe> = withContext(ioDispatcher) {
        try {
            val recipeDb = recipeDao.getRecipeById(recipeId)
            if (recipeDb != null) {
                return@withContext Success(recipeDb.asDomainModel())
            } else {
                return@withContext Error(Exception("Recipe not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun saveRecipe(recipe: Recipe) = withContext(ioDispatcher) {
        recipeDao.insertRecipe(recipe.asDatabaseModel())
    }

    override suspend fun deleteAllRecipes() = withContext(ioDispatcher) {
        recipeDao.deleteAllRecipes()
    }

    override suspend fun deleteRecipe(recipeId: String) = withContext(ioDispatcher) {
        recipeDao.deleteRecipeById(recipeId)
    }

}