package dev.aayushgupta.recipecookbook.recipes

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.aayushgupta.recipecookbook.data.domain.Recipe

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Recipe>?) {
    items?.let {
        (listView.adapter as RecipeAdapter).submitRecipeList(items)
    }
}