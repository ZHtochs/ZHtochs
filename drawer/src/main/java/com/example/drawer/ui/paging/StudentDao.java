package com.example.drawer.ui.paging;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insertStudents(Student... students);

    @Query("DELETE FROM student_table")
    void deleteAllStudents();

    @Query("SELECT * FROM student_table ORDER BY id")
    PagingSource<Integer, Student> getAllStudents();

    @Query("SELECT * FROM student_table WHERE classId =:classId AND id>:start ORDER BY id LIMIT :offset")
    List<Student> getSameClassStudent(int classId, int start, int offset);
}
