package dev.aayushgupta.recipecookbook.recipedetails

import androidx.annotation.StringRes
import androidx.lifecycle.*
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.IRecipeRepository
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.utils.Event
import dev.aayushgupta.recipecookbook.utils.Result
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(private val recipeRepository: IRecipeRepository): ViewModel() {
    private val _recipeId = MutableLiveData<String>()

    private val _recipe = _recipeId.switchMap { recipeId ->
        recipeRepository.observeRecipe(recipeId).map { checkResult(it) }
    }
    val recipe: LiveData<Recipe> = _recipe

    val isDataAvailable: LiveData<Boolean> = _recipe.map { it.id != "NULL" }

    // For refresh
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _editRecipeEvent = MutableLiveData<Event<Unit>>()
    val editRecipeEvent: LiveData<Event<Unit>> = _editRecipeEvent

    private val _deleteRecipeEvent = MutableLiveData<Event<Unit>>()
    val deleteRecipeEvent: LiveData<Event<Unit>> = _deleteRecipeEvent

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    fun start(recipeId: String) {
        if (_dataLoading.value == true || recipeId == _recipeId.value) {
            return
        }
        _recipeId.value = recipeId
    }

    fun deleteRecipe() = viewModelScope.launch {
        _recipeId.value?.let {
            recipeRepository.deleteRecipe(it)
            _deleteRecipeEvent.value = Event(Unit)
        }
    }

    fun editRecipe() {
        _editRecipeEvent.value = Event(Unit)
    }

    private fun checkResult(result: Result<Recipe>): Recipe {
        return when (result) {
            is Result.Success -> {
                result.data
            }
            is Result.Loading -> {
                Recipe(id = "NULL")
            }
            else -> {
                showSnackbarMessage(R.string.error_loading_recipe)
                Recipe(id = "NULL")
            }
        }
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        _snackbarText.value = Event(message)
    }
}

@Suppress("UNCHECKED_CAST")
class RecipeDetailsViewModelFactory (
    private val recipeRepository: IRecipeRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (RecipeDetailsViewModel(recipeRepository) as T)
}
