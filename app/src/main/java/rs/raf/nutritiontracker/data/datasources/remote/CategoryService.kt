package rs.raf.nutritiontracker.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.nutritiontracker.data.models.CategoryResponse

interface CategoryService {
    @GET("categories.php")
    fun getAllCategories(@Query("limit") limit: Int = 1000): Observable<List<CategoryResponse>>
}