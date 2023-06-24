package rs.raf.nutritiontracker.data.models

import androidx.room.Embedded
import androidx.room.Relation
import rs.raf.nutritiontracker.data.models.entities.CategoryEntity
import rs.raf.nutritiontracker.data.models.entities.MealEntity

// Ovo je tabela koja definise one-to-many vezu izmedju kategorije i jela
data class CategoryWithMeals(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "strCategory",
        entityColumn = "strCategory"
    )
    val meals: List<MealEntity>
) {
}