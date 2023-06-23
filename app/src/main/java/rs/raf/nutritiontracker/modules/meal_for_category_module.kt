package rs.raf.nutritiontracker.modules

import io.reactivex.schedulers.Schedulers.single
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasources.local.MealDataBase
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.repositories.MealRepositoryImpl
import rs.raf.nutritiontracker.data.repositories.MealsForCategoryRepository
import rs.raf.nutritiontracker.data.repositories.MealsForCategoryRepositoryImpl
import rs.raf.nutritiontracker.presentation.viewmodel.MealsForCategoryViewModel

val mealsForCategoryModule = module {

    viewModel { MealsForCategoryViewModel(mealsForCategoryRepository = get()) }

    single<MealsForCategoryRepository> { MealsForCategoryRepositoryImpl( localMealsForCategoryDataSource = get(), remoteDataSource = get(), localCategoryDataSource = get(), categoryRepository = get()) }

    single { get<MealDataBase>().getMealsForCategoryDao() }

}