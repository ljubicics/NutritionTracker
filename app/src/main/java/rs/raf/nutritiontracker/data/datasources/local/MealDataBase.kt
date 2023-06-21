package rs.raf.nutritiontracker.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.nutritiontracker.data.models.CategoryEntity
import rs.raf.nutritiontracker.data.models.MealEntity

@Database(
    entities = [MealEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class MealDataBase : RoomDatabase() {
    abstract fun getMealDao(): MealDao
    abstract fun getCategoryDao(): CategoryDao
}