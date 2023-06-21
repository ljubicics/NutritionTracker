package rs.raf.nutritiontracker.data.models

import com.squareup.moshi.JsonClass
import rs.raf.nutritiontracker.data.models.MealForCategoryResponse

@JsonClass(generateAdapter = true)
data class AllMealsForCategoryResponse(
    val allMealsForcategory : List<MealForCategoryResponse>
) {

}