package dev.aayushgupta.recipecookbook.utils

import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseMethod
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.data.domain.FlavorType
import dev.aayushgupta.recipecookbook.data.domain.RecipeType
import dev.aayushgupta.recipecookbook.data.domain.TimeUnit
import timber.log.Timber

object Converter {
    @InverseMethod("flavor_str_to_enum")
    @JvmStatic
    fun flavor_enum_to_str(
        view: AppCompatAutoCompleteTextView,
        oldVal: String?,
        value: FlavorType?
    ): String {
        return when (value) {
            FlavorType.SWEET -> "Sweet"//view.context.getString(R.string.label_flavor_sweet)
            FlavorType.SAVORY -> "Savory"//view.context.getString(R.string.label_flavor_savory)
            FlavorType.SOUR -> "Sour"//view.context.getString(R.string.label_flavor_sour)
            FlavorType.SALTY -> "Salty"//view.context.getString(R.string.label_flavor_salty)
            FlavorType.FRUITY -> "Fruity"//view.context.getString(R.string.label_flavor_fruity)
            FlavorType.NONE -> "None"//view.context.getString(R.string.label_none)
            else -> "None"
        }
    }

    @JvmStatic
    fun flavor_str_to_enum(
        view: AppCompatAutoCompleteTextView,
        oldVal: String?,
        value: String?
    ): FlavorType {
        return when (value) {
            "Sweet" -> FlavorType.SWEET
            "Savory" -> FlavorType.SAVORY
            "Sour" -> FlavorType.SOUR
            "Salty" -> FlavorType.SALTY
            "Fruity" -> FlavorType.FRUITY
            "None" -> FlavorType.NONE
            else -> FlavorType.NONE
        }
    }
}
//
//    @InverseMethod(value = "type_str_to_enum")
//    @JvmStatic fun type_enum_to_str(view: AppCompatAutoCompleteTextView, value: RecipeType): String {
//        return when (value) {
//            RecipeType.STARTER -> view.context.getString(R.string.label_recipe_starter)
//            RecipeType.MAIN -> view.context.getString(R.string.label_recipe_main)
//            RecipeType.DESSERT -> view.context.getString(R.string.label_recipe_dessert)
//            RecipeType.NONE -> view.context.getString(R.string.label_none)
//        }
//    }
//
//    @JvmStatic fun type_str_to_enum(view: AppCompatAutoCompleteTextView, value: String): RecipeType {
//        return when (value) {
//            view.context.getString(R.string.label_recipe_starter) -> RecipeType.STARTER
//            view.context.getString(R.string.label_recipe_main) -> RecipeType.MAIN
//            view.context.getString(R.string.label_recipe_dessert) -> RecipeType.DESSERT
//            view.context.getString(R.string.label_none) -> RecipeType.NONE
//            else -> RecipeType.NONE
//        }
//    }
//
//    @InverseMethod(value = "time_str_to_enum")
//    @JvmStatic fun time_enum_to_str(view: AppCompatAutoCompleteTextView, value: TimeUnit): String {
//        return when (value) {
//            TimeUnit.MINUTES -> view.context.getString(R.string.label_time_unit_minutes)
//            TimeUnit.HOURS -> view.context.getString(R.string.label_time_unit_minutes)
//            TimeUnit.NONE -> view.context.getString(R.string.label_none)
//        }
//    }
//
//    @JvmStatic fun time_str_to_enum(view: AppCompatAutoCompleteTextView, value: String): TimeUnit {
//        return when (value) {
//            view.context.getString(R.string.label_time_unit_minutes) -> TimeUnit.MINUTES
//            view.context.getString(R.string.label_time_unit_hours) -> TimeUnit.HOURS
//            view.context.getString(R.string.label_none) -> TimeUnit.NONE
//            else -> TimeUnit.NONE
//        }
//    }
//
//}

