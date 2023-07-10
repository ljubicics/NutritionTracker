package rs.raf.nutritiontracker.presentation.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.User
import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity
import rs.raf.nutritiontracker.databinding.FragmentCategoriesBinding
import rs.raf.nutritiontracker.databinding.FragmentProfileBinding
import rs.raf.nutritiontracker.presentation.contract.CategoryContract
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.contract.UserContract
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.view.states.SavedMealState
import rs.raf.nutritiontracker.presentation.view.states.UserState
import rs.raf.nutritiontracker.presentation.viewmodel.CategoryViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.UserViewModel
import timber.log.Timber
import java.util.Calendar

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val userViewModel: UserContract.ViewModel by sharedViewModel<UserViewModel>()
    private val mealViewModel: MealContract.ViewModel by sharedViewModel<MealViewModel>()
    private lateinit var user: User
    private lateinit var savedMeals: List<MealSavedEntity>

    lateinit var barChart: BarChart

    // on below line we are creating
    // a variable for bar data
    lateinit var barData: BarData

    // on below line we are creating a
    // variable for bar data set
    lateinit var barDataSet: BarDataSet

    lateinit var barEntriesList: ArrayList<BarEntry>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val userState = userViewModel.userState.value
        if (userState != null) {
            getUser(userState)
        }
        initUi()
    }

    private fun initUi() {
        initObservers()
        initListeners()

    }

    private fun initListeners() {
        binding.savedMealsButton.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.container, UserSavedMealsFragment(user.username)).addToBackStack(null)
            transaction.commit()
        }
    }

    private fun initObservers() {
        mealViewModel.getSavedMealsByUser(user.username)
        mealViewModel.savedMealState.observe(viewLifecycleOwner, Observer {
            if(it is SavedMealState.Success) {
                savedMeals = it.savedMeals
                Timber.e("SAVED MEALS SIZE = " + savedMeals.size)
                createBarChart()
            }
        })
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

    private fun createBarChart() {
        barChart = binding.idBarChart

        getBarChartData()

        // on below line we are initializing our bar data set
        barDataSet = BarDataSet(barEntriesList, "Meals Per Day")

        // on below line we are initializing our bar data
        barData = BarData(barDataSet)

        // on below line we are setting data to our bar chart
        barChart.data = barData

        barChart.animateXY(1000, 1000);

        // on below line we are setting colors for our bar chart text
        barDataSet.valueTextColor = Color.BLACK

        // on below line we are setting color for our bar data set
        barDataSet.setColor(resources.getColor(R.color.colorSelected))

        // on below line we are setting text size
        barDataSet.valueTextSize = 16f

        // on below line we are enabling description as false
        barChart.description.isEnabled = false
    }

    private fun getBarChartData() {
        barEntriesList = ArrayList()

        var firstDay : Float = 0.0F
        var secondDay : Float = 0.0F
        var thirdDay : Float = 0.0F
        var fourthDay : Float = 0.0F
        var fifthDay : Float = 0.0F
        var sixthDay : Float = 0.0F
        var seventhDay : Float = 0.0F

        val calendar = Calendar.getInstance()
        val currentDateInMillis = calendar.timeInMillis

        val mealsPerDay = IntArray(7) // Mapa za čuvanje broja jela po danima

        mealsPerDay.fill(0)
//
        for (meal in savedMeals) {
            val mealDateInMillis = meal.dateAdded // Vreme jela u milisekundama
//            Timber.e("VREMEEE " + (currentDateInMillis - mealDateInMillis))
//            // Računanje broja dana koji je prošao od datuma jela do trenutnog datuma
            val daysAgo = ((currentDateInMillis - mealDateInMillis) / (24 * 60 * 60 * 1000)).toInt() // Pretvaranje u dane
//
//            Timber.e("BROJ DANA JELA " + daysAgo)
//
            if(daysAgo <= 6) {
                mealsPerDay[daysAgo] = mealsPerDay[daysAgo] + 1
            }
        }
//
//        for(i in 0..mealsPerDay.size) {
//            Timber.e("Dan ima ovoliko jela: " + mealsPerDay[i])
//        }

        firstDay = mealsPerDay[0].toFloat()
        secondDay = mealsPerDay[1].toFloat()
        thirdDay = mealsPerDay[2].toFloat()
        fourthDay = mealsPerDay[3].toFloat()
        fifthDay = mealsPerDay[4].toFloat()
        sixthDay = mealsPerDay[5].toFloat()
        seventhDay = mealsPerDay[6].toFloat()

        println(firstDay)
        println(secondDay)
        println(thirdDay)
        println(fourthDay)
        println(fifthDay)
        println(sixthDay)
        println(seventhDay)


        barEntriesList.add(BarEntry(0f, firstDay))
        barEntriesList.add(BarEntry(1f, secondDay))
        barEntriesList.add(BarEntry(2f, thirdDay))
        barEntriesList.add(BarEntry(3f, fourthDay))
        barEntriesList.add(BarEntry(4f, fifthDay))
        barEntriesList.add(BarEntry(5f, sixthDay))
        barEntriesList.add(BarEntry(6f, seventhDay))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}