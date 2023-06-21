package rs.raf.nutritiontracker.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasources.local.MealDao
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.Resource

class MealRepositoryImpl(
    private val localDataSource: MealDao,
    private val remoteDataSource: MealService
    ) : MealRepository {
    override fun fetchAll(): Observable<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Observable<List<Meal>> {
        TODO("Not yet implemented")
    }

    override fun getAllByName(name: String): Observable<List<Meal>> {
        TODO("Not yet implemented")
    }

    override fun insert(meal: Meal): Completable {
        TODO("Not yet implemented")
    }
}