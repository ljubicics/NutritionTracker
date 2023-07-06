package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasources.local.MealDataBase
import rs.raf.nutritiontracker.data.repositories.implementation.AreaRepositoryImpl
import rs.raf.nutritiontracker.data.repositories.implementation.MealsForAreaRepositoryImpl
import rs.raf.nutritiontracker.data.repositories.implementation.MealsForCategoryRepositoryImpl
import rs.raf.nutritiontracker.data.repositories.specification.AreaRepository
import rs.raf.nutritiontracker.data.repositories.specification.MealsForAreaRepository
import rs.raf.nutritiontracker.data.repositories.specification.MealsForCategoryRepository
import rs.raf.nutritiontracker.presentation.viewmodel.FilterMealsByAreaViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.FilterMealsByCatViewModel

val filterMealsByAreaModule = module {

    viewModel { FilterMealsByAreaViewModel(mealsForAreaRepository = get(), areaRepository = get()) }

    single<MealsForAreaRepository> { MealsForAreaRepositoryImpl( remoteDataSource = get()) }

    single<AreaRepository> { AreaRepositoryImpl(remoteDataSource = get(), localDataSource = get()) }

    single { get<MealDataBase>().getAreaDao() }
}