package rs.raf.nutritiontracker.data.repositories.implementation

import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasources.remote.CategoryService
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.models.response.OneMealForCategoryResponse
import rs.raf.nutritiontracker.data.repositories.specification.MealsForAreaRepository
import javax.sql.CommonDataSource

class MealsForAreaRepositoryImpl(
    private val remoteDataSource: MealService
) : MealsForAreaRepository {
    override fun fetchAll(): Observable<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun fetchAllByArea(area: String): Observable<Resource<List<OneMealForCategoryResponse>>> {
        val meals = remoteDataSource
            .fetchAllMealsByArea(area)
            .map { it ->
                it.meals.map {
                    it
                }
            }

        return meals.map {
            Resource.Success(it)
        }
    }
}