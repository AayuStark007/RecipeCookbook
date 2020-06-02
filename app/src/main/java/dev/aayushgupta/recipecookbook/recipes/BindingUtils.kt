package dev.aayushgupta.recipecookbook.recipes

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import dev.aayushgupta.recipecookbook.data.domain.Ingredient
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.data.domain.RecipeType
import dev.aayushgupta.recipecookbook.data.domain.TimeUnit

@BindingAdapter("recipeImage")
fun ImageView.setRecipeImage(recipe: Recipe?) {
    recipe?.let {
        if (recipe.images.isEmpty()) {
            return
        }
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
        if (recipe.cuisine.isBlank() || recipe.cuisine.isEmpty()){
            visibility = View.GONE
        } else {
            text = recipe.cuisine
        }
    }
}

@BindingAdapter("recipeType")
fun TextView.setRecipeTypeText(recipe: Recipe?) {
    recipe?.let {
        if (recipe.type == RecipeType.NONE) {
            visibility = View.GONE
        } else {
            text = context.getString(recipe.type.displayId)
        }
    }
}

// TODO: better formatting
@BindingAdapter("recipeTime")
fun TextView.setRecipeTimeText(recipe: Recipe?) {
    recipe?.let {
        val timeVal = recipe.cookingTime.value
        when (recipe.cookingTime.unit) {
            TimeUnit.HOURS -> {
                text = if (timeVal == 1F) {
                    "$timeVal Hour"
                } else {
                    "$timeVal Hours"
                }
            }
            TimeUnit.MINUTES -> {
                text = if (timeVal == 1F) {
                    "$timeVal Min."
                } else {
                    "$timeVal Mins."
                }
            }
            TimeUnit.NONE -> visibility = View.GONE
        }
    }
}

@BindingAdapter("recipeDescription")
fun TextView.setRecipeDescription(recipe: Recipe?) {
    recipe?.let {
        if (recipe.description.isBlank() || recipe.description.isEmpty()) {
            visibility = View.GONE
        } else {
            text = recipe.description
        }
    }
}

@BindingAdapter("recipeIngredients")
fun TextView.setRecipeIngredients(recipe: Recipe?) {
    recipe?.let {
        text = formatIngredientsList(recipe.ingredients)
    }
}

@BindingAdapter("recipeSteps")
fun TextView.setRecipeSteps(recipe: Recipe?) {
    recipe?.let {
        text = formatStepsList(recipe.steps)
    }
}


fun formatIngredientsList(ingredients: List<Ingredient>): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("<ul>")
        ingredients.forEach {
            val strIt = it.name
            if (strIt.isNotEmpty() && strIt.isNotBlank()) {
                append("<li>")
                append(strIt)
                append("</li>")
            }
        }
        append("</ul>")
    }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

fun formatStepsList(steps: List<String>): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("<ol>")
        steps.forEach {
            if (it.isNotEmpty() && it.isNotBlank()) {
                append("<li>")
                append(it)
                append("</li>")
            }
        }
        append("</ol>")
    }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}