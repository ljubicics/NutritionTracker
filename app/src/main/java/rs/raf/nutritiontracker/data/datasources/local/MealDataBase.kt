package rs.raf.nutritiontracker.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.nutritiontracker.data.models.entities.AreaEntity
import rs.raf.nutritiontracker.data.models.entities.CategoryEntity
import rs.raf.nutritiontracker.data.models.entities.MealEntity
import rs.raf.nutritiontracker.data.models.entities.MealForCategoryEntity
import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity
import rs.raf.nutritiontracker.data.models.entities.UserEntity

@Database(
    entities = [MealEntity::class, CategoryEntity::class,
        MealForCategoryEntity::class, UserEntity::class,
               AreaEntity::class, MealSavedEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class MealDataBase : RoomDatabase() {
    abstract fun getMealDao(): MealDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getMealsForCategoryDao(): MealsForCategoryDao
    abstract fun getUserDao(): UserDao
    abstract fun getAreaDao(): AreaDao
    abstract fun getMealSavedDao(): MealSavedDao
}