package com.example.popularmoviesapp.popularmoviesapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.popularmoviesapp.popularmoviesapp.R;
import com.example.popularmoviesapp.popularmoviesapp.models.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract com.example.popularmoviesapp.popularmoviesapp.data.database.MovieDao movieDao();

    private static AppDatabase instance;
    private static final Object LOCK = new Object();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, context.getString(R.string.db_name))
                        .build();
            }
        }
        return instance;
    }
}
