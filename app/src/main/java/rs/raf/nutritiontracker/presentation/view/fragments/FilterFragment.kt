package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.core.KoinApplication.Companion.init
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentFilterBinding
import rs.raf.nutritiontracker.presentation.view.adapters.FilterPagerAdapter

class FilterFragment : Fragment(R.layout.fragment_filter) {
    private var _binding: FragmentFilterBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        println("USLA MALAA")
        binding.viewPager2.adapter = FilterPagerAdapter( parentFragmentManager, requireContext())
        binding.tabLayout.setupWithViewPager(binding.viewPager2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}