// Update view when livedata changes
@BindingAdapter("android:text")
fun AppCompatAutoCompleteTextView.assignFlavor(value: FlavorType?) {
    Timber.d("FlavorType: assign $value to view: $text")
    value?.let {
        if (text.toString() != context.getString(value.displayId)) {
            when (value) {
                FlavorType.SWEET -> setText(context.getText(R.string.label_flavor_sweet), false)
                FlavorType.SAVORY -> setText(context.getText(R.string.label_flavor_savory), false)
                FlavorType.SOUR -> setText(context.getText(R.string.label_flavor_sour), false)
                FlavorType.SALTY -> setText(context.getText(R.string.label_flavor_salty), false)
                FlavorType.FRUITY -> setText(context.getText(R.string.label_flavor_fruity), false)
                FlavorType.NONE -> setText(context.getText(R.string.label_none), false)
            }
        }

    }
}

// Update livedata when view changes
@InverseBindingAdapter(attribute = "android:text")
fun AppCompatAutoCompleteTextView.getFlavor(): FlavorType {
    Timber.d("FlavorType: Set live data $text")
    return when (text.toString()) {
        context.getString(R.string.label_flavor_sweet) -> FlavorType.SWEET
        context.getString(R.string.label_flavor_savory) -> FlavorType.SAVORY
        context.getString(R.string.label_flavor_sour) -> FlavorType.SOUR
        context.getString(R.string.label_flavor_salty) -> FlavorType.SALTY
        context.getString(R.string.label_flavor_fruity) -> FlavorType.FRUITY
        context.getString(R.string.label_none) -> FlavorType.NONE
        else -> FlavorType.NONE
    }
}


// Update view when livedata changes
@BindingAdapter("android:text")
fun AppCompatAutoCompleteTextView.assignType(value: RecipeType?) {
    Timber.d("RecipeType: assign $value to view: $text")
    value?.let {
        if (text.toString() != context.getString(value.displayId)) {
            when (value) {
                RecipeType.STARTER -> setText(context.getText(R.string.label_recipe_starter), false)
                RecipeType.MAIN -> setText(context.getText(R.string.label_recipe_main), false)
                RecipeType.DESSERT -> setText(context.getText(R.string.label_recipe_dessert), false)
                RecipeType.NONE -> setText(context.getText(R.string.label_none), false)
            }
        }

    }
}

// Update livedata when view changes
@InverseBindingAdapter(attribute = "android:text")
fun AppCompatAutoCompleteTextView.getType(): RecipeType {
    Timber.d("RecipeType: Set live data $text")
    return when (text.toString()) {
        context.getString(R.string.label_recipe_starter) -> RecipeType.STARTER
        context.getString(R.string.label_recipe_main) -> RecipeType.MAIN
        context.getString(R.string.label_recipe_dessert) -> RecipeType.DESSERT
        context.getString(R.string.label_none) -> RecipeType.NONE
        else -> RecipeType.NONE
    }
}

// Update view when livedata changes
@BindingAdapter("android:text")
fun AppCompatAutoCompleteTextView.assignUnit(value: TimeUnit?) {
    Timber.d("TimeUnit: assign $value to view: $text")
    value?.let {
        if (text.toString() != context.getString(value.displayId)) {
            when (value) {
                TimeUnit.HOURS -> setText(context.getText(R.string.label_time_unit_hours), false)
                TimeUnit.MINUTES -> setText(
                    context.getText(R.string.label_time_unit_minutes),
                    false
                )
                TimeUnit.NONE -> setText(context.getText(R.string.label_none), false)
            }
        }

    }
}

// Update livedata when view changes
@InverseBindingAdapter(attribute = "android:text")
fun AppCompatAutoCompleteTextView.getUnit(): TimeUnit {
    Timber.d("TimeUnit: Set live data $text")
    return when (text.toString()) {
        context.getString(R.string.label_time_unit_hours) -> TimeUnit.HOURS
        context.getString(R.string.label_time_unit_minutes) -> TimeUnit.MINUTES
        context.getString(R.string.label_none) -> TimeUnit.NONE
        else -> TimeUnit.NONE
    }
}