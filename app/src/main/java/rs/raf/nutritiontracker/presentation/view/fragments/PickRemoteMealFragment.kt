package rs.raf.nutritiontracker.presentation.view.fragments

import android.app.DatePickerDialog
import android.content.Intent
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
import rs.raf.nutritiontracker.data.models.DayInTheWeek
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.MealForPlan
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.data.models.User
import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity
import rs.raf.nutritiontracker.databinding.FragmentFilterAreaBinding
import rs.raf.nutritiontracker.databinding.FragmentPickRemoteMealBinding
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.contract.PickMealsContract
import rs.raf.nutritiontracker.presentation.contract.UserContract
import rs.raf.nutritiontracker.presentation.view.states.MealState
import rs.raf.nutritiontracker.presentation.view.states.UserState
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.PickMealsViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.UserViewModel
import java.util.Calendar

class PickRemoteMealFragment(
    private val mealForCategory: MealForCategory
) : Fragment(R.layout.fragment_pick_remote_meal) {
    private var _binding: FragmentPickRemoteMealBinding? = null

    private val mealViewModel: MealContract.ViewModel by sharedViewModel<MealViewModel>()
    private val userViewModel: UserContract.ViewModel by sharedViewModel<UserViewModel>()
    private val pickMealsViewModel: PickMealsContract.ViewModel by sharedViewModel<PickMealsViewModel>()
    private lateinit var meal: Meal
    private lateinit var strMeal: String
    private lateinit var strMealThumb: String
    private lateinit var idMeal: String
    private lateinit var spinner: Spinner
    private var date: Long = 0
    private var mealDateSelected: String = ""
    private lateinit var user: User
    private val listOfMealTypes: List<String> = listOf("DORUCAK", "RUCAK", "VECERA", "UZINA")

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        pickRemotedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPickRemoteMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, pickRemotedInstanceState: Bundle?) {
        super.onViewCreated(view, pickRemotedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
        initListeners()
    }

    private fun initUi() {
        strMeal = mealForCategory.strMeal
        strMealThumb = mealForCategory.strMealThumb
        idMeal = mealForCategory.idMeal
        spinner = binding.pickRemoteMealSpinner
    }

    private fun initObservers() {
        val userState = userViewModel.userState.value
        if (userState != null) {
            getUser(userState)
        }
        mealViewModel.fetchMealById(idMeal)
        mealViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            if(it is MealState.Success) {
                meal = it.meals[0]
                fillPage()
            }
        })
    }
    private fun fillPage() {
        binding.pickRemoteMealNameTV.text = meal.strMeal
        Picasso
            .get()
            .load(meal.strMealThumb)
            .into(binding.pickRemoteMealImageView)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listOfMealTypes)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
    }
    private fun initListeners() {
        val calendar = Calendar.getInstance()
        val today = calendar.timeInMillis
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        date = calendar.timeInMillis

        binding.pickRemoteMealImageView.setOnClickListener(View.OnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 1888)
        })
        binding.pickRemoteMealDateButton.setText("" + calendar.get(Calendar.DAY_OF_MONTH) + " " + (calendar.get(
            Calendar.MONTH) + 1) + " " + calendar.get(Calendar.YEAR))
        binding.pickRemoteMealDateButton.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year2, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                year = year2
                month = monthOfYear
                day = dayOfMonth
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.YEAR, year)
                date = calendar.timeInMillis
                binding.pickRemoteMealDateButton.setText("" + calendar.get(Calendar.DAY_OF_MONTH) + " " + (calendar.get(
                    Calendar.MONTH) + 1) + " " + calendar.get(Calendar.YEAR))
            }, year, month, day)
            dpd.show()
        }
        mealDateSelected = "$day $month $year"
        binding.pickRemoteMealAddButton.setOnClickListener {
            val i = pickMealsViewModel.number.value
            val mealForPlan = MealForPlan(
                mealId = i!!,
                meal.idMeal,
                meal.strMeal,
                meal.strDrinkAlternate,
                meal.strCategory,
                meal.strArea,
                meal.strInstructions,
                meal.strMealThumb,
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
            pickMealsViewModel.addMealToDay(mealForPlan, calculateDay(mealForPlan))
            pickMealsViewModel.increaseNumber()
            val state = pickMealsViewModel.mondayState
            println("JELO" + state.value?.size)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun getUser(state: UserState) {
        when (state) {
            is UserState.Success -> {
                user = state.users[0]
            }
            is UserState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is UserState.DataFetched -> {
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is UserState.Loading -> {
            }
        }
    }

    private fun calculateDay(meal: MealForPlan) : DayInTheWeek {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = meal.dateInMillis
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        if(day == 1) {
            return DayInTheWeek.SUNDAY
        } else if(day == 2) {
            return DayInTheWeek.MONDAY
        } else if(day == 3) {
            return DayInTheWeek.TUESDAY
        } else if(day == 4) {
            return DayInTheWeek.WEDNESDAY
        } else if(day == 5) {
            return DayInTheWeek.THURSDAY
        } else if(day == 6) {
            return DayInTheWeek.FRIDAY
        } else {
            return DayInTheWeek.SATURDAY
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}