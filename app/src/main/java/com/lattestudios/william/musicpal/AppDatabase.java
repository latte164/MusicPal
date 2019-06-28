package com.lattestudios.william.musicpal;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {SongList.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "song_list_db";
    private static AppDatabase instance;

    public abstract SongListDAO getSongListDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

}
