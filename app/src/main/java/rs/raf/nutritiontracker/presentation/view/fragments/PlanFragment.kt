package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentCategoriesBinding
import rs.raf.nutritiontracker.databinding.FragmentPlanBinding
import rs.raf.nutritiontracker.presentation.contract.CategoryContract
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.viewmodel.CategoryViewModel
import timber.log.Timber

class PlanFragment : Fragment(R.layout.fragment_plan) {

    private var _binding: FragmentPlanBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        init()
    }

    private fun init() {

    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {

    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }


    private fun showLoadingState(loading: Boolean) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}