package rs.raf.nutritiontracker.data.models.response

import com.squareup.moshi.JsonClass

// Vraca stvari koje se dobiju kada se pozove List All Meal Categories
@JsonClass(generateAdapter = true)
data class OneCategoryResponse(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String,
)