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
    private var currPage: Int = 0
    private var goNext: Boolean = true

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
        adapter = ShortMealAdapter(onItemMoreClicked = {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, MealDetailedFragment(it, null)).addToBackStack(null)
            transaction.commit()
        },
            listener = {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.add(R.id.container, MealDetailedFragment(it, null)).addToBackStack(null)
                transaction.commit()
            })
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
                if(listOfMealsByIngredient.size > (currPage+1) * 10) {
                    adapter.submitList(listOfMealsByIngredient.subList(currPage * 10, (currPage + 1) * 10))
                } else {
                    val numLeft = (listOfMealsByIngredient.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMealsByIngredient.subList(currPage * 10, max))
                    goNext = false
                }
                binding.filterIngredientRecycler.postDelayed({
                    layoutManager?.scrollToPosition(0)
                }, 100)
            } else {
                val layoutManager = binding.filterIngredientRecycler.layoutManager
                listOfMealsByIngredient = listOfMealsByIngredient.sortedByDescending { it.strMeal }
                if(listOfMealsByIngredient.size > (currPage+1) * 10) {
                    adapter.submitList(listOfMealsByIngredient.subList(currPage * 10, (currPage + 1) * 10))
                } else {
                    val numLeft = (listOfMealsByIngredient.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMealsByIngredient.subList(currPage * 10, max))
                    goNext = false
                }
                binding.filterIngredientRecycler.postDelayed({
                    layoutManager?.scrollToPosition(0)
                }, 100)
            }
        }
        binding.ingredientNextButton.setOnClickListener {
            if(goNext) {
                this.currPage++
            }
            if(listOfMealsByIngredient.size > (currPage+1) * 10) {
                adapter.submitList(listOfMealsByIngredient.subList(currPage * 10, (currPage + 1) * 10))
            } else {
                if(goNext) {
                    val numLeft = (listOfMealsByIngredient.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMealsByIngredient.subList(currPage * 10, max))
                    goNext = false
                }
            }
        }
        binding.ingredientPrevButton.setOnClickListener {
            if(this.currPage > 0) {
                this.currPage--
                adapter.submitList(listOfMealsByIngredient.subList(currPage*10, (currPage+1)*10))
                goNext = true
            }
        }
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
                currPage = 0
                goNext = true
                if(listOfMealsByIngredient.size > (currPage+1) * 10) {
                    adapter.submitList(listOfMealsByIngredient.subList(currPage * 10, (currPage + 1) * 10))
                } else {
                    val numLeft = (listOfMealsByIngredient.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMealsByIngredient.subList(currPage * 10, max))
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
        binding.filterIngredientRecycler.isVisible = !loading
        binding.ingredientET.isVisible = !loading
        binding.filterIngredientFragmentET.isVisible = !loading
        binding.findByIngredientButton.isVisible = !loading
        binding.toggleButtonIngredient.isVisible = !loading
        binding.progressBarIngredient.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}