package rs.raf.nutritiontracker.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.nutritiontracker.data.models.response.AllMealsForCategoryResponse
import rs.raf.nutritiontracker.data.models.response.MealResponse

interface MealService {

    @GET("search.php")
    fun fetchByMealName(@Query("s") name: String): Observable<MealResponse>
    @GET("filter.php")
    fun fetchAllMealsByArea(@Query("a") area: String): Observable<AllMealsForCategoryResponse>
    @GET("filter.php")
    fun fetchAllMealsByIngredient(@Query("i") ingredient: String): Observable<AllMealsForCategoryResponse>
    @GET("lookup.php")
    fun fetchAllMealsById(@Query("i") id: String): Observable<MealResponse>
    @GET("filter.php")
    fun getAllMealsForCategory(@Query("c") category: String, @Query("limit") limit: Int = 1000): Observable<AllMealsForCategoryResponse>
    @GET("lookup.php")
    fun getMealForId(@Query("i") id: String): Observable<MealResponse>
}