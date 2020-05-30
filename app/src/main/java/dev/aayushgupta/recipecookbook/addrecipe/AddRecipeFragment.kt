package dev.aayushgupta.recipecookbook.addrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.domain.FlavorType
import dev.aayushgupta.recipecookbook.data.domain.RecipeType
import dev.aayushgupta.recipecookbook.data.domain.TimeUnit
import dev.aayushgupta.recipecookbook.data.repository.DefaultRecipeRepository
import dev.aayushgupta.recipecookbook.databinding.FragmentAddrecipeBinding
import timber.log.Timber

class AddRecipeFragment: Fragment() {

    private lateinit var fragmentAddrecipeBinding: FragmentAddrecipeBinding

    private val args: AddRecipeFragmentArgs by navArgs()

    private val viewModel by viewModels<AddRecipeViewModel> {
        AddRecipeViewModelFactory(DefaultRecipeRepository.getRepository(requireActivity().application))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_addrecipe, container, false)
        fragmentAddrecipeBinding = FragmentAddrecipeBinding.bind(root).apply {
            viewmodel = viewModel
        }

        fragmentAddrecipeBinding.lifecycleOwner = this.viewLifecycleOwner
        return fragmentAddrecipeBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSnackbar()
        setupAutoFills()
        viewModel.start(args.recipeId)
        setupIngredientLists()
        //setupNavigation()
        // viemodel setup
    }

    private fun setupSnackbar() {
        //view?.setupSnackbar(this)
    }

    private fun setupAutoFills() {
        // setup flavor items
        val flavorItems = FlavorType.values().map { getString(it.displayId) }
        val flavorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, flavorItems)
        fragmentAddrecipeBinding.addrecipeFlavorAutocomplete.setAdapter(flavorAdapter)

        // setup type items
        val typeItems = RecipeType.values().map { getString(it.displayId) }
        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, typeItems)
        fragmentAddrecipeBinding.addrecipeTypeAutocomplete.setAdapter(typeAdapter)

        // setup time unit
        val timeItems = TimeUnit.values().map { getString(it.displayId) }
        val timeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, timeItems)
        fragmentAddrecipeBinding.addrecipeTimeUnitAutocomplete.setAdapter(timeAdapter)
    }

    private fun setupIngredientLists() {
        if (viewModel.isNewRecipe) {
            Timber.d("New recipe")
            fragmentAddrecipeBinding.addrecipeIngredientNameTextinputLayout.setEndIconOnClickListener {
                Timber.d("On End click")
                if (validateCurrentIngredientSet()) {
                    fragmentAddrecipeBinding.addrecipeIngredientNameTextinputLayout.setEndIconDrawable(R.drawable.ic_cross_circle_outline_black_24dp)

                    // create new qty field

                    // create new unit field

                    // create new name field

                    // set constraints

                    // set click listener

                    // add view to container

                    fragmentAddrecipeBinding.executePendingBindings()
                }
            }
        }
    }

    private fun validateCurrentIngredientSet(): Boolean {
        return true
    }
}