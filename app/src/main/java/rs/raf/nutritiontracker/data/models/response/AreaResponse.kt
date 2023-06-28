package rs.raf.nutritiontracker.data.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AreaResponse(
    val meals: List<OneAreaResponse>
) {
}