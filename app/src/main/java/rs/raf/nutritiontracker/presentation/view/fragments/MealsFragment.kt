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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.ShortMeal
import rs.raf.nutritiontracker.databinding.FragmentMealsBinding
import rs.raf.nutritiontracker.presentation.contract.MealsForCategoryContract
import rs.raf.nutritiontracker.presentation.contract.MealsForIngredientContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.ShortMealAdapter
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealsForIngredientState
import rs.raf.nutritiontracker.presentation.viewmodel.ListMealsViewModel
import timber.log.Timber

class MealsFragment : Fragment(R.layout.fragment_meals){
    private var _binding: FragmentMealsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ShortMealAdapter
    private val listMealsViewModel: MealsForCategoryContract.ViewModel by sharedViewModel<ListMealsViewModel>()
    private val listMealsIngredientViewModel: MealsForIngredientContract.ViewModel by sharedViewModel<ListMealsViewModel>()
    private var listOfMeals: List<ShortMeal> = listOf()
    private var currPage: Int = 0
    private var goNext: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealsBinding.inflate(inflater, container, false)
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
        adapter = ShortMealAdapter(onItemMoreClicked = {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.container, MealDetailedFragment(it, null)).addToBackStack(null)
            transaction.commit()
        },
            listener = {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.add(R.id.container, MealDetailedFragment(it, null)).addToBackStack(null)
                transaction.commit()
            })
        binding.mealsRecycler.adapter = adapter
        binding.mealsRecycler.layoutManager = LinearLayoutManager(context)
    }

    private fun initListeners() {
        if(!binding.listMealsToggleButton.isChecked) {
            binding.button.isVisible = false
        }
        binding.listMealsToggleButton.setOnCheckedChangeListener{buttonView, isChecked ->
            binding.button.isVisible = isChecked
            if(!isChecked) {
                listMealsViewModel.getAllMeals()
                val layoutManager = binding.mealsRecycler.layoutManager
                binding.mealsRecycler.postDelayed({
                    layoutManager?.scrollToPosition(0)
                }, 100)
            }
        }
        binding.editTextListMeals.doAfterTextChanged {
            val filter = it.toString()
            if(!binding.listMealsToggleButton.isChecked) {
                var list: MutableList<ShortMeal> = mutableListOf()
                for (meal in listOfMeals) {
                    if (meal.strMeal.contains(filter, ignoreCase = true)) {
                        list.add(meal)
                    }
                }
//                adapter.submitList(list)
                currPage = 0
                if(list.size > (currPage+1) * 10) {
                    adapter.submitList(listOfMeals.subList(currPage * 10, (currPage + 1) * 10))
                } else {
                    val numLeft = (list.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMeals.subList(currPage * 10, max))
                    goNext = false
                }
            } else {
                binding.button.setOnClickListener {
                    currPage = 0
                    listMealsIngredientViewModel.fetchAllMealsForIngredient(filter)
                }
            }
        }
        binding.nextPage.setOnClickListener {
            if(goNext) {
                this.currPage++
            }
            if(listOfMeals.size > (currPage+1) * 10) {
                adapter.submitList(listOfMeals.subList(currPage * 10, (currPage + 1) * 10))
            } else {
                if(goNext) {
                    val numLeft = (listOfMeals.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMeals.subList(currPage * 10, max))
                    goNext = false
                }
            }
        }
        binding.prevPage.setOnClickListener {
            if(this.currPage > 0) {
                this.currPage--
                adapter.submitList(listOfMeals.subList(currPage*10, (currPage+1)*10))
                goNext = true
            }
        }
    }

    private fun initObservers() {
        listMealsViewModel.getAllMeals()
        listMealsViewModel.mealsForCategoryState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            currPage = 0
            goNext = true
            renderStateMeal(it)
        })
        listMealsIngredientViewModel.mealsForIngredientState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            currPage = 0
            renderStateMealIngredient(it)
        })
    }

    private fun renderStateMeal(state: MealsForCategoryState) {
        when (state) {
            is MealsForCategoryState.Success -> {
                showLoadingState(false)
                listOfMeals = state.mealsForCategory.map {
                    ShortMeal(
                        it.strMeal,
                        it.strMealThumb,
                        it.idMeal,
                    )
                }
                adapter.submitList(listOfMeals.subList(currPage*10, (currPage+1)*10))
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
    private fun renderStateMealIngredient(state: MealsForIngredientState) {
        when (state) {
            is MealsForIngredientState.Success -> {
                showLoadingState(false)
                Timber.e("USOOOOOOOOOOOOOOOOO " + currPage)
                listOfMeals = state.mealsForCategory.map {
                    ShortMeal(
                        it.strMeal,
                        it.strMealThumb,
                        it.idMeal,
                    )
                }
                if(listOfMeals.size > (currPage+1) * 10) {
                    adapter.submitList(listOfMeals.subList(currPage * 10, (currPage + 1) * 10))
                } else {
                    val numLeft = (listOfMeals.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMeals.subList(currPage * 10, max))
                    goNext = false
                }
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
        binding.mealsRecycler.isVisible = !loading
        binding.editTextListMeals.isVisible = !loading
        binding.listMealsToggleButton.isVisible = !loading
        binding.progressBarListMeals.isVisible = loading
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}