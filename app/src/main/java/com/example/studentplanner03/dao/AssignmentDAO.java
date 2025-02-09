package com.example.studentplanner03.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentplanner03.entities.Assignment;

import java.util.List;

@Dao
public interface AssignmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assignment assignment);

    @Update
    void update(Assignment assignment);

    @Delete
    void delete(Assignment assignment);

    @Query("SELECT * FROM ASSIGNMENTS ORDER BY assignmentID ASC")
    List<Assignment> getAllAssignments();

    @Query("SELECT * FROM ASSIGNMENTS WHERE courseID=:crse ORDER BY assignmentID ASC")
    List<Assignment> getAssociatedAssignments(int crse);
}
