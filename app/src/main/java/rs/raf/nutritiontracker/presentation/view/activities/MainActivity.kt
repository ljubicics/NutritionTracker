package rs.raf.nutritiontracker.presentation.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.ActivityMainBinding
import rs.raf.nutritiontracker.presentation.contract.CategoryContract
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.contract.MealsForCategoryContract
import rs.raf.nutritiontracker.presentation.viewmodel.CategoryViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealsForCategoryViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val categoryViewModel: CategoryContract.ViewModel by viewModel<CategoryViewModel>()
    private val mealViewModel: MealContract.ViewModel by viewModel<MealViewModel>()
    private val mealsForCategoryViewModel: MealsForCategoryContract.ViewModel by viewModel<MealsForCategoryViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setContentView(R.layout.fragment_categories)
        init()
    }

    private fun init() {
        initUi()
        categoryViewModel.fetchAllCategories()
//        mealViewModel.fetchAllMeals()
        mealsForCategoryViewModel.fetchAllMealsForCategory()
    }

    private fun initUi() {
//        println("USOOOOOO")
//        binding.viewPager.adapter =
//            MainPagerAdapter(
//                supportFragmentManager,
//                this
//            )
//        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}