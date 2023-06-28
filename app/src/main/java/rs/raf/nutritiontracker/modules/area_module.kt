package rs.raf.nutritiontracker.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasources.local.MealDataBase
import rs.raf.nutritiontracker.data.repositories.implementation.AreaRepositoryImpl
import rs.raf.nutritiontracker.data.repositories.specification.AreaRepository
import rs.raf.nutritiontracker.data.repositories.specification.CategoryRepository
import rs.raf.nutritiontracker.presentation.viewmodel.AreaViewModel

val areaModule = module {

    viewModel { AreaViewModel(areaRepository = get()) }

    single<AreaRepository> { AreaRepositoryImpl(remoteDataSource = get(), localDataSource = get()) }

    single { get<MealDataBase>().getAreaDao() }

}