package com.example.oshodimobile.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.oshodimobile.Entities.Lvl;
import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters({Converter.class})
@Database(entities = {User.class,Match.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract MatchDAO matchDAO();
    public abstract UserMatchDAO userMatchDAO();
    ///Singleton instance to retrieve when the db is needed
    private static volatile AppDatabase INSTANCE;

    static final ExecutorService executor = Executors.newFixedThreadPool(4);

    static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            //The synchronized is to prevent multiple instances being created.
            synchronized (AppDatabase.class) {
                //If the db has not yet been created, the builder creates it.
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "tennis_database").build();
                }
            }
        }
        return INSTANCE;
    }
}