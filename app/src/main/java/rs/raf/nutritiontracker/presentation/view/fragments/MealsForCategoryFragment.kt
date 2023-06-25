package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.databinding.FragmentMealsforcategoryBinding
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.contract.MealsForCategoryContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.MealForCategoryAdapter
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealsForCategoryViewModel
import timber.log.Timber

class MealsForCategoryFragment(
    private val category: Category
) : Fragment(R.layout.fragment_mealsforcategory){
    private val mealsForCategoryViewModel: MealsForCategoryContract.ViewModel by sharedViewModel<MealsForCategoryViewModel>()
    private val mealViewModel: MealContract.ViewModel by sharedViewModel<MealViewModel>()

    private var _binding: FragmentMealsforcategoryBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: MealForCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealsforcategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        binding.recyclerViewMealsForCat.layoutManager = LinearLayoutManager(context)
        adapter = MealForCategoryAdapter()
        binding.recyclerViewMealsForCat.adapter = adapter
    }

    private fun initListeners() {
//        binding.filterCategoriesET.doAfterTextChanged {
//            val filter = it.toString()
//            categoryViewModel.getCategoriesByName(filter)
//        }
    }

    private fun initObservers() {
        mealsForCategoryViewModel.mealsForCategoryState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
        // koji zadovoljavaju query
        mealsForCategoryViewModel.getAllMealsForCategory(category.strCategory)
        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
        // preko metode getAllMovies()
//        categoryViewModel.fetchAllCategories()
    }

    private fun renderState(state: MealsForCategoryState) {
        when (state) {
            is MealsForCategoryState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.mealsForCategory)
            }
            is MealsForCategoryState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsForCategoryState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsForCategoryState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.mealsForCatTV.isVisible = !loading
        binding.progressBar2.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}