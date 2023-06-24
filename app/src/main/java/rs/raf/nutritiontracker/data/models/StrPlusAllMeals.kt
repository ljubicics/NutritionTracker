package rs.raf.nutritiontracker.data.models

import rs.raf.nutritiontracker.data.models.response.AllMealsForCategoryResponse

data class StrPlusAllMeals(
    val allMealsForCategoryResponse: AllMealsForCategoryResponse,
    val strCategory: String
) {

}