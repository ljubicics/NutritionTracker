package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.core.KoinApplication.Companion.init
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentFilterBinding
import rs.raf.nutritiontracker.presentation.view.adapters.FilterPagerAdapter
import rs.raf.nutritiontracker.presentation.view.adapters.FilterPagerAdapter2

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
        println("ON CREATE VIEW")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("ON VIEW CREATED")
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        println("INITTT")
//        binding.viewPager2.adapter = FilterPagerAdapter( parentFragmentManager, requireContext())
//        binding.tabLayout.setupWithViewPager(binding.viewPager2)
        if (binding.viewPager2.adapter == null) {
            val adapter = FilterPagerAdapter2(this)
            binding.viewPager2.adapter = adapter

            // Connect the TabLayout with the ViewPager2
            TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
                tab.text = when (position) {
                    0 -> "By Category"
                    1 -> "By Area"
                    2 -> "By Ingredient"
                    else -> ""
                }
            }.attach()
        }
    }



    override fun onResume() {
        super.onResume()
        println("SELECTED " + binding.tabLayout.selectedTabPosition)
        binding.viewPager2.adapter?.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        println("PAUZICA")
    }

    override fun onStop() {
        super.onStop()
        println("STOPICC")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("DESTROJCIC")
        _binding = null
    }
}