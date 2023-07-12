package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.DayInTheWeek
import rs.raf.nutritiontracker.data.models.MealForPlan
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.data.models.User
import rs.raf.nutritiontracker.databinding.FragmentPickMealsBinding
import rs.raf.nutritiontracker.presentation.contract.PickMealsContract
import rs.raf.nutritiontracker.presentation.contract.UserContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.PickMealAdapterLocal
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.PickMealAdapterRemote
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.SavedMealState
import rs.raf.nutritiontracker.presentation.view.states.UserState
import rs.raf.nutritiontracker.presentation.viewmodel.PickMealsViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.UserViewModel
import java.util.Calendar

class PickMealsFragment : Fragment(R.layout.fragment_pick_meals) {
    private var _binding: FragmentPickMealsBinding? = null

    private lateinit var adapterLocal: PickMealAdapterLocal
    private lateinit var adapterRemote: PickMealAdapterRemote
    private val pickMealsViewModel: PickMealsContract.ViewModel by sharedViewModel<PickMealsViewModel>()
    private val userViewModel: UserContract.ViewModel by sharedViewModel<UserViewModel>()
    private lateinit var user: User
    private lateinit var switch: Switch

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPickMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUI()
        initObservers()
        initRecycler()
        initListeners()
    }

    private fun initUI() {
        switch = binding.pickMealSwitchButton
    }

    private fun initRecycler() {
        binding.pickMealListRv.layoutManager = LinearLayoutManager(context)
        adapterLocal = PickMealAdapterLocal(onItemAddClicked = {
            val i = pickMealsViewModel.number.value
            val mealForPlan = MealForPlan(
                i!!,
                it.idMeal,
                it.strMeal,
                it.strDrinkAlternate,
                it.strCategory,
                it.strArea,
                it.strInstructions,
                it.strMealThumb,
                it.strTags,
                it.strYoutube,
                it.strIngredient1,
                it.strIngredient2,
                it.strIngredient3,
                it.strIngredient4,
                it.strIngredient5,
                it.strIngredient6,
                it.strIngredient7,
                it.strIngredient8,
                it.strIngredient9,
                it.strIngredient10,
                it.strIngredient11,
                it.strIngredient12,
                it.strIngredient13,
                it.strIngredient14,
                it.strIngredient15,
                it.strIngredient16,
                it.strIngredient17,
                it.strIngredient18,
                it.strIngredient19,
                it.strIngredient20,
                it.strMeasure1,
                it.strMeasure2,
                it.strMeasure3,
                it.strMeasure4,
                it.strMeasure5,
                it.strMeasure6,
                it.strMeasure7,
                it.strMeasure8,
                it.strMeasure9,
                it.strMeasure10,
                it.strMeasure11,
                it.strMeasure12,
                it.strMeasure13,
                it.strMeasure14,
                it.strMeasure15,
                it.strMeasure16,
                it.strMeasure17,
                it.strMeasure18,
                it.strMeasure19,
                it.strMeasure20,
                it.strSource,
                it.strImageSource,
                it.strCreativeCommonsConfirmed,
                it.dateModified,
                it.user,
                it.mealType,
                it.dateInMillis,
                it.dateAdded
            )
            pickMealsViewModel.addMealToDay(mealForPlan, calculateDay(it))
            pickMealsViewModel.increaseNumber()
            val state = pickMealsViewModel.mondayState
            println("JELO" + state.value?.size)
        }, listener = {
            val i = pickMealsViewModel.number.value
            val mealForPlan = MealForPlan(
                i!!,
                it.idMeal,
                it.strMeal,
                it.strDrinkAlternate,
                it.strCategory,
                it.strArea,
                it.strInstructions,
                it.strMealThumb,
                it.strTags,
                it.strYoutube,
                it.strIngredient1,
                it.strIngredient2,
                it.strIngredient3,
                it.strIngredient4,
                it.strIngredient5,
                it.strIngredient6,
                it.strIngredient7,
                it.strIngredient8,
                it.strIngredient9,
                it.strIngredient10,
                it.strIngredient11,
                it.strIngredient12,
                it.strIngredient13,
                it.strIngredient14,
                it.strIngredient15,
                it.strIngredient16,
                it.strIngredient17,
                it.strIngredient18,
                it.strIngredient19,
                it.strIngredient20,
                it.strMeasure1,
                it.strMeasure2,
                it.strMeasure3,
                it.strMeasure4,
                it.strMeasure5,
                it.strMeasure6,
                it.strMeasure7,
                it.strMeasure8,
                it.strMeasure9,
                it.strMeasure10,
                it.strMeasure11,
                it.strMeasure12,
                it.strMeasure13,
                it.strMeasure14,
                it.strMeasure15,
                it.strMeasure16,
                it.strMeasure17,
                it.strMeasure18,
                it.strMeasure19,
                it.strMeasure20,
                it.strSource,
                it.strImageSource,
                it.strCreativeCommonsConfirmed,
                it.dateModified,
                it.user,
                it.mealType,
                it.dateInMillis,
                it.dateAdded
            )
            pickMealsViewModel.addMealToDay(mealForPlan, calculateDay(it))
            pickMealsViewModel.increaseNumber()
        })
        adapterRemote = PickMealAdapterRemote(onItemAddClicked = {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, PickRemoteMealFragment(it)).addToBackStack(null)
            transaction.commit()
        }, listener = {
            println(it)
        })
        binding.pickMealListRv.adapter = adapterLocal
    }

    private fun initObservers() {
        val userFromViewModel = userViewModel.userState.value
        if(userFromViewModel is UserState.Success) {
            user = userFromViewModel.users[0]
        }
        pickMealsViewModel.getAllMealsLocal(user.username)
        pickMealsViewModel.localMealsState.observe(viewLifecycleOwner, Observer {
            renderStateLocal(it)
        })
        pickMealsViewModel.remoteMealsState.observe(viewLifecycleOwner, Observer {
            renderStateRemote(it)
        })
    }

    private fun initListeners() {
        switch.setOnCheckedChangeListener{buttonView, isChecked ->
            if(isChecked) {
                pickMealsViewModel.getAllMealsRemote()
            } else {
                pickMealsViewModel.getAllMealsLocal(user.username)
            }
        }
    }

    private fun renderStateRemote(state: MealsForCategoryState) {
        when (state) {
            is MealsForCategoryState.Success -> {
//                showLoadingState(false)
                binding.pickMealListRv.adapter = adapterRemote
                adapterRemote.submitList(state.mealsForCategory)
                adapterRemote.notifyDataSetChanged()
            }
            is MealsForCategoryState.Error -> {
//                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsForCategoryState.DataFetched -> {
//                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsForCategoryState.Loading -> {
//                showLoadingState(true)
            }
        }
    }

    private fun renderStateLocal(state: SavedMealState) {
        when (state) {
            is SavedMealState.Success -> {
//                showLoadingState(false)
                binding.pickMealListRv.adapter = adapterLocal
                val listOfSavedMeals = state.savedMeals.map {
                    SavedMeal(
                        it.mealId,
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        it.strIngredient1,
                        it.strIngredient2,
                        it.strIngredient3,
                        it.strIngredient4,
                        it.strIngredient5,
                        it.strIngredient6,
                        it.strIngredient7,
                        it.strIngredient8,
                        it.strIngredient9,
                        it.strIngredient10,
                        it.strIngredient11,
                        it.strIngredient12,
                        it.strIngredient13,
                        it.strIngredient14,
                        it.strIngredient15,
                        it.strIngredient16,
                        it.strIngredient17,
                        it.strIngredient18,
                        it.strIngredient19,
                        it.strIngredient20,
                        it.strMeasure1,
                        it.strMeasure2,
                        it.strMeasure3,
                        it.strMeasure4,
                        it.strMeasure5,
                        it.strMeasure6,
                        it.strMeasure7,
                        it.strMeasure8,
                        it.strMeasure9,
                        it.strMeasure10,
                        it.strMeasure11,
                        it.strMeasure12,
                        it.strMeasure13,
                        it.strMeasure14,
                        it.strMeasure15,
                        it.strMeasure16,
                        it.strMeasure17,
                        it.strMeasure18,
                        it.strMeasure19,
                        it.strMeasure20,
                        it.strSource,
                        it.strImageSource,
                        it.strCreativeCommonsConfirmed,
                        it.dateModified,
                        it.user,
                        it.mealType,
                        it.dateInMillis,
                        it.dateAdded
                    )
                }
                adapterLocal.submitList(listOfSavedMeals)
                adapterLocal.notifyDataSetChanged()
            }
            is SavedMealState.Error -> {
//                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SavedMealState.DataFetched -> {
//                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is SavedMealState.Loading -> {
//                showLoadingState(true)
            }
        }
    }

    private fun calculateDay(meal: SavedMeal) : DayInTheWeek {
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