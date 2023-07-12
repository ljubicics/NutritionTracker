package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.DayInTheWeek
import rs.raf.nutritiontracker.databinding.FragmentFilterAreaBinding
import rs.raf.nutritiontracker.databinding.FragmentPlanForDayBinding
import rs.raf.nutritiontracker.presentation.contract.PickMealsContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.MealsForDayAdapter
import rs.raf.nutritiontracker.presentation.viewmodel.PickMealsViewModel
import timber.log.Timber

class PlanForDayFragment(
    private val dayInTheWeek: DayInTheWeek
) : Fragment(R.layout.fragment_plan_for_day) {
    private var _binding: FragmentPlanForDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MealsForDayAdapter
    private val pickMealsViewModel: PickMealsContract.ViewModel by sharedViewModel<PickMealsViewModel>()
    private val day = dayInTheWeek.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanForDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        init()
        initObservers()
//        initListeners()
    }

    private fun init() {
        binding.dayNameTV.text = day
        if(dayInTheWeek == DayInTheWeek.MONDAY) {
           adapter.submitList(pickMealsViewModel.mondayState.value)
        } else if(dayInTheWeek == DayInTheWeek.TUESDAY) {
            adapter.submitList(pickMealsViewModel.tuesdayState.value)
        } else if(dayInTheWeek == DayInTheWeek.WEDNESDAY) {
            adapter.submitList(pickMealsViewModel.wednesdayState.value)
        } else if(dayInTheWeek == DayInTheWeek.THURSDAY) {
            adapter.submitList(pickMealsViewModel.thursdayState.value)
        } else if(dayInTheWeek == DayInTheWeek.FRIDAY) {
            adapter.submitList(pickMealsViewModel.fridayState.value)
        } else if(dayInTheWeek == DayInTheWeek.SATURDAY) {
            adapter.submitList(pickMealsViewModel.saturdayState.value)
        } else if(dayInTheWeek == DayInTheWeek.SUNDAY) {
            adapter.submitList(pickMealsViewModel.sundayState.value)
        }
    }

    private fun initRecycler() {
        adapter = MealsForDayAdapter(onItemDeleteClicked = {
            pickMealsViewModel.removeMealFromDay(it.mealId, dayInTheWeek)
        }, listener = {
            println(it)
        })
        binding.dayRecyclerTV.layoutManager = LinearLayoutManager(context)
        binding.dayRecyclerTV.adapter = adapter
    }

    private fun initObservers() {
        if(dayInTheWeek == DayInTheWeek.MONDAY) {
            pickMealsViewModel.mondayState.observe(viewLifecycleOwner, Observer{
                adapter.submitList(it)
            })
        }
        if(dayInTheWeek == DayInTheWeek.TUESDAY) {
            pickMealsViewModel.tuesdayState.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }
        if(dayInTheWeek == DayInTheWeek.WEDNESDAY) {
            pickMealsViewModel.wednesdayState.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }
        if(dayInTheWeek == DayInTheWeek.THURSDAY) {
            pickMealsViewModel.thursdayState.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }
        if(dayInTheWeek == DayInTheWeek.FRIDAY) {
            pickMealsViewModel.fridayState.observe(viewLifecycleOwner, Observer{
                adapter.submitList(it)
            })
        }
        if(dayInTheWeek == DayInTheWeek.SATURDAY) {
            pickMealsViewModel.saturdayState.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }
        if(dayInTheWeek == DayInTheWeek.TUESDAY) {
            pickMealsViewModel.sundayState.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}