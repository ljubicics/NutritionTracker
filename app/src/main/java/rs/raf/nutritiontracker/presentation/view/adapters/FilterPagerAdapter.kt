package rs.raf.nutritiontracker.presentation.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.nutritiontracker.presentation.view.fragments.FilterByAreaFragment
import rs.raf.nutritiontracker.presentation.view.fragments.FilterByCategoryFragment
import rs.raf.nutritiontracker.presentation.view.fragments.FilterByIngredientFragment

class FilterPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 3
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
        const val FRAGMENT_3 = 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> FilterByCategoryFragment()
            FRAGMENT_2 -> FilterByAreaFragment()
            else -> FilterByIngredientFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_1 -> "1"
            FRAGMENT_2 -> "2"
            else -> "3"
        }
    }

}