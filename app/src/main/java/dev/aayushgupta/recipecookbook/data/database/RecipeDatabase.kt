package dev.aayushgupta.recipecookbook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

// TODO: Revisit export schema and version
@Database(entities = [DbRecipe::class], version = 1, exportSchema = false)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
}
