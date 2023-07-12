package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.presentation.viewmodel.PickMealsViewModel

val pickMealsModule = module {
    viewModel { PickMealsViewModel(mealRepository = get(), mealsForCategoryRepository = get()) }

}