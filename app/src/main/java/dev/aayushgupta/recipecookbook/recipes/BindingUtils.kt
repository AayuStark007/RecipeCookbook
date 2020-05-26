package dev.aayushgupta.recipecookbook.recipes

import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.aayushgupta.recipecookbook.data.domain.Recipe

@BindingAdapter("recipeTitleText")
fun TextView.setRecipeTitleText(recipe: Recipe?) {
    recipe?.let {
        text = recipe.title
    }
}

@BindingAdapter("recipeCuisineText")
fun TextView.setRecipeCuisineText(recipe: Recipe?) {
    recipe?.let {
        text = recipe.cuisine
    }
}