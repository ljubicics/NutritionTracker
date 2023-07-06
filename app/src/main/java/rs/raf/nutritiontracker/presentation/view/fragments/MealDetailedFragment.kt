package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.ShortMeal
import rs.raf.nutritiontracker.databinding.FragmentMealDetailedBinding
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.contract.MealsForAreaContract
import rs.raf.nutritiontracker.presentation.view.states.MealState
import rs.raf.nutritiontracker.presentation.view.states.MealsForAreaState
import rs.raf.nutritiontracker.presentation.viewmodel.FilterMealsByAreaViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel

class MealDetailedFragment(
    private val shortMeal: ShortMeal?,
    private val mealForCategory: MealForCategory?
) : Fragment(R.layout.fragment_meal_detailed) {
    lateinit var viewPager : ViewPager
    private var _binding: FragmentMealDetailedBinding? = null
    private val mealViewModel: MealContract.ViewModel by sharedViewModel<MealViewModel>()
    private var ingredientMeasure: HashMap<String, String> = hashMapOf()
//    mealNameTV: TextView
//    mealsCategoryTV: TextView
//    mealsAreaTV: TextView
//    mealsTagsTV: TextView
//    mealsLinkTV: TextView
//    mealInstructionsTV: TextView
//    mealRecyclerView: RecyclerView
//    mealImageView: ImageView
    private lateinit var strMeal: String
    private lateinit var strMealThumb: String
    private lateinit var idMeal: String
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
        initListeners()
    }

    private fun initUi() {
        if(shortMeal != null) {
            strMeal = shortMeal.strMeal
            strMealThumb = shortMeal.strMealThumb
            idMeal = shortMeal.idMeal
        } else if(mealForCategory != null) {
            strMeal = mealForCategory.strMeal
            strMealThumb = mealForCategory.strMealThumb
            idMeal = mealForCategory.idMeal
        }
    }

    private fun initObservers() {
        mealViewModel.fetchMealById(idMeal)
        mealViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            renderStateMeal(it)
        })
    }

    private fun initListeners() {

    }

    private fun renderStateMeal(state: MealState) {
        when (state) {
            is MealState.Success -> {
//                showLoadingState(false)
                fillpage(state.meals[0])

            }
            is MealState.Error -> {
//                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealState.DataFetched -> {
//                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealState.Loading -> {
//                showLoadingState(true)
            }
        }
    }

    private fun fillpage(meal: Meal) {
        binding.mealNameTV.text = meal.strMeal
        Picasso
            .get()
            .load(meal.strMealThumb)
            .into(binding.mealImageView)
        binding.mealsCategoryTV.text = meal.strCategory
        binding.mealsAreaTV.text = meal.strArea
        binding.mealsTagsTV.text = meal.strTags
        binding.mealsLinkTV.text = meal.strYoutube
        binding.mealInstructionsTV.text = meal.strInstructions
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
        println("VELIKO BRATE " + ingredientMeasure.size)
        var str = ""
        for ((key, value) in ingredientMeasure) {
            str += "$key : $value \n"
        }
        binding.mealIngredientsTV.text = str
    }
}