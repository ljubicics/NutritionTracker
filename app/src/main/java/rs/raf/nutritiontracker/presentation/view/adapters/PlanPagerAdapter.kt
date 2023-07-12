package rs.raf.nutritiontracker.presentation.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import rs.raf.nutritiontracker.presentation.view.fragments.FilterByAreaFragment
import rs.raf.nutritiontracker.presentation.view.fragments.FilterByCategoryFragment
import rs.raf.nutritiontracker.presentation.view.fragments.FilterByIngredientFragment
import rs.raf.nutritiontracker.presentation.view.fragments.PickMealsFragment
import rs.raf.nutritiontracker.presentation.view.fragments.PlanFragment

class PlanPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragmentList = mutableListOf<Fragment?>()

    override fun getItemCount(): Int {
        return 2 // Number of tabs
    }

    override fun createFragment(position: Int): Fragment {
        // Initialize the fragmentList if null or smaller than position
        if (fragmentList.size <= position) {
            fragmentList.addAll(mutableListOf(
                null, // Placeholder for position 0
                null  // Placeholder for position 1
            ))
        }

        // Check if the fragment already exists
        val existingFragment = fragmentList[position]
        if (existingFragment != null) {
            return existingFragment
        }

        // Create a new fragment and add it to the list
        val newFragment = when (position) {
            0 -> PickMealsFragment()
            1 -> PickMealsFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
        fragmentList[position] = newFragment
        return newFragment
    }

//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        fragmentList[position] = null
//        super.destroyItem(container, position, `object`)
//    }
}