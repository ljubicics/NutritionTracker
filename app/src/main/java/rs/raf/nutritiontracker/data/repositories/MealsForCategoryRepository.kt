package rs.raf.nutritiontracker.data.repositories

import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.Resource

interface MealsForCategoryRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<MealForCategory>>
}