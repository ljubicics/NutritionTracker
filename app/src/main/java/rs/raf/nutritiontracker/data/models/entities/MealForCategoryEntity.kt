package rs.raf.nutritiontracker.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealsForCategory")
class MealForCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val mealForCatId: Long,
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String,
    val strCategory: String
) {
}