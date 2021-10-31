package com.example.drawer.ui.paging;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentsDatabase extends RoomDatabase {
    private static StudentsDatabase instance;

    public static synchronized StudentsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, StudentsDatabase.class, "students_database")
                    .build();
        }
        return instance;
    }

    public abstract StudentDao getStudentDao();
}
