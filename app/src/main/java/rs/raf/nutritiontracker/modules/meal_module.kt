package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasources.local.MealDataBase
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.repositories.MealRepository
import rs.raf.nutritiontracker.data.repositories.MealRepositoryImpl
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel

val mealModule = module {

    viewModel { MealViewModel(mealRepository = get(), categoryRepository = get()) }

    single<MealRepository> { MealRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<MealDataBase>().getMealDao() }

    single<MealService> { create(retrofit = get()) }

}