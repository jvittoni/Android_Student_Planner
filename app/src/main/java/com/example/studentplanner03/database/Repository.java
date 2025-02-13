package com.example.studentplanner03.database;

import android.app.Application;

import com.example.studentplanner03.dao.AssignmentDAO;
import com.example.studentplanner03.dao.CourseDAO;
import com.example.studentplanner03.entities.Assignment;
import com.example.studentplanner03.entities.Course;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class Repository {
    private AssignmentDAO mAssignmentDAO;
    private CourseDAO mCourseDAO;

    private List<Course> mAllCourses;
    private List<Assignment> mAllAssignments;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        StudentPlannerDatabaseBuilder db = StudentPlannerDatabaseBuilder.getDatabase(application);
        mAssignmentDAO = db.assignmentDAO();
        mCourseDAO = db.courseDAO();
    }



    // ------------ Report Generator ---------------
    public Course getCourseById(int courseID) {
        AtomicReference<Course> course = new AtomicReference<>();
        databaseExecutor.execute(() -> {
            course.set(mCourseDAO.getCourseById(courseID));
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return course.get();
    }


    // --------------- COURSES ---------------------

    public List<Course> getmAllCourses() {
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllCourses;
    }

    public void insert(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Assignments
    public List<Assignment> getmAllAssignments() {
        databaseExecutor.execute(()->{
            mAllAssignments = mAssignmentDAO.getAllAssignments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllAssignments;
    }

    public List<Assignment> getAssociatedAssignments(int courseID) {
        databaseExecutor.execute(()->{
            mAllAssignments = mAssignmentDAO.getAssociatedAssignments(courseID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllAssignments;
    }


    public void insert(Assignment assignment) {
        databaseExecutor.execute(()->{
            mAssignmentDAO.insert(assignment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assignment assignment) {
        databaseExecutor.execute(()->{
            mAssignmentDAO.update(assignment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assignment assignment) {
        databaseExecutor.execute(()->{
            mAssignmentDAO.delete(assignment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
