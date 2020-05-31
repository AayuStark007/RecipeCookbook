package dev.aayushgupta.recipecookbook.addrecipe

import androidx.lifecycle.*
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.IRecipeRepository
import dev.aayushgupta.recipecookbook.data.domain.*
import dev.aayushgupta.recipecookbook.utils.Event
import dev.aayushgupta.recipecookbook.utils.Result
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

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
    val cookingTimeUnit = MutableLiveData<TimeUnit>()
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
        val currentTitle = title.value
        val currentDescription = description.value ?: ""
        val recipeType = type.value ?: RecipeType.NONE
        val currentCuisine = cuisine.value ?: ""
        val flavorType = flavor.value ?: FlavorType.NONE

        // TODO: better validation
        val timeVal = cookingTimeValue.value
        val timeUnit = cookingTimeUnit.value

        val ingredientList = ingredients.value
        val stepsList = steps.value


        if (currentTitle == null) {
            _snackbarText.value = Event(R.string.empty_title_message)
            return
        }

        if (timeVal == null || timeUnit == null || timeUnit == TimeUnit.NONE) {
            _snackbarText.value = Event(R.string.invalid_time_specified)
            return
        }

        val floatTime = timeVal.toFloatOrNull() ?: 0F
        if (floatTime <= 0) {
            _snackbarText.value = Event(R.string.invalid_time_value)
            return
        }

        if (ingredientList.isNullOrBlank()) {
            _snackbarText.value = Event(R.string.invalid_ingredients)
            return
        }
        val listIngredient: List<Ingredient> = ingredientList.split("\n").map {
            Ingredient(name = it)
        }

        if (listIngredient.isNullOrEmpty()) {
            _snackbarText.value = Event(R.string.invalid_ingredients)
            return
        }

        if (stepsList.isNullOrBlank()) {
            _snackbarText.value = Event(R.string.invalid_steps)
            return
        }

        val listStep: List<String> = stepsList.split("\n")
        if (listStep.isNullOrEmpty()) {
            _snackbarText.value = Event(R.string.invalid_steps)
            return
        }

        val currentRecipeId = recipeId
        if (isNewRecipe || currentRecipeId == null) {
            val newRecipe = Recipe(title = currentTitle, description = currentDescription,
            type = recipeType, cuisine = currentCuisine, flavor = flavorType,
            cookingTime = RecipeTime(floatTime, timeUnit), ingredients = listIngredient,
            steps = listStep, images = listOf(
                    RecipeImage(
                        uri = "https://picsum.photos/seed/${UUID.randomUUID()}/200/200",
                        isLocal = false
                    )
                ))

            createRecipe(newRecipe)
        } else {
            val updatedRecipe = Recipe(id = currentRecipeId, title = currentTitle, description = currentDescription,
                type = recipeType, cuisine = currentCuisine, flavor = flavorType,
                cookingTime = RecipeTime(floatTime, timeUnit), ingredients = listIngredient,
                steps = listStep)
            updateRecipe(updatedRecipe)
        }

    }

    private fun createRecipe(recipe: Recipe) = viewModelScope.launch {
        recipeRepository.saveRecipe(recipe)
        _recipeUpdatedEvent.value = Event(Unit)
    }

    private fun updateRecipe(recipe: Recipe) {
        if (isNewRecipe) {
            throw RuntimeException("updateRecipe() was called but recipe is new.")
        }
        viewModelScope.launch {
            recipeRepository.saveRecipe(recipe)
            _recipeUpdatedEvent.value = Event(Unit)
        }
    }


}

@Suppress("UNCHECKED_CAST")
class AddRecipeViewModelFactory (
    private val recipeRepository: IRecipeRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (AddRecipeViewModel(recipeRepository) as T)
}