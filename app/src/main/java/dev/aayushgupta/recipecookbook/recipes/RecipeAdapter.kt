package dev.aayushgupta.recipecookbook.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.databinding.ListItemRecipeBinding

class RecipeAdapter(private val viewModel: RecipeViewModel):
    ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    fun submitRecipeList(list: List<Recipe>?) {
        submitList(list)
    }

    class RecipeViewHolder private constructor(private val binding: ListItemRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): RecipeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemRecipeBinding.inflate(layoutInflater, parent, false)
                return RecipeViewHolder(binding)
            }
        }

        fun bind(viewModel: RecipeViewModel, item: Recipe) {
            binding.viewmodel = viewModel
            binding.recipe = item
            binding.executePendingBindings()
        }
    }
}

class RecipeDiffCallback: DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }

}