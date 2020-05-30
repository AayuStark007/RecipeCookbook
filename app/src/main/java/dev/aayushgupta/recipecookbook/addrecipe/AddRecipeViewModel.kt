package dev.aayushgupta.recipecookbook.addrecipe

import androidx.lifecycle.*
import dev.aayushgupta.recipecookbook.data.IRecipeRepository
import dev.aayushgupta.recipecookbook.data.domain.FlavorType
import dev.aayushgupta.recipecookbook.data.domain.MeasureUnit
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.data.domain.RecipeType
import dev.aayushgupta.recipecookbook.utils.Result
import kotlinx.coroutines.launch

class AddRecipeViewModel(private val recipeRepository: IRecipeRepository): ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private var recipeId: String? = null
    var isNewRecipe: Boolean = false
    private var isDataLoaded: Boolean = false

    // Two-way databinding on some Recipe data fields
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val type = MutableLiveData<RecipeType>()
    val cuisine = MutableLiveData<String>()
    val flavor = MutableLiveData<FlavorType>()
    val cookingTimeValue = MutableLiveData<Float>()
    val cookingTimeUnit = MutableLiveData<MeasureUnit>()

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

    }


}

@Suppress("UNCHECKED_CAST")
class AddRecipeViewModelFactory (
    private val recipeRepository: IRecipeRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (AddRecipeViewModel(recipeRepository) as T)
}