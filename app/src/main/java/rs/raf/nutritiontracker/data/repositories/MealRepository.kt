package rs.raf.nutritiontracker.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.Resource

interface MealRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun fetchMealsForCategory(category: String): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Meal>>
    fun getAllByName(name: String): Observable<List<Meal>>
    fun insert(meal: Meal): Completable
}