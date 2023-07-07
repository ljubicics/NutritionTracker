package rs.raf.nutritiontracker.presentation.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.ListFragment
import rs.raf.nutritiontracker.presentation.view.fragments.CategoriesFragment
import rs.raf.nutritiontracker.presentation.view.fragments.FilterFragment
import rs.raf.nutritiontracker.presentation.view.fragments.MealsFragment
import rs.raf.nutritiontracker.presentation.view.fragments.PlanFragment
import rs.raf.nutritiontracker.presentation.view.fragments.ProfileFragment

class PagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 5
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
        const val FRAGMENT_3 = 2
        const val FRAGMENT_4 = 3
        const val FRAGMENT_5 = 4
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> CategoriesFragment()
            FRAGMENT_2 -> FilterFragment()
            FRAGMENT_3 -> MealsFragment()
            FRAGMENT_4 -> PlanFragment()
            else -> ProfileFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_1 -> return "1"
            FRAGMENT_2 -> return "2"
            FRAGMENT_3 -> return "3"
            FRAGMENT_4 -> return "4"
            else -> return "5"
        }
    }

}