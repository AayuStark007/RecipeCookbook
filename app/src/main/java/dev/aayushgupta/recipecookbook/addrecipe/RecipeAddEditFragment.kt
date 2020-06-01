package dev.aayushgupta.recipecookbook.addrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.domain.FlavorType
import dev.aayushgupta.recipecookbook.data.domain.RecipeType
import dev.aayushgupta.recipecookbook.data.domain.TimeUnit
import dev.aayushgupta.recipecookbook.data.repository.DefaultRecipeRepository
import dev.aayushgupta.recipecookbook.databinding.FragmentRecipeAddEditBinding
import dev.aayushgupta.recipecookbook.recipes.ADD_EDIT_RESULT_OK
import dev.aayushgupta.recipecookbook.utils.EventObserver
import dev.aayushgupta.recipecookbook.utils.setupSnackbar

class RecipeAddEditFragment : Fragment() {

    private lateinit var fragmentRecipeAddEditBinding: FragmentRecipeAddEditBinding

    private val args: RecipeAddEditFragmentArgs by navArgs()

    private val viewModel by viewModels<RecipeAddEditViewModel> {
        RecipeAddEditViewModelFactory(DefaultRecipeRepository.getRepository(requireActivity().application))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_recipe_add_edit, container, false)
        fragmentRecipeAddEditBinding = FragmentRecipeAddEditBinding.bind(root).apply {
            viewmodel = viewModel
        }

        fragmentRecipeAddEditBinding.lifecycleOwner = this.viewLifecycleOwner
        return fragmentRecipeAddEditBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSnackbar()
        setupAutoFills()
        setupNavigation()
        viewModel.start(args.recipeId)
        // viemodel setup
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation() {
        viewModel.recipeUpdatedEvent.observe(viewLifecycleOwner, EventObserver {
            val action = RecipeAddEditFragmentDirections
                .actionRecipeAddEditFragmentToRecipeFragment(ADD_EDIT_RESULT_OK)

            findNavController().navigate(action)
        })
    }

    private fun setupAutoFills() {
        // setup flavor items
        val flavorItems = FlavorType.values().map { getString(it.displayId) }
        val flavorAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, flavorItems)
        fragmentRecipeAddEditBinding.addrecipeFlavorAutocomplete.setAdapter(flavorAdapter)

        // setup type items
        val typeItems = RecipeType.values().map { getString(it.displayId) }
        val typeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, typeItems)
        fragmentRecipeAddEditBinding.addrecipeTypeAutocomplete.setAdapter(typeAdapter)

        // setup time unit
        val timeItems = TimeUnit.values().map { getString(it.displayId) }
        val timeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, timeItems)
        fragmentRecipeAddEditBinding.addrecipeTimeUnitAutocomplete.setAdapter(timeAdapter)
    }
}
