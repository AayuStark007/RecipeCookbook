package dev.aayushgupta.recipecookbook.recipedetails

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.repository.DefaultRecipeRepository
import dev.aayushgupta.recipecookbook.databinding.FragmentRecipeDetailBinding
import dev.aayushgupta.recipecookbook.recipes.DELETE_RESULT_OK
import dev.aayushgupta.recipecookbook.utils.EventObserver
import dev.aayushgupta.recipecookbook.utils.setupSnackbar
import timber.log.Timber

class RecipeDetailFragment : Fragment() {

    private lateinit var fragmentRecipeDetailBinding: FragmentRecipeDetailBinding

    private val args: RecipeDetailFragmentArgs by navArgs()

    private val viewModel by viewModels<RecipeDetailsViewModel> {
        RecipeDetailsViewModelFactory(DefaultRecipeRepository.getRepository(requireActivity().application))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        fragmentRecipeDetailBinding = FragmentRecipeDetailBinding.bind(view).apply {
            viewmodel = viewModel
        }
        fragmentRecipeDetailBinding.lifecycleOwner = this.viewLifecycleOwner
        setupViewModel()
        setHasOptionsMenu(true)
        return fragmentRecipeDetailBinding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                viewModel.deleteRecipe()
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_detail_fragment_menu, menu)
    }

    private fun setupViewModel() {
        viewModel.start(args.recipeId)

        viewModel.recipe.observe(viewLifecycleOwner, Observer {
            fragmentRecipeDetailBinding.recipe = it
        })

        viewModel.isDataAvailable.observe(viewLifecycleOwner, Observer {
            Timber.d("Data Available: $it")
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSnackbar()
        setupNavigation()
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation() {
        viewModel.deleteRecipeEvent.observe(viewLifecycleOwner, EventObserver {
            val action = RecipeDetailFragmentDirections
                .actionRecipeDetailFragmentToRecipeFragment(DELETE_RESULT_OK)
            findNavController().navigate(action)
        })

        viewModel.editRecipeEvent.observe(viewLifecycleOwner, EventObserver {
            val action = RecipeDetailFragmentDirections
                .actionRecipeDetailFragmentToRecipeAddEditFragment(
                    args.recipeId,
                    resources.getString(R.string.edit_recipe)
                )
            findNavController().navigate(action)
        })
    }
}