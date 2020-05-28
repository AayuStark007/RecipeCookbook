package dev.aayushgupta.recipecookbook.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import dev.aayushgupta.recipecookbook.data.IRecipeDataSource
import dev.aayushgupta.recipecookbook.data.IRecipeRepository
import dev.aayushgupta.recipecookbook.data.Result
import dev.aayushgupta.recipecookbook.data.database.RecipeDataSource
import dev.aayushgupta.recipecookbook.data.database.RecipeDatabase
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import kotlinx.coroutines.*

class DefaultRecipeRepository constructor(
    private val recipesLocalDataSource: IRecipeDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRecipeRepository {

    companion object {
        @Volatile
        private var INSTANCE: DefaultRecipeRepository? = null

        fun getRepository(app: Application): DefaultRecipeRepository {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    app,
                    RecipeDatabase::class.java, "Recipes01.db"
                ).build()
                DefaultRecipeRepository(RecipeDataSource(database.recipeDao())).also {
                    INSTANCE = it
                }
            }
        }
    }

    override suspend fun getRecipes(forceUpdate: Boolean): Result<List<Recipe>> {
        if (forceUpdate) {
            try {
                updateRecipesFromRemoteDataSource()
            } catch (e: Exception) {
                return Result.Error(e)
            }
        }
        return recipesLocalDataSource.getRecipes()
    }

    override suspend fun refreshRecipes() {
        updateRecipesFromRemoteDataSource()
    }

    override fun observeRecipes(): LiveData<Result<List<Recipe>>> {
        return recipesLocalDataSource.observeRecipes()
    }

    override fun observeRecipe(recipeId: String): LiveData<Result<Recipe>> {
        return recipesLocalDataSource.observeRecipe(recipeId)
    }

    override suspend fun refreshRecipe(recipeId: String) {
        updateRecipesFromRemoteDataSource(recipeId)
    }

    private suspend fun updateRecipesFromRemoteDataSource() {
        // TODO: Update data from remote data base
    }

    private suspend fun updateRecipesFromRemoteDataSource(recipeID: String) {
        // TODO: Update data from remote data base
    }

    override suspend fun getRecipe(recipeId: String, forceUpdate: Boolean):Result<Recipe> {
        if (forceUpdate) {
            updateRecipesFromRemoteDataSource(recipeId)
        }
        return recipesLocalDataSource.getRecipe(recipeId)
    }

    override suspend fun saveRecipe(recipe: Recipe) {
        coroutineScope {
            // TODO: launch coro to save to remote source
            launch { recipesLocalDataSource.saveRecipe(recipe) }
        }
    }

    override suspend fun deleteRecipe(recipeId: String) {
        coroutineScope {
            // TODO: launch coro to delete from remote source
            launch { recipesLocalDataSource.deleteRecipe(recipeId) }
        }
    }

    override suspend fun deleteAllRecipes() {
        withContext(ioDispatcher) {
            coroutineScope {
                // TODO: launch coro for delete from remote source
                launch { recipesLocalDataSource.deleteAllRecipes() }
            }
        }
    }

}