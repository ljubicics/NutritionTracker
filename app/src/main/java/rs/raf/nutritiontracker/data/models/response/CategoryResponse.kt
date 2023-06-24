package rs.raf.nutritiontracker.data.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    val categories: List<OneCategoryResponse>
) {
}