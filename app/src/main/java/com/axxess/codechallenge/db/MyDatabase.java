package com.axxess.codechallenge.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.axxess.codechallenge.db.dao.CommentsDao;
import com.axxess.codechallenge.db.model.Comments;


@Database(entities = {Comments.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract CommentsDao commentsDao();
}