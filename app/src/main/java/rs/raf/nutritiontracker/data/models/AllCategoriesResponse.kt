package rs.raf.nutritiontracker.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllCategoriesResponse(
    val allCategories : List<CategoryResponse>
) {
}