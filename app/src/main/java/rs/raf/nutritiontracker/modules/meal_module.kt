package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasources.local.MealDataBase
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.repositories.implementation.MealRepositoryImpl
import rs.raf.nutritiontracker.data.repositories.specification.MealRepository
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel

val mealModule = module {

    viewModel { MealViewModel(mealRepository = get()) }

    single<MealRepository> { MealRepositoryImpl(localMealDataSource = get(),localMealSavedDataSource = get(), localCategoryDataSource = get(), remoteDataSource = get(), mealsForCategoryRepository = get()) }

    single { get<MealDataBase>().getMealDao() }
    single { get<MealDataBase>().getMealSavedDao() }

    single<MealService> { create(retrofit = get()) }

}