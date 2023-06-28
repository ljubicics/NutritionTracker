package rs.raf.nutritiontracker.data.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class OneAreaResponse(
    val strArea: String
) {
}