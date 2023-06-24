package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasources.local.MealDataBase
import rs.raf.nutritiontracker.data.datasources.remote.CategoryService
import rs.raf.nutritiontracker.data.repositories.specification.CategoryRepository
import rs.raf.nutritiontracker.data.repositories.implementation.CategoryRepositoryImpl
import rs.raf.nutritiontracker.presentation.viewmodel.CategoryViewModel

val categoryModule = module {

    viewModel { CategoryViewModel(categoryRepository = get()) }

    single<CategoryRepository> { CategoryRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<MealDataBase>().getCategoryDao() }

    single<CategoryService> { create(retrofit = get()) }

}