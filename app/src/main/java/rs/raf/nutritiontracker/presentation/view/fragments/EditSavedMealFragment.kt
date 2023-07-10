package rs.raf.nutritiontracker.presentation.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity
import rs.raf.nutritiontracker.databinding.FragmentEditSavedMealBinding
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class EditSavedMealFragment(
    private val savedMeal: SavedMeal
) : Fragment(R.layout.fragment_edit_saved_meal){
    private var _binding: FragmentEditSavedMealBinding? = null
    private var ingredientMeasure: HashMap<String, String> = hashMapOf()
    private val mealViewModel: MealContract.ViewModel by sharedViewModel<MealViewModel>()
    private val binding get() = _binding!!

    private lateinit var strMeal: String
    private lateinit var strMealThumb: String
    private lateinit var idMeal: String
    private var date: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditSavedMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initListeners()
    }

    private fun initUi() {
        strMeal = savedMeal.strMeal.toString()
        strMealThumb = savedMeal.strMealThumb.toString()
        idMeal = savedMeal.idMeal
        fillpage(savedMeal)
    }

    private fun initListeners() {
        val calendar = Calendar.getInstance()
        val today = calendar.timeInMillis
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.editSavedMealDateTV.setOnClickListener{
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year2, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                year = year2
                month = monthOfYear
                day = dayOfMonth
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.YEAR, year)
                date = calendar.timeInMillis
                binding.editSavedMealDateTV.setText("" + calendar.get(Calendar.DAY_OF_MONTH) + " " + (calendar.get(Calendar.MONTH) + 1) + " " + calendar.get(Calendar.YEAR))
            }, year, month, day)
            dpd.show()
        }
        binding.editSavedMealButton.setOnClickListener {
            val mealForDB = MealSavedEntity(
                savedMeal.mealId,
                savedMeal.idMeal,
                binding.editSavedMealNameTV.text.toString(),
                savedMeal.strDrinkAlternate,
                binding.editSavedMealCategoryTV.text.toString(),
                binding.editSavedMealAreaTV.text.toString(),
                binding.editSavedMealInstructionsTV.text.toString(),
                savedMeal.strMealThumb,
                binding.editSavedMealsTagsTV.toString(),
                savedMeal.strYoutube,
                savedMeal.strIngredient1,
                savedMeal.strIngredient2,
                savedMeal.strIngredient3,
                savedMeal.strIngredient4,
                savedMeal.strIngredient5,
                savedMeal.strIngredient6,
                savedMeal.strIngredient7,
                savedMeal.strIngredient8,
                savedMeal.strIngredient9,
                savedMeal.strIngredient10,
                savedMeal.strIngredient11,
                savedMeal.strIngredient12,
                savedMeal.strIngredient13,
                savedMeal.strIngredient14,
                savedMeal.strIngredient15,
                savedMeal.strIngredient16,
                savedMeal.strIngredient17,
                savedMeal.strIngredient18,
                savedMeal.strIngredient19,
                savedMeal.strIngredient20,
                savedMeal.strMeasure1,
                savedMeal.strMeasure2,
                savedMeal.strMeasure3,
                savedMeal.strMeasure4,
                savedMeal.strMeasure5,
                savedMeal.strMeasure6,
                savedMeal.strMeasure7,
                savedMeal.strMeasure8,
                savedMeal.strMeasure9,
                savedMeal.strMeasure10,
                savedMeal.strMeasure11,
                savedMeal.strMeasure12,
                savedMeal.strMeasure13,
                savedMeal.strMeasure14,
                savedMeal.strMeasure15,
                savedMeal.strMeasure16,
                savedMeal.strMeasure17,
                savedMeal.strMeasure18,
                savedMeal.strMeasure19,
                savedMeal.strMeasure20,
                savedMeal.strSource,
                savedMeal.strImageSource,
                savedMeal.strCreativeCommonsConfirmed,
                binding.editSavedMealDateTV.text.toString(),
                savedMeal.user,
                savedMeal.mealType,
                date,
                today,
            )
            mealViewModel.editMeal(mealForDB)
        }
    }

    private fun fillpage(meal: SavedMeal) {
        val formatter = SimpleDateFormat("dd MM yyyy");
        val dateString : String = formatter.format(Date(meal.dateInMillis))
        binding.editSavedMealNameTV.setText(meal.strMeal)
        if(savedMeal.strMealThumb?.contains("http") == true) {
            Picasso
                .get()
                .load(meal.strMealThumb)
                .into(binding.editSavedMealImageView)
        } else {
            Picasso
                .get()
                .load(File(meal.strMealThumb))
                .into(binding.editSavedMealImageView)
        }
        binding.editSavedMealCategoryTV.setText(meal.strCategory)
        binding.editSavedMealAreaTV.setText(meal.strArea)
        binding.editSavedMealsTagsTV.setText(meal.strTags)
        binding.editSavedMealLinkTV.setText(meal.strYoutube)
        binding.editSavedMealDateTV.setText(dateString)
        binding.editSavedMealTypeTV.setText(meal.mealType)
        binding.editSavedMealInstructionsTV.setText(meal.strInstructions)
        for(i in 1..20) {
            var strIngredient = "strIngredient$i"
            var strMeasure = "strMeasure$i"
            val field1 = meal.javaClass.getDeclaredField(strIngredient)  // Dohvatanje polja refleksijom
            field1.isAccessible = true  // Omogućavanje pristupa privatnom polju
            val ingredientValue = field1.get(meal) as String
            val field2 = meal.javaClass.getDeclaredField(strMeasure)  // Dohvatanje polja refleksijom
            field2.isAccessible = true  // Omogućavanje pristupa privatnom polju
            val measureValue = field2.get(meal) as String
            if(ingredientValue == "" || measureValue == "") {
                break
            }
            ingredientMeasure.put(ingredientValue,  measureValue)
        }
        var str = ""
        for ((key, value) in ingredientMeasure) {
            str += "$key : $value \n"
        }
//        binding.editSavedMealIngredientsTV.setText(str)
    }
}