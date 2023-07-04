package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasources.local.MealDataBase
import rs.raf.nutritiontracker.data.repositories.implementation.MealsForCategoryRepositoryImpl
import rs.raf.nutritiontracker.data.repositories.specification.MealsForCategoryRepository
import rs.raf.nutritiontracker.presentation.viewmodel.MealsForCategoryViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.MealsLoadingPageViewModel

val mealsLoadingPageModule = module {

    viewModel { MealsLoadingPageViewModel(mealsForCategoryRepository = get()) }


}