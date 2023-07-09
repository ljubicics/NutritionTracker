package rs.raf.nutritiontracker.presentation.view.activities

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.models.User
import rs.raf.nutritiontracker.data.models.entities.UserEntity
import rs.raf.nutritiontracker.databinding.ActivityMainFragmentBinding
import rs.raf.nutritiontracker.presentation.contract.CategoryContract
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.contract.MealsForAreaContract
import rs.raf.nutritiontracker.presentation.contract.MealsForCategoryContract
import rs.raf.nutritiontracker.presentation.contract.UserContract
import rs.raf.nutritiontracker.presentation.view.fragments.LoginFragment
import rs.raf.nutritiontracker.presentation.view.fragments.MainFragment
import rs.raf.nutritiontracker.presentation.viewmodel.CategoryViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.FilterMealsByAreaViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealsForCategoryViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.UserViewModel
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainFragmentBinding
    private val categoryViewModel: CategoryContract.ViewModel by viewModel<CategoryViewModel>()
    private val mealViewModel: MealContract.ViewModel by viewModel<MealViewModel>()
    private val userViewModel: UserContract.ViewModel by viewModel<UserViewModel>()
    private val mealsForCategoryViewModel: MealsForCategoryContract.ViewModel by viewModel<MealsForCategoryViewModel>()
    private val filterMealsByAreaViewModel: MealsForAreaContract.ViewModel by viewModel<FilterMealsByAreaViewModel>()
    private val sharedPreferences: SharedPreferences by inject()
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen()

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setContentView(R.layout.fragment_categories)
        setContentView(R.layout.activity_main_fragment)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
        categoryViewModel.fetchAllCategories()
        mealsForCategoryViewModel.fetchAllMealsForCategory()
        filterMealsByAreaViewModel.fetchAllAreas()
//        mealViewModel.fetchAllMealsForCategory("Chicken")
//        mealViewModel.fetchAllMeals("Chicken")
    }

    private fun initUi() {
//        println("USOOOOOO")
//        binding.viewPager.adapter =
//            MainPagerAdapter(
//                supportFragmentManager,
//                this
//            )
//        binding.tabLayout.setupWithViewPager(binding.viewPager)

        val user = UserEntity(
            username = "strahinja",
            password = "strahinja",
            email = "strahinja.ljubicic@gmail.com"
        )
        userViewModel.insertUser(user)
    }

    private fun initObservers() {
//        categoryViewModel.categoriesState.observe(this, Observer {
//            Timber.e(it.toString())
//            renderState(it)
//        })
    }

    override fun onResume() {
        super.onResume()
    }

    private fun splashScreen() {
        val splashScreen : SplashScreen = installSplashScreen()
        splashScreen.setKeepVisibleCondition{
            try{
                val userString = sharedPreferences.getString("USER", null)
                val user = userString?.let { moshi.adapter(User::class.java).fromJson(it) }
                if (user != null) {
                    userViewModel.getUserByUsername(user.username)
                }
//                val userString : String? = null
                if (userString != null) {
                    // Ukoliko user postoji, postavite fragment za rad sa aplikacijom
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.mainFragmentFcv, MainFragment())
                    transaction.commit()
                } else {
                    // Ukoliko user ne postoji, postavite LoginFragment
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.mainFragmentFcv, LoginFragment())
                    transaction.commit()
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
            false
        }
    }

//    private fun renderState(state: CategoriesState) {
//        when (state) {
//            is CategoriesState.Success -> {
//                showLoadingState(false)
//                adapter.submitList(state.categories)
//            }
//            is CategoriesState.Error -> {
//                showLoadingState(false)
//                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
//            }
//            is CategoriesState.DataFetched -> {
//                showLoadingState(false)
//                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
//            }
//            is CategoriesState.Loading -> {
//                showLoadingState(true)
//            }
//        }
//    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}