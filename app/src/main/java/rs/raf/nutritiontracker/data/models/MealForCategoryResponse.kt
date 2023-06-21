package rs.raf.nutritiontracker.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealForCategoryResponse(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String
) {
//    "strMeal": "Baked salmon with fennel & tomatoes",
//    "strMealThumb": "https://www.themealdb.com/images/media/meals/1548772327.jpg",
//    "idMeal": "52959"
}