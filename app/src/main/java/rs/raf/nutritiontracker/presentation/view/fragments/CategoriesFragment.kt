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
import rs.raf.nutritiontracker.data.models.ShortMeal
import rs.raf.nutritiontracker.databinding.FragmentCategoriesBinding
import rs.raf.nutritiontracker.presentation.contract.CategoryContract
import rs.raf.nutritiontracker.presentation.contract.MealsForCategoryContract
import rs.raf.nutritiontracker.presentation.contract.MealsForIngredientContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.MealForCategoryAdapter
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.ShortMealAdapter
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealsForIngredientState
import rs.raf.nutritiontracker.presentation.viewmodel.CategoryViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.FilterMealsByIngredientViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealsLoadingPageViewModel
import timber.log.Timber


// TODO: Dodati pronalazenje svih jela po sastojku, dodati neki toggle koji ce da pita da li se trazi jelo po sastojku!!
class CategoriesFragment : Fragment(R.layout.fragment_categories) {
    private val categoryViewModel: CategoryContract.ViewModel by sharedViewModel<CategoryViewModel>()
    // cuva jela iz baze koja imaju trazeno ime
    private val mealsLoadingPageViewModel: MealsForCategoryContract.ViewModel by sharedViewModel<MealsLoadingPageViewModel>()
    private val mealsForIngredientViewModel: MealsForIngredientContract.ViewModel by sharedViewModel<FilterMealsByIngredientViewModel>()
    private lateinit var listOfMealsByIngredient: List<ShortMeal>


    private var _binding: FragmentCategoriesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter
    private lateinit var mealAdapter: MealForCategoryAdapter
    private lateinit var shortMealAdapter: ShortMealAdapter

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
        mealAdapter = MealForCategoryAdapter(
            onItemMoreClicked = {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.add(R.id.mainFragmentFcv, MealDetailedFragment(null, it)).addToBackStack(null)
                transaction.commit()
            },
            listener = {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.add(R.id.mainFragmentFcv, MealDetailedFragment(null, it)).addToBackStack(null)
                transaction.commit()
            }
        )
        shortMealAdapter = ShortMealAdapter(onItemMoreClicked = {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.mainFragmentFcv, MealDetailedFragment(it, null)).addToBackStack(null)
            transaction.commit()
        },
            listener = {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.add(R.id.mainFragmentFcv, MealDetailedFragment(it, null)).addToBackStack(null)
                transaction.commit()
            })
        binding.recyclerViewCategories.adapter = adapter
    }

    private fun initListeners() {
        binding.findByIngredientButton.isVisible = false
        binding.filterCategoriesET.doAfterTextChanged {
            val filter = it.toString()
            if(!binding.categoriesSwitch.isChecked) {
                if (filter == "") {
                    categoryViewModel.getAllCategories()
                } else {
                    val list: List<String> = listOf(
                        "Beef", "Chicken", "Dessert", "Lamb", "Miscellaneous", "Pasta",
                        "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian", "Breakfast", "Goat"
                    )
                    if (list.contains(filter)) {
                        categoryViewModel.getCategoriesByName(filter)
                    } else {
                        mealsLoadingPageViewModel.getAllMealsByName(filter)
                    }
                }
            }
        }

        binding.categoriesSwitch.setOnCheckedChangeListener{buttonView, isChecked ->
            binding.findByIngredientButton.isVisible = isChecked
            if(!isChecked) {
                categoryViewModel.getAllCategories()
                binding.filterCategoriesET.setText("")
            }
        }
        binding.findByIngredientButton.setOnClickListener {
            val text = binding.filterCategoriesET.text
            val replacedString = text.replace(Regex(" "), "_").toLowerCase()
            mealsForIngredientViewModel.fetchAllMealsForIngredient(replacedString)
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
        mealsForIngredientViewModel.mealsForIngredientState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderStateMealForIngredient(it)
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
    private fun renderStateMealForIngredient(state: MealsForIngredientState) {
        when (state) {
            is MealsForIngredientState.Success -> {
                showLoadingState(false)
                binding.recyclerViewCategories.adapter = shortMealAdapter
                listOfMealsByIngredient = state.mealsForCategory.map {
                    ShortMeal(
                        it.strMeal,
                        it.strMealThumb,
                        it.idMeal,
                    )
                }
                shortMealAdapter.submitList(listOfMealsByIngredient)
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
        binding.categoriesTV.isVisible = !loading
        binding.filterCategoriesET.isVisible = !loading
        binding.recyclerViewCategories.isVisible = !loading
        binding.progressBar.isVisible = loading
    }

    override fun onResume() {
        super.onResume()
        categoryViewModel.getAllCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}