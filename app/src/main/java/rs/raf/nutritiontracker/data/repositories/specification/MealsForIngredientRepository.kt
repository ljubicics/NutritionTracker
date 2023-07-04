package rs.raf.nutritiontracker.data.repositories.specification

import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.models.response.OneMealForCategoryResponse

interface MealsForIngredientRepository {
    fun fetchAllByIngredient(ingredient: String): Observable<Resource<List<OneMealForCategoryResponse>>>
}