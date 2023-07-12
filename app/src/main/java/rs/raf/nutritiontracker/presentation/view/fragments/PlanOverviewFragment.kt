package rs.raf.nutritiontracker.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.DayInTheWeek
import rs.raf.nutritiontracker.data.models.MealForPlan
import rs.raf.nutritiontracker.databinding.FragmentFilterAreaBinding
import rs.raf.nutritiontracker.databinding.FragmentPlanOverviewBinding
import rs.raf.nutritiontracker.presentation.contract.PickMealsContract
import rs.raf.nutritiontracker.presentation.viewmodel.PickMealsViewModel

class PlanOverviewFragment : Fragment(R.layout.fragment_plan_overview) {
    private var _binding: FragmentPlanOverviewBinding? = null
    private val pickMealsViewModel: PickMealsContract.ViewModel by sharedViewModel<PickMealsViewModel>()
    private val binding get() = _binding!!
    private var mondayNumOfMeals: Int = 0
    private var tuesdayNumOfMeals: Int = 0
    private var wednesdayNumOfMeals: Int = 0
    private var thursdayNumOfMeals: Int = 0
    private var fridayNumOfMeals: Int = 0
    private var saturdayNumOfMeals: Int = 0
    private var sundayNumOfMeals: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObservers()
        initListeners()
    }

    private fun init() {
        if(pickMealsViewModel.mondayState.value != null) {
            mondayNumOfMeals = pickMealsViewModel.mondayState.value!!.size
        }
        if(pickMealsViewModel.tuesdayState.value != null) {
            mondayNumOfMeals = pickMealsViewModel.tuesdayState.value!!.size
        }
        if(pickMealsViewModel.wednesdayState.value != null) {
            mondayNumOfMeals = pickMealsViewModel.wednesdayState.value!!.size
        }
        if(pickMealsViewModel.thursdayState.value != null) {
            mondayNumOfMeals = pickMealsViewModel.thursdayState.value!!.size
        }
        if(pickMealsViewModel.fridayState.value != null) {
            mondayNumOfMeals = pickMealsViewModel.fridayState.value!!.size
        }
        if(pickMealsViewModel.saturdayState.value != null) {
            mondayNumOfMeals = pickMealsViewModel.saturdayState.value!!.size
        }
        if(pickMealsViewModel.sundayState.value != null) {
            mondayNumOfMeals = pickMealsViewModel.sundayState.value!!.size
        }
    }

    private fun initObservers() {
        pickMealsViewModel.mondayState.observe(viewLifecycleOwner, Observer {
            mondayNumOfMeals = it.size
            binding.mondayTV.text = "$mondayNumOfMeals meals"
        })
        pickMealsViewModel.tuesdayState.observe(viewLifecycleOwner, Observer {
            tuesdayNumOfMeals = it.size
            binding.tuesdayTV.text = "$tuesdayNumOfMeals meals"
        })
        pickMealsViewModel.wednesdayState.observe(viewLifecycleOwner, Observer {
            wednesdayNumOfMeals = it.size
            binding.wednesdayTV.text = "$wednesdayNumOfMeals meals"
        })
        pickMealsViewModel.thursdayState.observe(viewLifecycleOwner, Observer {
            thursdayNumOfMeals = it.size
            binding.thursdayTV.text = "$thursdayNumOfMeals meals"
        })
        pickMealsViewModel.fridayState.observe(viewLifecycleOwner, Observer {
            fridayNumOfMeals = it.size
            binding.fridayTV.text = "$fridayNumOfMeals meals"
        })
        pickMealsViewModel.saturdayState.observe(viewLifecycleOwner, Observer {
            saturdayNumOfMeals = it.size
            binding.saturdayTV.text = "$saturdayNumOfMeals meals"
        })
        pickMealsViewModel.sundayState.observe(viewLifecycleOwner, Observer {
            sundayNumOfMeals = it.size
            binding.sundayTV.text = "$sundayNumOfMeals meals"
        })
    }

    private fun initListeners() {
        binding.mondayTV.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, PlanForDayFragment(DayInTheWeek.MONDAY)).addToBackStack(null)
            transaction.commit()
        }
        binding.tuesdayTV.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, PlanForDayFragment(DayInTheWeek.TUESDAY)).addToBackStack(null)
            transaction.commit()
        }
        binding.wednesdayTV.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, PlanForDayFragment(DayInTheWeek.WEDNESDAY)).addToBackStack(null)
            transaction.commit()
        }
        binding.thursdayTV.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, PlanForDayFragment(DayInTheWeek.THURSDAY)).addToBackStack(null)
            transaction.commit()
        }
        binding.fridayTV.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, PlanForDayFragment(DayInTheWeek.FRIDAY)).addToBackStack(null)
            transaction.commit()
        }
        binding.saturdayTV.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, PlanForDayFragment(DayInTheWeek.SATURDAY)).addToBackStack(null)
            transaction.commit()
        }
        binding.sundayTV.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, PlanForDayFragment(DayInTheWeek.SUNDAY)).addToBackStack(null)
            transaction.commit()
        }
        binding.sendPlanButton.setOnClickListener {
            var mealsMonday: List<MealForPlan> = listOf()
            var mealsTuesday: List<MealForPlan> = listOf()
            var mealsWednesday: List<MealForPlan> = listOf()
            var mealsThursday: List<MealForPlan> = listOf()
            var mealsFriday: List<MealForPlan> = listOf()
            var mealsSaturday: List<MealForPlan> = listOf()
            var mealsSunday: List<MealForPlan> = listOf()

            if(pickMealsViewModel.mondayState.value != null) {
                mealsMonday = pickMealsViewModel.mondayState.value!!
            }
            if(pickMealsViewModel.tuesdayState.value != null) {
                mealsTuesday = pickMealsViewModel.tuesdayState.value!!
            }
            if(pickMealsViewModel.wednesdayState.value != null) {
                mealsWednesday = pickMealsViewModel.wednesdayState.value!!
            }
            if(pickMealsViewModel.thursdayState.value != null) {
                mealsThursday = pickMealsViewModel.thursdayState.value!!
            }
            if(pickMealsViewModel.fridayState.value != null) {
                mealsFriday = pickMealsViewModel.fridayState.value!!
            }
            if(pickMealsViewModel.saturdayState.value != null) {
                mealsSaturday = pickMealsViewModel.saturdayState.value!!
            }
            if(pickMealsViewModel.sundayState.value != null) {
                mealsSunday = pickMealsViewModel.sundayState.value!!
            }
            var message = "Your meal plan: \n\n"
            message += "Monday:\n"
            for (meal in mealsMonday) {
                message += "Name: ${meal.strMeal}\n"
                message += "Meal Type: ${meal.mealType}\n"
                message += "--------------------\n"
            }
            message += "Tuesday:\n"
            for (meal in mealsTuesday) {
                message += "Name: ${meal.strMeal}\n"
                message += "Meal Type: ${meal.mealType}\n"
                message += "--------------------\n"
            }
            message += "Wednesday:\n"
            for (meal in mealsWednesday) {
                message += "Name: ${meal.strMeal}\n"
                message += "Meal Type: ${meal.mealType}\n"
                message += "--------------------\n"
            }
            message += "Thursday:\n"
            for (meal in mealsThursday) {
                message += "Name: ${meal.strMeal}\n"
                message += "Meal Type: ${meal.mealType}\n"
                message += "--------------------\n"
            }
            message += "Friday:\n"
            for(meal in mealsFriday) {
                message += "Name: ${meal.strMeal}\n"
                message += "Meal Type: ${meal.mealType}\n"
                message += "--------------------\n"
            }
            message += "Saturday:\n"
            for (meal in mealsSaturday) {
                message += "Name: ${meal.strMeal}\n"
                message += "Meal Type: ${meal.mealType}\n"
                message += "--------------------\n"
            }
            message += "Sunday:\n"
            for(meal in mealsSunday) {
                message += "Name: ${meal.strMeal}\n"
                message += "Meal Type: ${meal.mealType}\n"
                message += "--------------------\n"
            }
            message += "\nSincerely,\nYour NutritionTracker team"
            sendEmail(binding.planET.text.toString(), "Your meal plan", message)
        }
    }

    private fun sendEmail(emailAddress: String, subject: String, message: String) {
        val appLink = "myapp://open/"
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        if (intent.resolveActivity(context!!.packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Send Email"))
        } else {
            Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}