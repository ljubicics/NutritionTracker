package rs.raf.nutritiontracker.data.models.response

import com.squareup.moshi.JsonClass
import rs.raf.nutritiontracker.data.models.response.OneMealForCategoryResponse

@JsonClass(generateAdapter = true)
data class AllMealsForCategoryResponse(
    val meals : List<OneMealForCategoryResponse>
) {

}