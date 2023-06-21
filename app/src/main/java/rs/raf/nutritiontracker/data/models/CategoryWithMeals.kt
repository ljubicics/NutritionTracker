package rs.raf.nutritiontracker.data.models

import androidx.room.Embedded
import androidx.room.Relation

// Ovo je tabela koja definise one-to-many vezu izmedju kategorije i jela
data class CategoryWithMeals(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "idCategory",
        entityColumn = "idCategory"
    )
    val meals: List<MealEntity>
) {
}