package ru.topbun.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.topbun.data.source.local.database.converter.CategoryConverter
import ru.topbun.data.source.local.database.converter.IngredientsConverter
import ru.topbun.data.source.local.database.converter.StepConverter
import ru.topbun.data.source.local.database.dao.RecipeDao
import ru.topbun.data.source.local.database.dbo.CategoryDBO
import ru.topbun.data.source.local.database.dbo.IngredientsDBO
import ru.topbun.data.source.local.database.dbo.RecipeDBO
import ru.topbun.data.source.local.database.dbo.StepDBO

@Database(
    entities = [
        CategoryDBO::class,
        IngredientsDBO::class,
        RecipeDBO::class,
        StepDBO::class,
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(
    CategoryConverter::class,
    IngredientsConverter::class,
    StepConverter::class
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object{

        private const val DB_NAME = "tasty.db"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(Unit){
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context, AppDatabase::class.java, DB_NAME).build()

    }

}