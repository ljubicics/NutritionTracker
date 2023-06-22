package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.presentation.view.states.AddCategoryState
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState

interface CategoryContract {
    interface ViewModel {
        val categoriesState: LiveData<CategoriesState>
        val addCategoryDone: LiveData<AddCategoryState>

        fun fetchAllCategories()
        fun getAllCategories()
        fun getCategoriesByName(name: String)
        fun addCateogry(meal: Meal)
    }
}