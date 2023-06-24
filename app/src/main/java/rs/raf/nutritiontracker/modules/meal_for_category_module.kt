package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasources.local.MealDataBase
import rs.raf.nutritiontracker.data.repositories.specification.MealsForCategoryRepository
import rs.raf.nutritiontracker.data.repositories.implementation.MealsForCategoryRepositoryImpl
import rs.raf.nutritiontracker.presentation.viewmodel.MealsForCategoryViewModel

val mealsForCategoryModule = module {

    viewModel { MealsForCategoryViewModel(mealsForCategoryRepository = get()) }

    single<MealsForCategoryRepository> { MealsForCategoryRepositoryImpl( localMealsForCategoryDataSource = get(), remoteDataSource = get(), localCategoryDataSource = get(), categoryRepository = get()) }

    single { get<MealDataBase>().getMealsForCategoryDao() }

}