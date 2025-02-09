package com.example.studentplanner03.database;

import android.accessibilityservice.FingerprintGestureController;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.studentplanner03.dao.AssignmentDAO;
import com.example.studentplanner03.dao.CourseDAO;
import com.example.studentplanner03.entities.Assignment;
import com.example.studentplanner03.entities.Course;

@Database(entities = {Course.class, Assignment.class}, version = 1,exportSchema = false)
public abstract class StudentPlannerDatabaseBuilder extends RoomDatabase {
    public abstract CourseDAO courseDAO();
    public abstract AssignmentDAO assignmentDAO();
    private static volatile StudentPlannerDatabaseBuilder INSTANCE;

    static StudentPlannerDatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (StudentPlannerDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentPlannerDatabaseBuilder.class, "MyStudentPlannerDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
