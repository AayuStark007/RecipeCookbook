package dev.aayushgupta.recipecookbook.addrecipe

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
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
import dev.aayushgupta.recipecookbook.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import java.io.File
import java.io.IOException

class RecipeAddEditFragment : Fragment() {

    private lateinit var fragmentRecipeAddEditBinding: FragmentRecipeAddEditBinding

    private val args: RecipeAddEditFragmentArgs by navArgs()

    private val viewModel by viewModels<RecipeAddEditViewModel> {
        RecipeAddEditViewModelFactory(DefaultRecipeRepository.getRepository(requireActivity().application))
    }

    private lateinit var recipeImageAdapter: RecipeImageAdapter

    private val compressor by lazy {
        FileCompressor(maxWidth = 256, maxHeight = 256,
            destPath = requireContext().cacheDir.path + File.separator + "images")
    }

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
                    when (it.itemId) {
                        // TODO: Check if permissions granted
                        R.id.menu_open_camera -> dispatchTakePictureIntent()
                        R.id.menu_open_gallery -> dispatchGalleryIntent()
                        R.id.menu_add_random -> { viewModel.appendRandomImage() }
                    }
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

    private lateinit var currentImageFile: File

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile(requireContext())
                } catch (ex: IOException) {
                    Timber.e(ex)
                    null
                }

                photoFile?.also {
                    currentImageFile = it
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "dev.aayushgupta.recipecookbook.fileprovider",
                        it
                    )
                    Timber.d("Photo URI: $photoURI")
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    private fun dispatchGalleryIntent() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also { galleryIntent ->
            galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(galleryIntent, REQUEST_GALLERY_PHOTO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_TAKE_PHOTO -> viewModel.handleCameraResponse(currentImageFile, compressor)
                REQUEST_GALLERY_PHOTO -> {
                    Timber.d("XYPOS: Got file: ${data?.data}")
                    val realPath = data?.data?.getRealPathFromUri(requireContext()) // TODO: Profile this
                    viewModel.handleGalleryResponse(realPath, compressor)
                }
            }
        }
    }



    private fun handleGalleryResponse(data: Intent?) {

    }
}

const val REQUEST_TAKE_PHOTO = 101
const val REQUEST_GALLERY_PHOTO = 102
