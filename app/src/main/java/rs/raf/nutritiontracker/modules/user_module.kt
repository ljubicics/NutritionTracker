package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasources.local.MealDataBase
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.repositories.implementation.MealRepositoryImpl
import rs.raf.nutritiontracker.data.repositories.implementation.UserRepositoryImpl
import rs.raf.nutritiontracker.data.repositories.specification.MealRepository
import rs.raf.nutritiontracker.data.repositories.specification.UserRepository
import rs.raf.nutritiontracker.presentation.viewmodel.MealViewModel
import rs.raf.nutritiontracker.presentation.viewmodel.UserViewModel

val userModule = module {

    viewModel { UserViewModel(userRepository = get()) }

    single<UserRepository> { UserRepositoryImpl(localDataSource = get()) }

    single { get<MealDataBase>().getUserDao() }

}