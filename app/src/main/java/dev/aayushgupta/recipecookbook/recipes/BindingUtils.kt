package dev.aayushgupta.recipecookbook.recipes

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import dev.aayushgupta.recipecookbook.data.domain.Recipe

@BindingAdapter("recipeImage")
fun ImageView.setRecipeImage(recipe: Recipe?) {
    recipe?.let {
        Glide.with(context)
            .load(recipe.images[0].uri)
            .into(this)
    }
}

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