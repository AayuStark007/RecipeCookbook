package dev.aayushgupta.recipecookbook.addrecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.aayushgupta.recipecookbook.data.domain.RecipeImage
import dev.aayushgupta.recipecookbook.databinding.ListItemImageBinding

class RecipeImageAdapter(private val viewModel: RecipeAddEditViewModel) :
    ListAdapter<RecipeImage, RecipeImageAdapter.RecipeImageViewHolder>(RecipeImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeImageViewHolder {
        return RecipeImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipeImageViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    class RecipeImageViewHolder private constructor(private val binding: ListItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): RecipeImageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemImageBinding.inflate(layoutInflater, parent, false)
                return RecipeImageViewHolder(binding)
            }
        }

        fun bind(viewModel: RecipeAddEditViewModel, item: RecipeImage) {
            binding.viewmodel = viewModel
            binding.image = item
            binding.executePendingBindings()
        }
    }
}

class RecipeImageDiffCallback : DiffUtil.ItemCallback<RecipeImage>() {
    override fun areItemsTheSame(oldItem: RecipeImage, newItem: RecipeImage): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: RecipeImage, newItem: RecipeImage): Boolean {
        return oldItem == newItem
    }

}