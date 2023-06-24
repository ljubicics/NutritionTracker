package rs.raf.nutritiontracker.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.nutritiontracker.data.models.entities.CategoryEntity
import rs.raf.nutritiontracker.data.models.entities.MealEntity
import rs.raf.nutritiontracker.data.models.entities.MealForCategoryEntity

@Database(
    entities = [MealEntity::class, CategoryEntity::class, MealForCategoryEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class MealDataBase : RoomDatabase() {
    abstract fun getMealDao(): MealDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getMealsForCategoryDao(): MealsForCategoryDao
}