package dev.aayushgupta.recipecookbook.recipes

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.Recipe
import dev.aayushgupta.recipecookbook.databinding.FragmentRecipesBinding

class RecipesFragment: Fragment() {

    private lateinit var fragmentRecipesBinding: FragmentRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRecipesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false)

        val adapter = RecipeAdapter()
        adapter.submitRecipeList(listOf(
            Recipe(),Recipe(),Recipe(),Recipe(),Recipe(),
            Recipe(),Recipe(),Recipe(),Recipe(),Recipe(),
            Recipe(),Recipe(),Recipe(),Recipe(),Recipe(),
            Recipe(),Recipe(),Recipe(),Recipe(),Recipe(),
            Recipe(),Recipe(),Recipe(),Recipe(),Recipe()))

        val manager = GridLayoutManager(activity, 2)
        fragmentRecipesBinding.recipeList.layoutManager = manager
        fragmentRecipesBinding.recipeList.addItemDecoration(ItemSpacingDecoration(8, 2))
        fragmentRecipesBinding.recipeList.adapter = adapter

        return fragmentRecipesBinding.root
    }
}

class ItemSpacingDecoration(private var space: Int, private var span: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.top = space*2

        if ((parent.getChildLayoutPosition(view)+1) % span == 0) {
            outRect.right = space
        }
    }
}