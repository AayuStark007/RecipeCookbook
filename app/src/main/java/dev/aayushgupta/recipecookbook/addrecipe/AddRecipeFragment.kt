package dev.aayushgupta.recipecookbook.addrecipe

import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.domain.FlavorType
import dev.aayushgupta.recipecookbook.data.domain.RecipeType
import dev.aayushgupta.recipecookbook.data.domain.TimeUnit
import dev.aayushgupta.recipecookbook.data.repository.DefaultRecipeRepository
import dev.aayushgupta.recipecookbook.databinding.FragmentAddrecipeBinding
import timber.log.Timber

class AddRecipeFragment : Fragment() {

    private lateinit var fragmentAddrecipeBinding: FragmentAddrecipeBinding

    private val args: AddRecipeFragmentArgs by navArgs()

    private val viewModel by viewModels<AddRecipeViewModel> {
        AddRecipeViewModelFactory(DefaultRecipeRepository.getRepository(requireActivity().application))
    }

    private var ingredientList: List<IngredientContainers>? = null

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
        val flavorAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, flavorItems)
        fragmentAddrecipeBinding.addrecipeFlavorAutocomplete.setAdapter(flavorAdapter)

        // setup type items
        val typeItems = RecipeType.values().map { getString(it.displayId) }
        val typeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, typeItems)
        fragmentAddrecipeBinding.addrecipeTypeAutocomplete.setAdapter(typeAdapter)

        // setup time unit
        val timeItems = TimeUnit.values().map { getString(it.displayId) }
        val timeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, timeItems)
        fragmentAddrecipeBinding.addrecipeTimeUnitAutocomplete.setAdapter(timeAdapter)
    }

    private fun setupIngredientLists() {
        if (viewModel.isNewRecipe) {
            Timber.d("New recipe")
            val currentIngredientSet = IngredientContainers(
                fragmentAddrecipeBinding.addrecipeIngredientQtyTextinputLayout,
                fragmentAddrecipeBinding.addrecipeIngredientMeasureTextinputLayout,
                fragmentAddrecipeBinding.addrecipeIngredientNameTextinputLayout
            )
            ingredientList = ArrayList<IngredientContainers>(listOf(currentIngredientSet))
            fragmentAddrecipeBinding.addrecipeIngredientNameTextinputLayout.setEndIconOnClickListener {
                Timber.d("On End click")
                if (validateCurrentIngredientSet()) {
                    currentIngredientSet.nameField.setEndIconDrawable(R.drawable.ic_cross_circle_outline_black_24dp)
                    addNewIngredientToCurrent(
                        fragmentAddrecipeBinding.ingredientListContainer,
                        currentIngredientSet
                    )
                    fragmentAddrecipeBinding.executePendingBindings()
                }
            }
        }
    }

    private fun addNewIngredientToCurrent(
        parentContainer: ConstraintLayout,
        currentIngredientSet: IngredientContainers
    ) {
        // create new qty field
        val qtyField: TextInputLayout = createQtyField(currentIngredientSet.qtyField)
        // create new unit field
        val unitField: TextInputLayout = createUnitField(currentIngredientSet.unitField)
        // create new name field
        val nameField: TextInputLayout = createNameField(currentIngredientSet.nameField)
        // set constraints
        val constraintSet = ConstraintSet()
        constraintSet.clone(fragmentAddrecipeBinding.ingredientListContainer)

        val parentId = parentContainer.id
        val topId = currentIngredientSet.unitField.id

        // Constraint current ingredient item bottom to new item top
        constraintSet.connect(topId, ConstraintSet.BOTTOM, qtyField.id, ConstraintSet.TOP)

        // set constraints for qty field
        constraintSet.setDimensionRatio(qtyField.id, "w,2:1")
        constraintSet.connect(qtyField.id, ConstraintSet.START, parentId, ConstraintSet.START)
        constraintSet.connect(qtyField.id, ConstraintSet.BOTTOM, unitField.id, ConstraintSet.TOP)
        constraintSet.connect(qtyField.id, ConstraintSet.END, nameField.id, ConstraintSet.START)
        constraintSet.connect(qtyField.id, ConstraintSet.TOP, topId, ConstraintSet.BOTTOM)

        // set constraints for unit field
        constraintSet.setDimensionRatio(unitField.id, "w,2:1")
        constraintSet.connect(qtyField.id, ConstraintSet.END, qtyField.id, ConstraintSet.END)
        constraintSet.connect(qtyField.id, ConstraintSet.START, parentId, ConstraintSet.START)
        constraintSet.connect(qtyField.id, ConstraintSet.TOP, qtyField.id, ConstraintSet.BOTTOM)

        // set constraints for name field
        constraintSet.connect(qtyField.id, ConstraintSet.BOTTOM, unitField.id, ConstraintSet.BOTTOM)
        constraintSet.connect(qtyField.id, ConstraintSet.END, parentId, ConstraintSet.END)
        constraintSet.connect(qtyField.id, ConstraintSet.START, qtyField.id, ConstraintSet.END)
        constraintSet.connect(qtyField.id, ConstraintSet.TOP, qtyField.id, ConstraintSet.TOP)

        // set click listener
        val newCurrentSet = IngredientContainers(qtyField, unitField, nameField)
        (ingredientList as ArrayList).add(newCurrentSet)
        nameField.setEndIconOnClickListener {
            Timber.d("On End click")
            if (validateCurrentIngredientSet()) {
                nameField.setEndIconDrawable(R.drawable.ic_cross_circle_outline_black_24dp)
                addNewIngredientToCurrent(parentContainer, newCurrentSet)
                fragmentAddrecipeBinding.executePendingBindings()
            }
        }

        // add view to container
        parentContainer.addView(qtyField)
        parentContainer.addView(unitField)
        parentContainer.addView(nameField)
        constraintSet.applyTo(parentContainer)
    }

    private fun createNameField(nameField: TextInputLayout): TextInputLayout {
        val textInputLayout = TextInputLayout(
            requireContext(),
            null,
            R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox
        )
        textInputLayout.id = View.generateViewId()
        textInputLayout.endIconContentDescription =
            getString(R.string.content_description_add_ingredient)
        textInputLayout.endIconDrawable =
            requireContext().getDrawable(R.drawable.ic_add_circle_outline_black_24dp)
        textInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM

        val textInputEditText = TextInputEditText(requireContext())
        textInputEditText.hint = getString(R.string.addrecipe_hint_ingredient_name)
        textInputEditText.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
        textInputEditText.setTextAppearance(
            requireContext(),
            R.style.TextAppearance_AppCompat_Small
        )
        textInputEditText.typeface = Typeface.DEFAULT_BOLD

        textInputEditText.layoutParams = nameField.getChildAt(0).layoutParams
        textInputLayout.layoutParams = nameField.layoutParams

        textInputLayout.addView(textInputEditText)

        return textInputLayout
    }

    private fun createUnitField(unitField: TextInputLayout): TextInputLayout {
        val textInputLayout = TextInputLayout(
            requireContext(),
            null,
            R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox_ExposedDropdownMenu
        )
        textInputLayout.id = View.generateViewId()
        val autoText = AppCompatAutoCompleteTextView(requireContext())
        autoText.hint = getString(R.string.addrecipe_hint_ingredient_measure)
        autoText.maxLines = 1
        autoText.setTextAppearance(requireContext(), R.style.TextAppearance_AppCompat_Small)
        autoText.typeface = Typeface.DEFAULT_BOLD
        autoText.inputType = InputType.TYPE_NULL
        autoText.isCursorVisible = false

        autoText.layoutParams = unitField.getChildAt(0).layoutParams
        textInputLayout.layoutParams = unitField.layoutParams

        textInputLayout.addView(autoText)

        return textInputLayout
    }

    private fun createQtyField(qtyField: TextInputLayout): TextInputLayout {
        val textInputLayout = TextInputLayout(
            requireContext(),
            null,
            R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox
        )

        textInputLayout.id = View.generateViewId()

        val textInputEditText = TextInputEditText(requireContext())
        textInputEditText.hint = getString(R.string.addrecipe_hint_ingredient_qty)
        textInputEditText.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
        textInputEditText.maxLines = 1
        textInputEditText.setTextAppearance(
            requireContext(),
            R.style.TextAppearance_AppCompat_Small
        )
        textInputEditText.typeface = Typeface.DEFAULT_BOLD

        textInputEditText.layoutParams = qtyField.getChildAt(0).layoutParams
        textInputLayout.layoutParams = qtyField.layoutParams

        textInputLayout.addView(textInputEditText)

        return textInputLayout
    }

    private fun validateCurrentIngredientSet(): Boolean {
        return true
    }
}

data class IngredientContainers(
    var qtyField: TextInputLayout,
    var unitField: TextInputLayout,
    var nameField: TextInputLayout
)
