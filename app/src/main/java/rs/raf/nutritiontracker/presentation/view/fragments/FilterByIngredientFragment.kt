package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.ShortMeal
import rs.raf.nutritiontracker.databinding.FragmentFilterCategoryBinding
import rs.raf.nutritiontracker.databinding.FragmentFilterIngredientBinding
import rs.raf.nutritiontracker.presentation.contract.MealsForIngredientContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.ShortMealAdapter
import rs.raf.nutritiontracker.presentation.view.states.MealsForAreaState
import rs.raf.nutritiontracker.presentation.view.states.MealsForIngredientState
import rs.raf.nutritiontracker.presentation.viewmodel.FilterMealsByIngredientViewModel
import timber.log.Timber

class FilterByIngredientFragment : Fragment(R.layout.fragment_filter_ingredient) {
    lateinit var viewPager : ViewPager
    private var _binding: FragmentFilterIngredientBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: ShortMealAdapter
    private val mealsForIngredientViewModel: MealsForIngredientContract.ViewModel by sharedViewModel<FilterMealsByIngredientViewModel>()
    private lateinit var listOfMealsByIngredient: List<ShortMeal>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        init()
    }

    private fun init() {
        initObservers()
        initListeners()
    }

    private fun initRecycler() {
        adapter = ShortMealAdapter()
        binding.filterIngredientRecycler.adapter = adapter
        binding.filterIngredientRecycler.layoutManager = LinearLayoutManager(context)
    }
    private fun initObservers() {
        mealsForIngredientViewModel.fetchAllMealsForIngredient("chicken_breast")
        mealsForIngredientViewModel.mealsForIngredientState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderStateMeal(it)
        })
    }

    private fun initListeners() {
        binding.findByIngredientButton.setOnClickListener {
            val text = binding.ingredientET.text
            val replacedString = text.replace(Regex(" "), "_").toLowerCase()
            mealsForIngredientViewModel.fetchAllMealsForIngredient(replacedString)
        }
        binding.filterIngredientFragmentET.doAfterTextChanged {
            val filter = it.toString();
            var list: MutableList<ShortMeal> = mutableListOf()
            for (meal in listOfMealsByIngredient) {
                if(meal.strMeal.contains(filter, ignoreCase = true)) {
                    list.add(meal)
                }
            }
            adapter.submitList(list)
        }
        binding.toggleButtonIngredient.isChecked = true
        binding.toggleButtonIngredient.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                val layoutManager = binding.filterIngredientRecycler.layoutManager
                listOfMealsByIngredient = listOfMealsByIngredient.sortedBy { it.strMeal }
                adapter.submitList(listOfMealsByIngredient)
                binding.filterIngredientRecycler.postDelayed({
                    layoutManager?.scrollToPosition(0)
                }, 100)
            } else {
                val layoutManager = binding.filterIngredientRecycler.layoutManager
                listOfMealsByIngredient = listOfMealsByIngredient.sortedByDescending { it.strMeal }
                adapter.submitList(listOfMealsByIngredient)
                binding.filterIngredientRecycler.postDelayed({
                    layoutManager?.scrollToPosition(0)
                }, 100)
            }
        }
        binding.ingredientET
    }
    private fun renderStateMeal(state: MealsForIngredientState) {
        when (state) {
            is MealsForIngredientState.Success -> {
                showLoadingState(false)
                listOfMealsByIngredient = state.mealsForCategory.map {
                    ShortMeal(
                        it.strMeal,
                        it.strMealThumb,
                        it.idMeal,
                    )
                }
                adapter.submitList(listOfMealsByIngredient)
            }
            is MealsForIngredientState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsForIngredientState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsForIngredientState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.filterIngredientRecycler.isVisible = !loading
        binding.ingredientET.isVisible = !loading
        binding.filterIngredientFragmentET.isVisible = !loading
        binding.findByIngredientButton.isVisible = !loading
        binding.toggleButtonIngredient.isVisible = !loading
        binding.progressBarIngredient.isVisible = loading
    }
}