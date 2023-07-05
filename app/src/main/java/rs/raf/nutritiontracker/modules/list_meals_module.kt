package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.presentation.viewmodel.ListMealsViewModel

val listmealsModule = module {

    viewModel { ListMealsViewModel(mealsForCategoryRepository = get(), mealsForIngredientRepository = get()) }

}