package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.databinding.FragmentAboutSavedMealBinding
import rs.raf.nutritiontracker.databinding.FragmentMealDetailedBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AboutSavedMealFragment(
    private val savedMeal: SavedMeal
) : Fragment(R.layout.fragment_about_saved_meal) {
    private var _binding: FragmentAboutSavedMealBinding? = null
    private var ingredientMeasure: HashMap<String, String> = hashMapOf()
    private val binding get() = _binding!!

    private lateinit var strMeal: String
    private lateinit var strMealThumb: String
    private lateinit var idMeal: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutSavedMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        strMeal = savedMeal.strMeal.toString()
        strMealThumb = savedMeal.strMealThumb.toString()
        idMeal = savedMeal.idMeal
        fillpage(savedMeal)
    }

    private fun fillpage(meal: SavedMeal) {
        binding.aboutSavedMealNameTV.text = meal.strMeal
        if(savedMeal.strMealThumb?.contains("http") == true) {
            Picasso
                .get()
                .load(meal.strMealThumb)
                .into(binding.aboutSavedMealImageView)
        } else {
            Picasso
                .get()
                .load(File(meal.strMealThumb))
                .into(binding.aboutSavedMealImageView)
        }
        binding.aboutSavedMealCategoryTV.text = meal.strCategory
        binding.aboutSavedMealAreaTV.text = meal.strArea
        binding.aboutSavedMealsTagsTV.text = meal.strTags
        binding.aboutSavedMealLinkTV.text = meal.strYoutube
        val cal : Calendar = Calendar.getInstance()
        cal.timeInMillis = meal.dateInMillis
        val dateFormat = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
        val formattedTime = dateFormat.format(cal.time)
        binding.aboutSavedMealDateTV.text = formattedTime
        binding.aboutSavedMealTypeTV.text = meal.mealType
        binding.aboutSavedMealInstructionsTV.text = meal.strInstructions
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
        binding.aboutSavedMealIngredientsTV.text = str
    }
}