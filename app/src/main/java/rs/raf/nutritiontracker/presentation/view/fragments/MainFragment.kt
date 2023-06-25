package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.BottomNavigationBinding
import rs.raf.nutritiontracker.databinding.FragmentLoginBinding
import rs.raf.nutritiontracker.presentation.view.adapters.PagerAdapter

class MainFragment : Fragment(R.layout.bottom_navigation) {
    lateinit var viewPager : ViewPager
    private var _binding: BottomNavigationBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initViewPager()
        initBottomNav()
    }

    private fun initViewPager() {
        viewPager = binding.viewPager
        viewPager.adapter = PagerAdapter(parentFragmentManager, requireContext())
    }

    private fun initBottomNav() {

        binding.bottomNavigation.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.navigation_1 -> {
                    binding.viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false)
                    viewPager.adapter?.notifyDataSetChanged()
                }
                R.id.navigation_2 -> {
                    binding.viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false)
                }
                R.id.navigation_3 -> {
                    binding.viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false)
                }
                R.id.navigation_4 -> {
                    binding.viewPager.setCurrentItem(PagerAdapter.FRAGMENT_4, false)
                }
            }
                true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}