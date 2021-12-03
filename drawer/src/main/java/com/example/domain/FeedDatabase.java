package com.example.domain;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import org.jetbrains.annotations.NotNull;

@Database(entities = {FeedEntry.class}, version = 2, exportSchema = false)
public abstract class FeedDatabase extends RoomDatabase {
    private static FeedDatabase instance;

    public static synchronized FeedDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, FeedDatabase.class, "user.db")
                    .addMigrations(MIGRATION1TO2)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract FeedDao getFeedDao();

    static final Migration MIGRATION1TO2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull @NotNull SupportSQLiteDatabase database) {

        }
    };
}
