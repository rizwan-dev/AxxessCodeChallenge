package com.axxess.codechallenge.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.axxess.codechallenge.db.model.Comments

@Dao
interface CommentsDao {
    @Query("SELECT * FROM comments")
    suspend fun getAllComments(): List<Comments>

    @Query("SELECT * FROM comments WHERE id LIKE :id LIMIT 1")
    suspend fun findById(id: String): Comments?

    @Insert
    suspend fun insert(comments: Comments)

    @Update
    suspend fun update(comments: Comments)
}