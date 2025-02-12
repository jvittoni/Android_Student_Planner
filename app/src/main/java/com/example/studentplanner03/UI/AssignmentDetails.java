package com.example.studentplanner03.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentplanner03.R;
import com.example.studentplanner03.database.Repository;
import com.example.studentplanner03.entities.Assignment;
import com.example.studentplanner03.entities.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AssignmentDetails extends AppCompatActivity {

    String assignmentName;
    String assignmentDueDate;
    String assignmentDescription;
    int assignmentID;
    int courseID;

    EditText editAssignmentName;
    TextView editAssignmentDueDate;
    EditText editAssignmentDescription;

    DatePickerDialog.OnDateSetListener assignmentDeadline;
    final Calendar myCalendarAssignmentDeadline = Calendar.getInstance();

    Assignment currentAssignment;

    // Notification Alert
    Random rand = new Random();
    int numAlert = rand.nextInt(99999);

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

        // Notification
        numAlert = rand.nextInt(99999);

        ArrayList<Course> courseArrayList = new ArrayList<>();
        courseArrayList.addAll(repository.getmAllCourses());
        ArrayList<Integer> courseIdList = new ArrayList<>();
        for (Course course : courseArrayList) {
            courseIdList.add(course.getCourseID());
        }

        // Date Format Validation
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (assignmentDueDate != null) {
            try {
                Date excursionDate = sdf.parse(assignmentDueDate);
                myCalendarAssignmentDeadline.setTime(excursionDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        editAssignmentDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;

                String info = editAssignmentDueDate.getText().toString();
                if(info.equals("")) info = assignmentDueDate;
                try {
                    myCalendarAssignmentDeadline.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(
                        AssignmentDetails.this, assignmentDeadline, myCalendarAssignmentDeadline
                        .get(Calendar.YEAR), myCalendarAssignmentDeadline.get(Calendar.MONTH),
                        myCalendarAssignmentDeadline.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        assignmentDeadline = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarAssignmentDeadline.set(Calendar.YEAR, year);
                myCalendarAssignmentDeadline.set(Calendar.MONTH, monthOfYear);
                myCalendarAssignmentDeadline.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelAssignmentStart();
            }
        };

    }

    private void updateLabelAssignmentStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editAssignmentDueDate.setText(sdf.format(myCalendarAssignmentDeadline.getTime()));
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
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String assignmentDateString = sdf.format(myCalendarAssignmentDeadline.getTime());
            Course course = null;
            List<Course> courses = repository.getmAllCourses();
            for (Course crs : courses) {
                if (crs.getCourseID() == courseID) {
                    course = crs;
                }
            }
            try {
                Date assignmentDate = sdf.parse(assignmentDateString);
                Date startDate = sdf.parse(course.getCourseStartDate());
                Date endDate = sdf.parse(course.getCourseEndDate());
                if (assignmentDate.before(startDate) || assignmentDate.after(endDate)) {
                    Toast.makeText(this, "Assignment due date must be set during the associated class.", Toast.LENGTH_LONG).show();
                    return true;
                } else {
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
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }

        if (item.getItemId() == R.id.deleteAssignment) {
            for (Assignment assignment : repository.getmAllAssignments()) {
                if (assignment.getAssignmentID() == assignmentID) currentAssignment = assignment;
            }
            repository.delete(currentAssignment);
            Toast.makeText(AssignmentDetails.this, currentAssignment.getAssignmentName() + " has been deleted.", Toast.LENGTH_LONG).show();
            AssignmentDetails.this.finish();
        }

        // Set Alert for Assignment Due Date
        if (item.getItemId() == R.id.alertDueDate) {
            String dateFromScreen = editAssignmentDueDate.getText().toString();
            String alert = "Assignment: " + assignmentName + " is due today";

            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(AssignmentDetails.this, MyReceiver.class);
            intent.putExtra("key", alert);
//            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            PendingIntent sender = PendingIntent.getBroadcast(AssignmentDetails.this, numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            numAlert = rand.nextInt(99999);
            System.out.println("numAlert Assignment = " + numAlert);
            Toast.makeText(AssignmentDetails.this, "Assignment Due Date Alert has been set", Toast.LENGTH_LONG).show();
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLabelAssignmentStart();
    }

}