package rs.raf.nutritiontracker.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rs.raf.nutritiontracker.data.models.CategoryResponse
import rs.raf.nutritiontracker.data.models.FullMealForIdResponse
import rs.raf.nutritiontracker.data.models.MealForCategoryResponse

interface MealService {

    @GET("filter.php?c={category}")
    fun getAllMealsForCategory(@Path("category") category: String, @Query("limit") limit: Int = 1000): Observable<List<MealForCategoryResponse>>

    @GET("lookup.php?i={id}")
    fun getMealForId(@Path("id") id: String): Observable<FullMealForIdResponse>
}