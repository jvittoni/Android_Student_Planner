package com.example.studentplanner03.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentplanner03.R;
import com.example.studentplanner03.database.Repository;
import com.example.studentplanner03.entities.Assignment;
import com.example.studentplanner03.entities.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        System.out.println(getIntent().getStringExtra("test"));

        // Add classes floating action button
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, CourseDetails.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        repository = new Repository(getApplication());
        List<Course> allCourses = repository.getmAllCourses();
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);

    }

    // Menu item to add sample code
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Course> allCourses = repository.getmAllCourses();
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mySampleCode) {
//            Toast.makeText(CourseList.this, "put in sample data", Toast.LENGTH_LONG).show();
            repository = new Repository(getApplication());

            Course course = new Course(0, "Course1", "Instructor1", "03/01/25", "06/01/25", "This is course1");
            repository.insert(course);

            course = new Course(0, "Course2", "Instructor2", "03/01/25", "06/01/25", "This is course2");
            repository.insert(course);

            Assignment assignment = new Assignment(0, "Homework1", "03/05/25", "This is homework1", 1);
            repository.insert(assignment);

            assignment = new Assignment(0, "Homework2", "03/05/25", "This is homework2", 1);
            repository.insert(assignment);

            return true;
        }

        if (item.getItemId() == R.id.myGenerateReport) {
            Intent intent = new Intent(this, ReportGenerator.class);
            startActivity(intent);
            return true;
        }

        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        //return true;
        return super.onOptionsItemSelected(item);
    }

}