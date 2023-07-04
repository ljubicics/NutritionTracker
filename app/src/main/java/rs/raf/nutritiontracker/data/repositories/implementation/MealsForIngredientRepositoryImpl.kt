package rs.raf.nutritiontracker.data.repositories.implementation

import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.models.response.OneMealForCategoryResponse
import rs.raf.nutritiontracker.data.repositories.specification.MealsForIngredientRepository

class MealsForIngredientRepositoryImpl(
    private val remoteDataSource: MealService
) : MealsForIngredientRepository {

    override fun fetchAllByIngredient(ingredient: String): Observable<Resource<List<OneMealForCategoryResponse>>> {
        val meals = remoteDataSource
            .fetchAllMealsByIngredient(ingredient)
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