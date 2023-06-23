package rs.raf.nutritiontracker.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rs.raf.nutritiontracker.data.models.AllMealsForCategoryResponse
import rs.raf.nutritiontracker.data.models.FullMealForIdResponse
import rs.raf.nutritiontracker.data.models.OneMealForCategoryResponse

interface MealService {

    @GET("filter.php")
    fun getAllMealsForCategory(@Query("c") category: String, @Query("limit") limit: Int = 1000): Observable<AllMealsForCategoryResponse>

    @GET("lookup.php?i={id}")
    fun getMealForId(@Path("id") id: String): Observable<FullMealForIdResponse>
}