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
import androidx.viewpager.widget.ViewPager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.ShortMeal
import rs.raf.nutritiontracker.databinding.FragmentFilterAreaBinding
import rs.raf.nutritiontracker.presentation.contract.MealsForAreaContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.ShortMealAdapter
import rs.raf.nutritiontracker.presentation.view.states.AreaState
import rs.raf.nutritiontracker.presentation.view.states.MealsForAreaState
import rs.raf.nutritiontracker.presentation.viewmodel.FilterMealsByAreaViewModel
import timber.log.Timber

class FilterByAreaFragment : Fragment(R.layout.fragment_filter_area) {
    lateinit var viewPager : ViewPager
    private var _binding: FragmentFilterAreaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: ShortMealAdapter
    private val mealsForAreaViewModel: MealsForAreaContract.ViewModel by sharedViewModel<FilterMealsByAreaViewModel>()
//    private val areaViewModel: AreaContract.ViewModel by sharedViewModel<AreaViewModel>()
    private lateinit var spinner: Spinner
    val dataList = mutableListOf<String>()
    private var listOfMealsByArea: List<ShortMeal> = listOf()
    private var currPage: Int = 0
    private var goNext: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterAreaBinding.inflate(inflater, container, false)
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
        spinner = binding.spinner2
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
        binding.filterAreaRecycler.adapter = adapter
        binding.filterAreaRecycler.layoutManager = LinearLayoutManager(context)
    }

    private fun initObservers() {
        mealsForAreaViewModel.getAllAreas()
        mealsForAreaViewModel.areaState.observe(viewLifecycleOwner, Observer {
            renderStateArea(it)
        })
        mealsForAreaViewModel.fetchAllMealsForArea("American")
        mealsForAreaViewModel.mealsForAreaState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderStateMeal(it)
        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedArea =  dataList[position]// Dobijanje izabrane kategorije na osnovu pozicije
                // Obrada izbora kategorije
                mealsForAreaViewModel.fetchAllMealsForArea(selectedArea)
                if(binding.toggleButtonArea.isChecked == false) {
                    binding.toggleButtonArea.isChecked = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Obrada kada nijedna stavka nije izabrana
            }
        }
    }

    private fun initListeners() {
        binding.filterAreaFragmentET.doAfterTextChanged {
            val filter = it.toString()
            var list: MutableList<ShortMeal> = mutableListOf()
            for(meal in listOfMealsByArea) {
                if(meal.strMeal.contains(filter, ignoreCase = true)) {
                    list.add(meal)
                }
            }
            adapter.submitList(list)
        }
        binding.toggleButtonArea.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                val layoutManager = binding.filterAreaRecycler.layoutManager
                listOfMealsByArea = listOfMealsByArea.sortedBy { it.strMeal }
                if(listOfMealsByArea.size > (currPage+1) * 10) {
                    adapter.submitList(listOfMealsByArea.subList(currPage * 10, (currPage + 1) * 10))
                } else {
                    val numLeft = (listOfMealsByArea.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMealsByArea.subList(currPage * 10, max))
                    goNext = false
                }
                binding.filterAreaRecycler.postDelayed({
                    layoutManager?.scrollToPosition(0)
                }, 100)
            } else {
                val layoutManager = binding.filterAreaRecycler.layoutManager
                listOfMealsByArea = listOfMealsByArea.sortedByDescending { it.strMeal }
                if(listOfMealsByArea.size > (currPage+1) * 10) {
                    adapter.submitList(listOfMealsByArea.subList(currPage * 10, (currPage + 1) * 10))
                } else {
                    val numLeft = (listOfMealsByArea.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMealsByArea.subList(currPage * 10, max))
                    goNext = false
                }
                binding.filterAreaRecycler.postDelayed({
                    layoutManager?.scrollToPosition(0)
                }, 100)
            }
        }
        binding.areaNextButton.setOnClickListener {
            if(goNext) {
                this.currPage++
            }
            if(listOfMealsByArea.size > (currPage+1) * 10) {
                adapter.submitList(listOfMealsByArea.subList(currPage * 10, (currPage + 1) * 10))
            } else {
                if(goNext) {
                    val numLeft = (listOfMealsByArea.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMealsByArea.subList(currPage * 10, max))
                    goNext = false
                }
            }
        }
        binding.areaPrevButton.setOnClickListener {
            if(this.currPage > 0) {
                this.currPage--
                adapter.submitList(listOfMealsByArea.subList(currPage*10, (currPage+1)*10))
                goNext = true
            }
        }
    }

    private fun renderStateMeal(state: MealsForAreaState) {
        when (state) {
            is MealsForAreaState.Success -> {
                showLoadingState(false)
                listOfMealsByArea = state.mealsForCategory.map {
                    ShortMeal(
                        it.strMeal,
                        it.strMealThumb,
                        it.idMeal,
                    )
                }
                currPage = 0
                goNext = true
                if(listOfMealsByArea.size > (currPage+1) * 10) {
                    adapter.submitList(listOfMealsByArea.subList(currPage * 10, (currPage + 1) * 10))
                } else {
                    val numLeft = (listOfMealsByArea.size - (currPage * 10))
                    val max = currPage * 10 + numLeft
                    adapter.submitList(listOfMealsByArea.subList(currPage * 10, max))
                    goNext = false
                }
            }
            is MealsForAreaState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsForAreaState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsForAreaState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun renderStateArea(state: AreaState) {
        when (state) {
            is AreaState.Success -> {
                showLoadingState(false)
                val spinner = binding.spinner2
                val areas = state.areas // Dobijanje liste kategorija iz stanja
                for (area in areas) {
                    dataList.add(area.strArea) // Dodajte ime kategorije u dataList
                }
                val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dataList)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = arrayAdapter
            }
            is AreaState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is AreaState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is AreaState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.filterAreaRecycler.isVisible = !loading
        binding.spinner2.isVisible = !loading
        binding.filterAreaFragmentET.isVisible = !loading
        binding.progressBar4.isVisible = loading
    }
}