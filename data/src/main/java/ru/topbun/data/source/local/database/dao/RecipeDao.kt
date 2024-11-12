package ru.topbun.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.topbun.data.source.local.database.dbo.RecipeDBO

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeDBO)

    @Query("DELETE FROM recipes WHERE id=:id")
    suspend fun deleteRecipe(id: Int)

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :q || '%' ORDER BY id LIMIT :limit OFFSET :offset")
    suspend fun getRecipeList(q: String, offset: Int, limit: Int): List<RecipeDBO>

    @Query("SELECT * FROM recipes WHERE id=:id LIMIT 1")
    suspend fun getRecipeWithId(id: Int): RecipeDBO

    @Query("SELECT EXISTS(SELECT * FROM recipes WHERE id=:id)")
    suspend fun isExists(id: Int): Boolean
}