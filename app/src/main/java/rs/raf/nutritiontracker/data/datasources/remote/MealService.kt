package rs.raf.nutritiontracker.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.nutritiontracker.data.models.AllMealsForCategoryResponse
import rs.raf.nutritiontracker.data.models.MealResponse

interface MealService {

    @GET("filter.php")
    fun getAllMealsForCategory(@Query("c") category: String, @Query("limit") limit: Int = 1000): Observable<AllMealsForCategoryResponse>
    @GET("lookup.php")
    fun getMealForId(@Query("i") id: String): Observable<MealResponse>
}