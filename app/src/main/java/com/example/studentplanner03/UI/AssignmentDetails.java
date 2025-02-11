package com.example.studentplanner03.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentplanner03.R;
import com.example.studentplanner03.database.Repository;
import com.example.studentplanner03.entities.Assignment;
import com.example.studentplanner03.entities.Course;

import java.util.ArrayList;

public class AssignmentDetails extends AppCompatActivity {

    String assignmentName;
    String assignmentDueDate;
    String assignmentDescription;
    int assignmentID;
    int courseID;

    EditText editAssignmentName;
    TextView editAssignmentDueDate;
    EditText editAssignmentDescription;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assignment_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new Repository(getApplication());

        // Course ID
        courseID = getIntent().getIntExtra("crseID", -1);

        // Assignment ID
        assignmentID = getIntent().getIntExtra("assignmID", -1);

        // Assignment Name
        editAssignmentName = findViewById(R.id.assignmentTitle);
        assignmentName = getIntent().getStringExtra("assignmName");
        editAssignmentName.setText(assignmentName);

        // Assignment Due Date
        editAssignmentDueDate = findViewById(R.id.assignmentDueDate);
        assignmentDueDate = getIntent().getStringExtra("assignmDD");
        editAssignmentDueDate.setText(assignmentDueDate);

        // Assignment Description
        editAssignmentDescription = findViewById(R.id.assignmentDescription);
        assignmentDescription = getIntent().getStringExtra("assignmDesc");
        editAssignmentDescription.setText(assignmentDescription);

        ArrayList<Course> courseArrayList = new ArrayList<>();
        courseArrayList.addAll(repository.getmAllCourses());
        ArrayList<Integer> courseIdList = new ArrayList<>();
        for (Course course : courseArrayList) {
            courseIdList.add(course.getCourseID());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assignmentdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.saveAssignment) {
            Assignment assignment;
            if (assignmentID == -1) {
                if (repository.getmAllAssignments().size() == 0)
                    assignmentID = 1;
                else
                    assignmentID = repository.getmAllAssignments().get(repository.getmAllAssignments().size() - 1).getAssignmentID() + 1;
                assignment = new Assignment(assignmentID, editAssignmentName.getText().toString(), editAssignmentDueDate.getText().toString(), editAssignmentDescription.getText().toString(), courseID);
                repository.insert(assignment);
                this.finish();
            } else {
                assignment = new Assignment(assignmentID, editAssignmentName.getText().toString(), editAssignmentDueDate.getText().toString(), editAssignmentDescription.getText().toString(), courseID);
                repository.update(assignment);
                this.finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}