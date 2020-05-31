package dev.aayushgupta.recipecookbook.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.aayushgupta.recipecookbook.data.IRecipeRepository

class RecipeDetailsViewModel(private val recipeRepository: IRecipeRepository): ViewModel() {

}

@Suppress("UNCHECKED_CAST")
class RecipeViewModelFactory (
    private val recipeRepository: IRecipeRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (RecipeDetailsViewModel(recipeRepository) as T)
}
