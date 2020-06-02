package dev.aayushgupta.recipecookbook.addrecipe

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.aayushgupta.recipecookbook.data.domain.RecipeImage

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<RecipeImage>?) {
    items?.let {
        (listView.adapter as RecipeImageAdapter).submitList(items)
    }
}