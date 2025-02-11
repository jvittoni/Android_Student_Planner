package com.example.studentplanner03.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentplanner03.R;
import com.example.studentplanner03.database.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CourseDetails extends AppCompatActivity {

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
}