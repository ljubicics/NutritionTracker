package rs.raf.nutritiontracker.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rs.raf.nutritiontracker.modules.categoryModule
import rs.raf.nutritiontracker.modules.coreModule
import rs.raf.nutritiontracker.modules.filterMealsByAreaModule
import rs.raf.nutritiontracker.modules.filterMealsByCatModule
import rs.raf.nutritiontracker.modules.filterMealsByIngredientViewModel
import rs.raf.nutritiontracker.modules.listmealsModule
import rs.raf.nutritiontracker.modules.mealModule
import rs.raf.nutritiontracker.modules.mealsForCategoryModule
import rs.raf.nutritiontracker.modules.mealsLoadingPageModule
import rs.raf.nutritiontracker.modules.pickMealsModule
import rs.raf.nutritiontracker.modules.userModule
import timber.log.Timber

class NutritionTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        val modules = listOf(
            coreModule,
            mealModule,
            categoryModule,
            mealsForCategoryModule,
            userModule,
            filterMealsByCatModule,
            filterMealsByAreaModule,
            filterMealsByIngredientViewModel,
            mealsLoadingPageModule,
            listmealsModule,
            pickMealsModule
        )
        startKoin {
            androidLogger(Level.ERROR)
            // Use application context
            androidContext(this@NutritionTrackerApplication)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }
    }
}