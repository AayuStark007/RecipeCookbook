package dev.aayushgupta.recipecookbook.recipes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.repository.DefaultRecipeRepository
import dev.aayushgupta.recipecookbook.databinding.FragmentRecipesBinding
import dev.aayushgupta.recipecookbook.utils.EventObserver
import dev.aayushgupta.recipecookbook.utils.ItemSpacingDecoration
import dev.aayushgupta.recipecookbook.utils.setupSnackbar
import timber.log.Timber

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
            viewmodel = viewModel
            recipeList.layoutManager = GridLayoutManager(activity, 2)
            recipeList.addItemDecoration(
                ItemSpacingDecoration(space = 8, span = 2)
            )
        }
        // TODO: so something about the bizzare icon color
        setHasOptionsMenu(true)
        return fragmentRecipesBinding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_filter -> {
                showFilterPopUpMenu()
                true
            }
            R.id.menu_refresh -> {
                viewModel.loadRecipes(true)
                true
            }
            else -> false
        }

    private fun showFilterPopUpMenu() {
        TODO("Not yet implemented")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_fragment_menu, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentRecipesBinding.lifecycleOwner = this.viewLifecycleOwner
        setupSnackbar()
        setupRecipeAdapter()
        setupNavigation()
        setupFab()
    }

    private fun setupFab() {
        activity?.findViewById<FloatingActionButton>(R.id.add_recipe_fab)?.let {
            it.setOnClickListener {
                //navigateToAddNewRecipe()
                navigateToTestFragment()
            }
        }
    }

    private fun setupNavigation() {
        viewModel.openRecipeEvent.observe(viewLifecycleOwner, EventObserver {
            openRecipeDetails(it)
        })
        viewModel.newRecipeEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToAddNewRecipe()
        })
    }

    private fun navigateToAddNewRecipe() {
        val action = RecipeFragmentDirections
            .actionRecipeFragmentToAddRecipeFragment(
                null,
                "New Recipe"
            )
        findNavController().navigate(action)
    }

    private fun navigateToTestFragment() {
        val action = RecipeFragmentDirections
            .actionRecipeFragmentToTestFragment()
        findNavController().navigate(action)
    }

    private fun openRecipeDetails(recipeId: String) {
        // Do Stuff
    }

    private fun setupRecipeAdapter() {
        val viewModel = fragmentRecipesBinding.viewmodel
        if (viewModel != null) {
            recipeAdapter = RecipeAdapter()
            fragmentRecipesBinding.recipeList.adapter = recipeAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
        arguments?.let {
            // viewModel.showEditResultMessage()
        }
    }
}

