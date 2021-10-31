package com.example.drawer.ui.paging;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface StudentDao {
    @Insert
    void insertStudents(Student... students);

    @Query("DELETE FROM student_table")
    void deleteAllStudents();

    @Query("SELECT * FROM student_table ORDER BY id")
    PagingSource<Integer, Student> getAllStudents();
}
