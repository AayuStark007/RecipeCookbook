package dev.aayushgupta.recipecookbook.addrecipe

import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.databinding.FragmentTestBinding
import timber.log.Timber

data class IngredientContainersTest(
//    var qtyField: TextInputLayout,
//    var unitField: TextInputLayout,
    var nameField: TextInputLayout
)

class TestFragment: Fragment() {

    private lateinit var fragmentTestBinding: FragmentTestBinding

    private var ingredientList: List<IngredientContainersTest>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_test, container, false)
        fragmentTestBinding = FragmentTestBinding.bind(root)

        setupIngredientLists(inflater, container)

        fragmentTestBinding.lifecycleOwner = this.viewLifecycleOwner
        return fragmentTestBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setupIngredientLists(inflater, container)
    }

    private fun setupIngredientLists(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        Timber.d("New recipe")

        val baseNameUnit: TextInputLayout = inflater.inflate(R.layout.list_item_test, container, false) as TextInputLayout


        val currentIngredientSet = IngredientContainersTest(
//            fragmentTestBinding.addrecipeIngredientQtyTextinputLayout,
//            fragmentTestBinding.addrecipeIngredientMeasureTextinputLayout,
              baseNameUnit
        )
        ingredientList = ArrayList<IngredientContainersTest>(listOf(currentIngredientSet))
        baseNameUnit.setEndIconOnClickListener {
            Timber.d("On End click")
            currentIngredientSet.nameField.setEndIconDrawable(R.drawable.ic_cross_circle_outline_black_24dp)
            addNewIngredientToCurrent(
                fragmentTestBinding.ingredientListContainer,
                inflater, container
            )
            fragmentTestBinding.executePendingBindings()
        }

        fragmentTestBinding.ingredientListContainer.addView(baseNameUnit)
        fragmentTestBinding.executePendingBindings()
    }

    private fun addNewIngredientToCurrent(
        parentContainer: LinearLayout,
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        // create new qty field
        //val qtyField: TextInputLayout = createQtyField(currentIngredientSet.qtyField)
        // create new unit field
        //val unitField: TextInputLayout = createUnitField(currentIngredientSet.unitField)
        // create new name field
        val nameField: TextInputLayout = createNameField(inflater, container)
        // set constraints
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(fragmentTestBinding.ingredientListContainer)
//
//        val parentId = parentContainer.id
//        val topId = currentIngredientSet.nameField.id
//
//        // Constraint current ingredient item bottom to new item top
//        constraintSet.connect(topId, ConstraintSet.BOTTOM, nameField.id, ConstraintSet.TOP)

//        // set constraints for qty field
//        constraintSet.setDimensionRatio(qtyField.id, "w,2:1")
//        constraintSet.connect(qtyField.id, ConstraintSet.START, parentId, ConstraintSet.START)
//        constraintSet.connect(qtyField.id, ConstraintSet.BOTTOM, unitField.id, ConstraintSet.TOP)
//        constraintSet.connect(qtyField.id, ConstraintSet.END, nameField.id, ConstraintSet.START)
//        constraintSet.connect(qtyField.id, ConstraintSet.TOP, topId, ConstraintSet.BOTTOM)
//
//        // set constraints for unit field
//        constraintSet.setDimensionRatio(unitField.id, "w,2:1")
//        constraintSet.connect(unitField.id, ConstraintSet.END, qtyField.id, ConstraintSet.END)
//        constraintSet.connect(unitField.id, ConstraintSet.START, parentId, ConstraintSet.START)
//        constraintSet.connect(unitField.id, ConstraintSet.TOP, qtyField.id, ConstraintSet.BOTTOM)

//        // set constraints for name field
//        constraintSet.connect(nameField.id, ConstraintSet.BOTTOM, parentId, ConstraintSet.BOTTOM)
//        constraintSet.connect(nameField.id, ConstraintSet.END, parentId, ConstraintSet.END)
//        constraintSet.connect(nameField.id, ConstraintSet.START, parentId, ConstraintSet.START)
//        constraintSet.connect(nameField.id, ConstraintSet.TOP, parentId, ConstraintSet.TOP)

        // set click listener
        val newCurrentSet = IngredientContainersTest(nameField)
        (ingredientList as ArrayList).add(newCurrentSet)
        nameField.setEndIconOnClickListener {
            Timber.d("On End click")
            nameField.setEndIconDrawable(R.drawable.ic_cross_circle_outline_black_24dp)
            addNewIngredientToCurrent(parentContainer, inflater, container)
            fragmentTestBinding.executePendingBindings()
        }

        // add view to container
//        parentContainer.addView(qtyField)
//        parentContainer.addView(unitField)
        parentContainer.addView(nameField)
    }

    private fun createNameField(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): TextInputLayout {
        return inflater.inflate(R.layout.list_item_test, container, false) as TextInputLayout
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
}