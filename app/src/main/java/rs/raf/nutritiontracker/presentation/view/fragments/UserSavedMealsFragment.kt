package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.data.models.ShortMeal
import rs.raf.nutritiontracker.databinding.FragmentUserMealsBinding
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.contract.UserContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.SavedMealAdapter
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.ShortMealAdapter
import rs.raf.nutritiontracker.presentation.view.states.MealsForAreaState
import rs.raf.nutritiontracker.presentation.view.states.SavedMealState
import rs.raf.nutritiontracker.presentation.view.states.UserState
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.UserViewModel
import rs.raf.nutrmealiontracker.presentation.view.fragments.SaveMealFragment
import timber.log.Timber

class UserSavedMealsFragment(
    private val username: String
) : Fragment(R.layout.fragment_user_meals) {
    private var _binding: FragmentUserMealsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: SavedMealAdapter
    private val mealViewModel: MealContract.ViewModel by sharedViewModel<MealViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initRecycler()
        initObservers()
    }

    private fun initRecycler() {
        adapter = SavedMealAdapter(onItemDeleteClicked = {
            mealViewModel.deleteMealById(it.mealId)
            mealViewModel.getSavedMealsByUser(username)
            Timber.e("ID " + it.mealId)
        }, onItemEditClicked = {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.container, EditSavedMealFragment(it)).addToBackStack(null)
            transaction.commit()
        }, listener = {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.container, AboutSavedMealFragment(it)).addToBackStack(null)
            transaction.commit()
        })
        binding.userSavedMealsRecyclerView.adapter = adapter
        binding.userSavedMealsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initObservers() {
        mealViewModel.getSavedMealsByUser(username)
        mealViewModel.savedMealState.observe(viewLifecycleOwner, Observer{
            renderSavedMeals(it)
        })
    }

    private fun renderSavedMeals(state: SavedMealState) {
        when (state) {
            is SavedMealState.Success -> {
//                showLoadingState(false)
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
                        it.dateInMillis
                    )
                }
                adapter.submitList(listOfSavedMeals)
            }
            is SavedMealState.Error -> {
//                showLoadingState(false)
                println("IZADJI MALA IZADJI MALA IZADJI BAR NA CAS ERROR")
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SavedMealState.DataFetched -> {
//                showLoadingState(false)
                println("IZADJI MALA IZADJI MALA IZADJI BAR NA CAS DATA FETCHED")
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is SavedMealState.Loading -> {
                println("IZADJI MALA IZADJI MALA IZADJI BAR NA CAS LOADING")
//                showLoadingState(true)
            }
        }
    }
}