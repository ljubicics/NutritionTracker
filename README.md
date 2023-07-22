# NutritionTracker
Nutrition Tracker is an Android application written in Kotlin using Android Studio and it was the second project for "Mobile Development" course at Faculty of Computing. 
The main purpose of the application is to help users quickly and easily find meals information about their preparating and first of all help them save them for later user. 
In addition, application has an option for sending email with weekly plan, also user can see statistcis how many meals she/he added in the past 7 days.

# How to use project
Login - To login you need to enter strahinja for pin and username. User is stored in database.
Main Screen - On the main screen there are 5 bottom navigation icons: categories, filter, meals, plan and profile. You can enter either of them just by clicking on the icon in the bottom navigation.
Categories Screen - List of all categories. By clicking on a category, list of meals for given category loads from MealDB API. Meal can be saved by clickinh on iy
Filter Screen - Used for filtering meals from API by category, area and ingredient
Meals Screen - Listing all meals from local database using RecyclerView.
Plan Screen - Shows number of meals chosen for each day, by clicking on the number you can see which meals were selected, and by writing email in edittext you can send weekly plan on email using implicit intent.
Profile Screen - Shows number of meals saved by user in past 7 days. By clicking on saved meals there could be found all the the meals that were saved by user

#Technologies used in the project:
- Koin framework for dependency injection
    - Retrofit library for communicating with the server and REST convention
    - RxJava library for easier work with threads
    - Room library for working with database (ORM)
    - Moshi library for working with json
