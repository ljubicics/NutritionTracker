package rs.raf.nutritiontracker.data.models

// Model jednog jela za kategoriju
//"strMeal": "Baked salmon with fennel & tomatoes",
//"strMealThumb": "https://www.themealdb.com/images/media/meals/1548772327.jpg",
//"idMeal": "52959"
data class MealForCategory(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String,
    val strCategory: String
) {
}