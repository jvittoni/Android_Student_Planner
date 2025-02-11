package com.example.studentplanner03.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentplanner03.R;
import com.example.studentplanner03.database.Repository;
import com.example.studentplanner03.entities.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseDetails extends AppCompatActivity {

    String courseName;
    String courseInstructor;
    String courseStartDate;
    String courseEndDate;
    String courseDescription;
    int courseID;

    EditText editCourseName;
    EditText editCourseInstructor;
    TextView editCourseStartDate;
    TextView editCourseEndDate;
    EditText editCourseDescription;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Course ID
        courseID = getIntent().getIntExtra("crseID", -1);

        // Course Name
        editCourseName = findViewById(R.id.courseTitleText);
        courseName = getIntent().getStringExtra("crseName");
        editCourseName.setText(courseName);

        // Course Instructor
        editCourseInstructor = findViewById(R.id.courseInstructorText);
        courseInstructor = getIntent().getStringExtra("crseInstruct");
        editCourseInstructor.setText(courseInstructor);

        // Course Start Date
        editCourseStartDate = findViewById(R.id.selectStart);
        courseStartDate = getIntent().getStringExtra("crseStartDate");
        editCourseStartDate.setText(courseStartDate);

        // Course End Date
        editCourseEndDate = findViewById(R.id.selectEnd);
        courseEndDate = getIntent().getStringExtra("crseEndDate");
        editCourseEndDate.setText(courseStartDate);

        // Course Description
        editCourseDescription = findViewById(R.id.courseDescriptionText);
        courseDescription = getIntent().getStringExtra("crseDesc");
        editCourseDescription.setText(courseDescription);


        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, AssignmentDetails.class);
                startActivity(intent);
            }
        });

        // show associated assignments
        RecyclerView recyclerView = findViewById(R.id.assignmentRecyclerView);
        repository = new Repository(getApplication());
        final AssignmentAdapter assignmentAdapter = new AssignmentAdapter(this);
        recyclerView.setAdapter(assignmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignmentAdapter.setAssignments(repository.getmAllAssignments());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveCourse) {
            Course course;
            if (courseID == -1) {
                if (repository.getmAllCourses().size() == 0) courseID = 1;
                else courseID = repository.getmAllCourses().get(repository.getmAllCourses().size() - 1).getCourseID() + 1;
                course = new Course(courseID, editCourseName.getText().toString(), editCourseInstructor.getText().toString(), editCourseStartDate.getText().toString(), editCourseEndDate.getText().toString(), editCourseDescription.getText().toString());
                repository.insert(course);
                this.finish();
            }
            else {
                course = new Course(courseID, editCourseName.getText().toString(), editCourseInstructor.getText().toString(), editCourseStartDate.getText().toString(), editCourseEndDate.getText().toString(), editCourseDescription.getText().toString());
                repository.update(course);
                this.finish();
            }
        }
        return true;
    }


}