package rs.raf.nutritiontracker.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentFilterCategoryBinding
import rs.raf.nutritiontracker.databinding.FragmentFilterIngredientBinding

class FilterByIngredientFragment : Fragment(R.layout.fragment_filter_category) {
    lateinit var viewPager : ViewPager
    private var _binding: FragmentFilterIngredientBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}