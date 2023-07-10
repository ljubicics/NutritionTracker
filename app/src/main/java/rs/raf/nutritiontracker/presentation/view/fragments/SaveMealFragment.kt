package rs.raf.nutrmealiontracker.presentation.view.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.User
import rs.raf.nutritiontracker.data.models.entities.MealEntity
import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity
import rs.raf.nutritiontracker.databinding.FragmentSaveMealBinding
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.contract.UserContract
import rs.raf.nutritiontracker.presentation.view.states.AddMealState
import rs.raf.nutritiontracker.presentation.view.states.MealState
import rs.raf.nutritiontracker.presentation.view.states.UserState
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.UserViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class SaveMealFragment(
    private val meal: Meal
) : Fragment(R.layout.fragment_save_meal){
    private var _binding: FragmentSaveMealBinding? = null
    private val binding get() = _binding!!
    private val mealViewModel: MealContract.ViewModel by sharedViewModel<MealViewModel>()
    private val userViewModel: UserContract.ViewModel by sharedViewModel<UserViewModel>()

    private lateinit var spinner: Spinner
    private val listOfMealTypes: List<String> = listOf("DORUCAK", "RUCAK", "VECERA", "UZINA")
    private var mealDateSelected: String = ""
    private lateinit var user: User
    private lateinit var spinnerSelected: String
    private var date: Long = 0
    private lateinit var image: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inmeal()
    }

    private fun inmeal() {
        inmealUI()
        inmealObservers()
        inmealListeners()
    }

    private fun inmealUI() {
        binding.saveMealNameTV.text = meal.strMeal
        Picasso
            .get()
            .load(meal.strMealThumb)
            .into(binding.saveMealImageView)
        image = meal.strMealThumb.toString()
        // Inicijalizacija spinera
        spinner = binding.saveMealSpinner
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listOfMealTypes)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

    }

    private fun inmealObservers() {
        val userState = userViewModel.userState.value
        if (userState != null) {
            getUser(userState)
        }
        mealViewModel.addMealDone.observe(viewLifecycleOwner, Observer {
            when(it) {
                is AddMealState.Success -> {
                    Toast.makeText(context, "Meal successfully saved", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun inmealListeners() {
        val calendar = Calendar.getInstance()
        val today = calendar.timeInMillis
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        date = calendar.timeInMillis

        binding.saveMealImageView.setOnClickListener(View.OnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 1888)
        })
        binding.saveMealDateButton.setText("" + calendar.get(Calendar.DAY_OF_MONTH) + " " + (calendar.get(Calendar.MONTH) + 1) + " " + calendar.get(Calendar.YEAR))
        binding.saveMealDateButton.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year2, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                year = year2
                month = monthOfYear
                day = dayOfMonth
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.YEAR, year)
                date = calendar.timeInMillis
                binding.saveMealDateButton.setText("" + calendar.get(Calendar.DAY_OF_MONTH) + " " + (calendar.get(Calendar.MONTH) + 1) + " " + calendar.get(Calendar.YEAR))
            }, year, month, day)
            dpd.show()
        }
        mealDateSelected = "$day $month $year"
        binding.saveMealSaveButton.setOnClickListener {
            val mealForDB = MealSavedEntity(
                mealId = 0,
                meal.idMeal,
                meal.strMeal,
                meal.strDrinkAlternate,
                meal.strCategory,
                meal.strArea,
                meal.strInstructions,
                image,
                meal.strTags,
                meal.strYoutube,
                meal.strIngredient1,
                meal.strIngredient2,
                meal.strIngredient3,
                meal.strIngredient4,
                meal.strIngredient5,
                meal.strIngredient6,
                meal.strIngredient7,
                meal.strIngredient8,
                meal.strIngredient9,
                meal.strIngredient10,
                meal.strIngredient11,
                meal.strIngredient12,
                meal.strIngredient13,
                meal.strIngredient14,
                meal.strIngredient15,
                meal.strIngredient16,
                meal.strIngredient17,
                meal.strIngredient18,
                meal.strIngredient19,
                meal.strIngredient20,
                meal.strMeasure1,
                meal.strMeasure2,
                meal.strMeasure3,
                meal.strMeasure4,
                meal.strMeasure5,
                meal.strMeasure6,
                meal.strMeasure7,
                meal.strMeasure8,
                meal.strMeasure9,
                meal.strMeasure10,
                meal.strMeasure11,
                meal.strMeasure12,
                meal.strMeasure13,
                meal.strMeasure14,
                meal.strMeasure15,
                meal.strMeasure16,
                meal.strMeasure17,
                meal.strMeasure18,
                meal.strMeasure19,
                meal.strMeasure20,
                meal.strSource,
                meal.strImageSource,
                meal.strCreativeCommonsConfirmed,
                mealDateSelected,
                user.username,
                spinner.selectedItem.toString(),
                date,
                today
            )
            mealViewModel.addMeal(mealForDB)
            requireActivity().supportFragmentManager.popBackStack()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun getUser(state: UserState) {
        when (state) {
            is UserState.Success -> {
//                showLoadingState(false)
                user = state.users[0]
            }
            is UserState.Error -> {
//                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is UserState.DataFetched -> {
//                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is UserState.Loading -> {
//                showLoadingState(true)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1888 && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.saveMealImageView.setImageBitmap(imageBitmap)

            // Save the image to the local storage
            val savedImagePath = saveImageToStorage(imageBitmap)

            // Save the path to the image in the database
//            mealDetailedVM.setMealImageFilePath(savedImagePath)
            image = savedImagePath
            Picasso
                .get()
                .load(File(image))
                .into(binding.saveMealImageView)
        }
    }

    private fun saveImageToStorage(image: Bitmap): String {
        val storageDir = requireContext().getExternalFilesDir(null)
        val imageFile = File.createTempFile(
            "meal_image",
            ".jpg",
            storageDir
        )
        val outputStream = FileOutputStream(imageFile)
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        return imageFile.absolutePath
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}