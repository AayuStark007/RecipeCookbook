package dev.aayushgupta.recipecookbook.recipes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.*
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.IRecipeRepository
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.utils.Event
import dev.aayushgupta.recipecookbook.utils.Result
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeRepository: IRecipeRepository): ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _items: LiveData<List<Recipe>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            _dataLoading.value = true
            viewModelScope.launch {
                recipeRepository.refreshRecipes()
                _dataLoading.value = false
            }
        }
        recipeRepository.observeRecipes().switchMap { filterRecipes(it) }
    }

    val items: LiveData<List<Recipe>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _currentFilteringLabel = MutableLiveData<Int>()
    val currentFilteringLabel: LiveData<Int> = _currentFilteringLabel

    private val _noItemsLabel = MutableLiveData<Int>()
    val noItemsLabel: LiveData<Int> = _noItemsLabel

    private val _noItemsIconRes = MutableLiveData<Int>()
    val noItemsIconRes: LiveData<Int> = _noItemsIconRes

    private val _addRecipeViewVisible = MutableLiveData<Boolean>()
    val addRecipeViewVisible: LiveData<Boolean> = _addRecipeViewVisible

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _openRecipeEvent = MutableLiveData<Event<String>>()
    val openRecipeEvent: LiveData<Event<String>> = _openRecipeEvent

    private val _newRecipeEvent = MutableLiveData<Event<Unit>>()
    val newRecipeEvent: LiveData<Event<Unit>> = _newRecipeEvent

    private val isDataLoadingError = MutableLiveData<Boolean>()

    private var resultMessageShown: Boolean = false

    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    init {
        setScreenState()
        loadRecipes(true)
    }

    private fun setScreenState() {
        setState(
            R.string.label_saved_recipes, R.string.no_recipes,
            R.drawable.ic_filter_list, true
        )
    }

    private fun setState(
        @StringRes labelString: Int, @StringRes noItemLabelString: Int,
        @DrawableRes noItemIconDrawable: Int, recipeAddVisible: Boolean
    ) {
        _currentFilteringLabel.value = labelString
        _noItemsLabel.value = noItemLabelString
        _noItemsIconRes.value = noItemIconDrawable
        _addRecipeViewVisible.value = recipeAddVisible
    }

    fun loadRecipes(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    fun addNewRecipe() {
        _newRecipeEvent.value = Event(Unit)
    }


     // Called by Data Binding.
    fun openRecipe(recipeId: String) {
        _openRecipeEvent.value = Event(recipeId)
    }

    fun showEditResultMessage(result: Int) {
        if (resultMessageShown) return
        when (result) {
            EDIT_RESULT_OK -> showSnackbarMessage(R.string.success_save_recipe_message)
            ADD_EDIT_RESULT_OK -> showSnackbarMessage(R.string.success_add_recipe_message)
            DELETE_RESULT_OK -> showSnackbarMessage(R.string.success_delete_recipe_message)
        }
        resultMessageShown = true
    }

    private fun filterRecipes(recipeResult: Result<List<Recipe>>): LiveData<List<Recipe>> {
        val result = MutableLiveData<List<Recipe>>()

        if (recipeResult is Result.Success) {
            isDataLoadingError.value = false
            viewModelScope.launch {
                result.value = recipeResult.data
            }
        } else {
            result.value = emptyList()
            showSnackbarMessage(R.string.loading_recipes_error)
            isDataLoadingError.value = true
        }

        return result
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }
}

@Suppress("UNCHECKED_CAST")
class RecipeViewModelFactory (
    private val recipeRepository: IRecipeRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (RecipeViewModel(recipeRepository) as T)
}