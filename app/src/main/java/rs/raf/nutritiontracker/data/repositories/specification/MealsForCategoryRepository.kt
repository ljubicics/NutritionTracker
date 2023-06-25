package rs.raf.nutritiontracker.data.repositories.specification

import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.Resource

interface MealsForCategoryRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<MealForCategory>>
    fun getAllByCategory(category: String): Observable<List<MealForCategory>>

    fun getAllMealsByName(mealName: String): Observable<List<MealForCategory>>
}