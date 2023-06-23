package rs.raf.nutritiontracker.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllMealsForCategoryResponse(
    val meals : List<OneMealForCategoryResponse>
) {

}