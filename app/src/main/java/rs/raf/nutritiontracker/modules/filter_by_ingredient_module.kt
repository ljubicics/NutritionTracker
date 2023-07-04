package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.repositories.implementation.MealsForIngredientRepositoryImpl
import rs.raf.nutritiontracker.data.repositories.specification.MealsForIngredientRepository
import rs.raf.nutritiontracker.presentation.viewmodel.FilterMealsByCatViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.FilterMealsByIngredientViewModel

val filterMealsByIngredientViewModel = module {

    viewModel { FilterMealsByIngredientViewModel(mealsForIngredientRepository = get()) }

    single<MealsForIngredientRepository> { MealsForIngredientRepositoryImpl( remoteDataSource = get())}
}