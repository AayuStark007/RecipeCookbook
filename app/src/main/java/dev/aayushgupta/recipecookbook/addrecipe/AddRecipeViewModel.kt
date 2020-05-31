package dev.aayushgupta.recipecookbook.addrecipe

import androidx.databinding.BaseObservable
import androidx.lifecycle.*
import dev.aayushgupta.recipecookbook.data.IRecipeRepository
import dev.aayushgupta.recipecookbook.data.domain.FlavorType
import dev.aayushgupta.recipecookbook.data.domain.MeasureUnit
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.data.domain.RecipeType
import dev.aayushgupta.recipecookbook.utils.Event
import dev.aayushgupta.recipecookbook.utils.Result
import kotlinx.coroutines.launch
import timber.log.Timber

class AddRecipeViewModel(private val recipeRepository: IRecipeRepository): ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    // When we want to display something on snackbar
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    // When fab is clicked, we fire this event
    private val _recipeUpdatedEvent = MutableLiveData<Event<Unit>>()
    val recipeUpdatedEvent: LiveData<Event<Unit>> = _recipeUpdatedEvent

    private var recipeId: String? = null
    private var isNewRecipe: Boolean = false
    private var isDataLoaded: Boolean = false

    // Two-way databinding on some Recipe data fields
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val type = MutableLiveData<RecipeType>()
    val cuisine = MutableLiveData<String>()
    val flavor = MutableLiveData<FlavorType>()
    val cookingTimeValue = MutableLiveData<String>()
    val cookingTimeUnit = MutableLiveData<MeasureUnit>()
    val ingredients = MutableLiveData<String>()
    val steps = MutableLiveData<String>()

    fun start(recipeId: String?) {
        if (_dataLoading.value == true) {
            return
        }

        this.recipeId = recipeId
        if (recipeId == null) {
            isNewRecipe = true
            return
        }

        if (isDataLoaded) {
            return
        }

        isNewRecipe = false
        _dataLoading.value = true

        viewModelScope.launch {
            recipeRepository.getRecipe(recipeId).let { result ->
                if (result is Result.Success) {
                    onRecipeLoaded(result.data)
                } else {
                    onDataNotAvailable()
                }
            }
        }

    }

    private fun onRecipeLoaded(recipe: Recipe) {
        // populate all fields
        _dataLoading.value = false
        isDataLoaded = true
    }

    private fun onDataNotAvailable() {
        _dataLoading.value = false
    }

    // Fab is clicked
    fun saveRecipe() {
        Timber.d("Title: ${title.value}")
        Timber.d("Desc: ${description.value}")
        Timber.d("Type: ${type.value}")
        Timber.d("Flavor: ${flavor.value}")
        Timber.d("Cuisine: ${cuisine.value}")
        Timber.d("TimeVal: ${cookingTimeValue.value}")
        Timber.d("TimeUnit: ${cookingTimeUnit.value}")
        Timber.d("Ingredients: ${ingredients.value}")
        Timber.d("Steps: ${steps.value}")
    }


}

@Suppress("UNCHECKED_CAST")
class AddRecipeViewModelFactory (
    private val recipeRepository: IRecipeRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (AddRecipeViewModel(recipeRepository) as T)
}