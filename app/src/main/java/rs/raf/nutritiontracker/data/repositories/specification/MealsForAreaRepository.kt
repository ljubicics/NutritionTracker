package rs.raf.nutritiontracker.data.repositories.specification

import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.models.response.OneMealForCategoryResponse

interface MealsForAreaRepository {

    fun fetchAll(): Observable<Resource<Unit>>
    fun fetchAllByArea(area: String): Observable<Resource<List<OneMealForCategoryResponse>>>
}