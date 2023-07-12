package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentFilterAreaBinding
import rs.raf.nutritiontracker.databinding.FragmentMealPlanBinding
import rs.raf.nutritiontracker.presentation.view.adapters.PlanPagerAdapter

class MealPlanFragment : Fragment(R.layout.fragment_meal_plan){
    private var _binding: FragmentMealPlanBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealPlanBinding.inflate(inflater, container, false)

        tabLayout = binding.mealPlanTabLayout
        viewPager = binding.mealPlanViewPager

        viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
//
//        // Initialize the ViewPager2 adapter
        if (viewPager.adapter == null) {
            val adapter = PlanPagerAdapter(this)
            viewPager.adapter = adapter

            // Connect the TabLayout with the ViewPager2
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Daily Plan"
                    1 -> "Pick Meals"
                    else -> ""
                }
            }.attach()
        }

        return binding.root
    }

}