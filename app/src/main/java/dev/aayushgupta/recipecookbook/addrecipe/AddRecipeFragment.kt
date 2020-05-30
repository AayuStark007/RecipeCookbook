package dev.aayushgupta.recipecookbook.addrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.aayushgupta.recipecookbook.R
import dev.aayushgupta.recipecookbook.databinding.FragmentAddrecipeBinding

class AddRecipeFragment: Fragment() {

    private lateinit var fragmentAddrecipeBinding: FragmentAddrecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_addrecipe, container, false)
        fragmentAddrecipeBinding = FragmentAddrecipeBinding.bind(root)

        fragmentAddrecipeBinding.lifecycleOwner = this.viewLifecycleOwner
        return fragmentAddrecipeBinding.root
    }
}