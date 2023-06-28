package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.Area
import rs.raf.nutritiontracker.data.models.Category

sealed class AreaState {
    object Loading: AreaState()
    object DataFetched: AreaState()
    data class Success(val areas: List<Area>): AreaState()
    data class Error(val message: String): AreaState()
}