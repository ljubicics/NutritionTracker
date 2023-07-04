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
import androidx.viewpager2.widget.ViewPager2
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.databinding.FragmentCategoriesBinding
import rs.raf.nutritiontracker.modules.mealsLoadingPageModule
import rs.raf.nutritiontracker.presentation.contract.CategoryContract
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.contract.MealsForCategoryContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.MealForCategoryAdapter
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealsState
import rs.raf.nutritiontracker.presentation.viewmodel.CategoryViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealsForCategoryViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealsLoadingPageViewModel
import timber.log.Timber


// TODO: Dodati pronalazenje svih jela po sastojku, potreban je novi viewmodel koji ce da cuva jela
//  i neki checkbox da li zelite da pronadjete jela po sastojku
class CategoriesFragment : Fragment(R.layout.fragment_categories) {
    private val categoryViewModel: CategoryContract.ViewModel by sharedViewModel<CategoryViewModel>()
    private val mealsLoadingPageViewModel: MealsForCategoryContract.ViewModel by sharedViewModel<MealsLoadingPageViewModel>()

    private var _binding: FragmentCategoriesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter
    private lateinit var mealAdapter: MealForCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterCategoriesET.setText("")
        initRecycler()
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initListeners()
    }

    private fun initRecycler() {
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(context)
        adapter = CategoryAdapter(onItemMoreClicked = {
            CategoryDialogFragment(it.strCategoryDescription).show(
                childFragmentManager,
                CategoryDialogFragment.TAG
            )
        }, listener = {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.mainFragmentFcv, MealsForCategoryFragment(it)).addToBackStack(null)
            transaction.commit()
        })
        mealAdapter = MealForCategoryAdapter()
        binding.recyclerViewCategories.adapter = adapter
    }

    private fun initListeners() {
        binding.filterCategoriesET.doAfterTextChanged {
            val filter = it.toString()
            if(filter == "") {
                categoryViewModel.getAllCategories()
            } else {
                val list: List<String> = listOf("Beef", "Chicken", "Dessert", "Lamb", "Miscellaneous", "Pasta",
                    "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian", "Breakfast", "Goat")
                if(list.contains(filter)) {
                    categoryViewModel.getCategoriesByName(filter)
                } else {
                    mealsLoadingPageViewModel.getAllMealsByName(filter)
                }
            }
        }
    }

    private fun initObservers() {
        categoryViewModel.categoriesState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mealsLoadingPageViewModel.mealsForCategoryState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderStateMeal(it)
        })
        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
        // koji zadovoljavaju query
        categoryViewModel.getAllCategories()
        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
        // preko metode getAllMovies()
//        categoryViewModel.fetchAllCategories()
    }

    private fun renderState(state: CategoriesState) {
        when (state) {
            is CategoriesState.Success -> {
                showLoadingState(false)
                binding.recyclerViewCategories.adapter = adapter
                adapter.submitList(state.categories)
                adapter.notifyDataSetChanged()
            }
            is CategoriesState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is CategoriesState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is CategoriesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun renderStateMeal(state: MealsForCategoryState) {
        when (state) {
            is MealsForCategoryState.Success -> {
                showLoadingState(false)
                binding.recyclerViewCategories.adapter = mealAdapter
                mealAdapter.submitList(state.mealsForCategory)
                mealAdapter.notifyDataSetChanged()
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
        binding.categoriesTV.isVisible = !loading
        binding.filterCategoriesET.isVisible = !loading
        binding.recyclerViewCategories.isVisible = !loading
        binding.progressBar.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}