package dev.aayushgupta.recipecookbook.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    @Query("SELECT * FROM RECIPES")
    fun observeRecipes(): LiveData<List<DbRecipe>>

    @Query("SELECT * FROM RECIPES WHERE entryid = :recipeId")
    fun observeRecipeById(recipeId: String): LiveData<DbRecipe>

    @Query("SELECT * FROM RECIPES")
    suspend fun getRecipes(): List<DbRecipe>

    @Query("SELECT * FROM RECIPES WHERE entryid = :recipeId")
    suspend fun getRecipeById(recipeId: String): DbRecipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: DbRecipe)

    @Update
    suspend fun updateRecipe(recipe: DbRecipe)

    @Query("DELETE FROM RECIPES WHERE entryid = :recipeId")
    suspend fun deleteRecipeById(recipeId: String)

    @Query("DELETE FROM RECIPES")
    suspend fun deleteAllRecipes()
}