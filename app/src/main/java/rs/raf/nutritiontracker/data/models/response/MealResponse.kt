package rs.raf.nutritiontracker.data.models.response

import com.squareup.moshi.JsonClass
import rs.raf.nutritiontracker.data.models.response.OneMealResponse

@JsonClass(generateAdapter = true)
data class MealResponse(
    val meals: List<OneMealResponse>
)
