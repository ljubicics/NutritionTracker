package rs.raf.nutritiontracker.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "areas")
class AreaEntity(
    @PrimaryKey
    val strArea: String
) {
}