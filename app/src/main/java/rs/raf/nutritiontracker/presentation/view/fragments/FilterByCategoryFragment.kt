package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.databinding.FragmentFilterCategoryBinding
import rs.raf.nutritiontracker.presentation.contract.CategoryContract
import rs.raf.nutritiontracker.presentation.contract.MealsForCategoryContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.MealForCategoryAdapter
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import rs.raf.nutritiontracker.presentation.viewmodel.CategoryViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.FilterMealsByCatViewModel
import timber.log.Timber

class FilterByCategoryFragment : Fragment(R.layout.fragment_filter_category) {
    private var _binding: FragmentFilterCategoryBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: MealForCategoryAdapter
    private val mealsForCategoryViewModel: MealsForCategoryContract.ViewModel by sharedViewModel<FilterMealsByCatViewModel>()
    private val categoryViewModel: CategoryContract.ViewModel by sharedViewModel<CategoryViewModel>()
    private lateinit var spinner: Spinner
    val dataList = mutableListOf<String>()
    private lateinit var listOfMealsByCat: List<MealForCategory>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        init()
    }

    private fun init() {
        initUi()
        initObservers()
        initListeners()
    }

    private fun initUi() {
        spinner = binding.spinner
    }

    private fun initRecycler() {
        adapter = MealForCategoryAdapter()
        binding.filterCategoryRecycler.adapter = adapter
        binding.filterCategoryRecycler.layoutManager = LinearLayoutManager(context)
    }

    private fun initListeners() {
        binding.filterCategoryFragmentET.doAfterTextChanged {
            val filter = it.toString()
            mealsForCategoryViewModel.getAllMealsByName(filter)
        }
        binding.toggleButton.isChecked = true
        binding.toggleButton.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                val layoutManager = binding.filterCategoryRecycler.layoutManager
                listOfMealsByCat = listOfMealsByCat.sortedBy { it.strMeal }
                adapter.submitList(listOfMealsByCat)
                binding.filterCategoryRecycler.postDelayed({
                    layoutManager?.scrollToPosition(0)
                }, 100)
            } else {
                val layoutManager = binding.filterCategoryRecycler.layoutManager
                listOfMealsByCat = listOfMealsByCat.sortedByDescending { it.strMeal }
                adapter.submitList(listOfMealsByCat)
                binding.filterCategoryRecycler.postDelayed({
                    layoutManager?.scrollToPosition(0)
                }, 100)
            }
        }
    }

    private fun initObservers() {
        mealsForCategoryViewModel.mealsForCategoryState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderStateMeal(it)
        })
        categoryViewModel.categoriesState.observe(viewLifecycleOwner, Observer {
            renderStateCategory(it)
        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory =  dataList[position]// Dobijanje izabrane kategorije na osnovu pozicije
                // Obrada izbora kategorije
                mealsForCategoryViewModel.getAllMealsForCategory(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Obrada kada nijedna stavka nije izabrana
            }
        }
    }

    private fun renderStateMeal(state: MealsForCategoryState) {
        when (state) {
            is MealsForCategoryState.Success -> {
                showLoadingState(false)
                listOfMealsByCat = state.mealsForCategory
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

    private fun renderStateCategory(state: CategoriesState) {
        when (state) {
            is CategoriesState.Success -> {
                showLoadingState(false)
                val spinner = binding.spinner
                val categories = state.categories // Dobijanje liste kategorija iz stanja
                for (category in categories) {
                    if(dataList.size < 14) {
                        dataList.add(category.strCategory) // Dodajte ime kategorije u dataList
                    }
                }
                println("Data list velicina je: " + dataList.size)
                val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dataList)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = arrayAdapter
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

    private fun showLoadingState(loading: Boolean) {
        binding.filterCategoryRecycler.isVisible = !loading
        binding.spinner.isVisible = !loading
        binding.filterCategoryFragmentET.isVisible = !loading
        binding.progressBar3.isVisible = loading
    }

}