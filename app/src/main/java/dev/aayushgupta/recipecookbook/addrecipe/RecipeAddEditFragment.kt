package dev.aayushgupta.recipecookbook.addrecipe

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import timber.log.Timber

class RecipeAddEditFragment : Fragment() {

    private lateinit var fragmentRecipeAddEditBinding: FragmentRecipeAddEditBinding

    private val args: RecipeAddEditFragmentArgs by navArgs()

    private val viewModel by viewModels<RecipeAddEditViewModel> {
        RecipeAddEditViewModelFactory(DefaultRecipeRepository.getRepository(requireActivity().application))
    }

    private lateinit var recipeImageAdapter: RecipeImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_recipe_add_edit, container, false)
        fragmentRecipeAddEditBinding = FragmentRecipeAddEditBinding.bind(root).apply {
            viewmodel = viewModel
            addrecipeImageRvContainer.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        }

        fragmentRecipeAddEditBinding.lifecycleOwner = this.viewLifecycleOwner
        return fragmentRecipeAddEditBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSnackbar()
        setupAutoFills()
        setupNavigation()
        setupRecipeImageAdapter()
        setupImageSelectionMenu()
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

    private fun setupImageSelectionMenu() {
        viewModel.addImageEvent.observe(viewLifecycleOwner, EventObserver {
            PopupMenu(requireContext(), fragmentRecipeAddEditBinding.addrecipeBtnAddImage, Gravity.START).run {
                menuInflater.inflate(R.menu.image_selection_menu, menu)

                setOnMenuItemClickListener {
                    Timber.d("Image selection item ${it.itemId}")
                    true
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) setForceShowIcon(true)
                show()
            }
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

    private fun setupRecipeImageAdapter() {
        val viewModel = fragmentRecipeAddEditBinding.viewmodel
        if (viewModel != null) {
            recipeImageAdapter = RecipeImageAdapter(viewModel)
            fragmentRecipeAddEditBinding.addrecipeImageRvContainer.adapter = recipeImageAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }
}
