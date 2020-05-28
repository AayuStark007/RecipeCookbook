package dev.aayushgupta.recipecookbook.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.domain.Recipe
import dev.aayushgupta.recipecookbook.data.repository.DefaultRecipeRepository
import dev.aayushgupta.recipecookbook.databinding.FragmentRecipesBinding
import dev.aayushgupta.recipecookbook.utils.ItemSpacingDecoration
import dev.aayushgupta.recipecookbook.utils.provideSampleRecipes

class RecipeFragment: Fragment() {

    private val viewModel by viewModels<RecipeViewModel> {
        RecipeViewModelFactory(DefaultRecipeRepository.getRepository(requireActivity().application))
    }

    private lateinit var fragmentRecipesBinding: FragmentRecipesBinding

    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRecipesBinding = FragmentRecipesBinding.inflate(inflater, container, false).apply {

        }

        val adapter = RecipeAdapter()
        adapter.submitRecipeList(provideSampleRecipes())

        val manager = GridLayoutManager(activity, 2)
        fragmentRecipesBinding.recipeList.layoutManager = manager
        fragmentRecipesBinding.recipeList.addItemDecoration(
            ItemSpacingDecoration(
                8,
                2
            )
        )
        fragmentRecipesBinding.recipeList.adapter = adapter

        return fragmentRecipesBinding.root
    }


}

