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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentplanner03.R;
import com.example.studentplanner03.database.Repository;
import com.example.studentplanner03.entities.Assignment;
import com.example.studentplanner03.entities.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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


    // Course start date and end date
    DatePickerDialog.OnDateSetListener crseStartDate;
    DatePickerDialog.OnDateSetListener crseEndDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    Course currentCourse;
    int numAssignments;

    // Notification Alert
    Random rand = new Random();
    int numAlert = rand.nextInt(99999);

    // Sharing Associated Assignments
    List<Assignment> filteredAssignments = new ArrayList<>();

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

        repository = new Repository(getApplication());

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

        // Course date format validation
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        // Notification
        numAlert = rand.nextInt(99999);

        // Floating action button to add assignments
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, AssignmentDetails.class);
                intent.putExtra("crseID", courseID);
                startActivity(intent);
            }
        });

        // show all assignments
//        RecyclerView recyclerView = findViewById(R.id.assignmentRecyclerView);
//        repository = new Repository(getApplication());
//        final AssignmentAdapter assignmentAdapter = new AssignmentAdapter(this);
//        recyclerView.setAdapter(assignmentAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        assignmentAdapter.setAssignments(repository.getmAllAssignments());

        // show only associated assignments
        RecyclerView recyclerView = findViewById(R.id.assignmentRecyclerView);
        repository = new Repository(getApplication());
        final AssignmentAdapter assignmentAdapter = new AssignmentAdapter(this);
        recyclerView.setAdapter(assignmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<Assignment> filteredAssignments = new ArrayList<>();
        for (Assignment a :repository.getmAllAssignments()) {
            if (a.getCourseID() == courseID) filteredAssignments.add(a);
        }
        assignmentAdapter.setAssignments(filteredAssignments);



        // Handle Null
        if (courseStartDate != null) {
            try {
                Date startDate = sdf.parse(courseStartDate);
                Date endDate = sdf.parse(courseEndDate);
                myCalendarStart.setTime(startDate);
                myCalendarEnd.setTime(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Course Start Date
        editCourseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editCourseStartDate.getText().toString();

                if(info.equals(""))info= courseStartDate;
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, crseStartDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        crseStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };


        // Course End Date
        editCourseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editCourseEndDate.getText().toString();

                if(info.equals(""))info= courseEndDate;
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, crseEndDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        crseEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Validate that vacation end date is after vacation start date
                if (myCalendarEnd.before(myCalendarStart)) {
                    Toast.makeText(CourseDetails.this, "Class end date must be AFTER class start date.", Toast.LENGTH_LONG).show();
                    return;
                }
                updateLabelEnd();
            }
        };

    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        // Save Course
        if (item.getItemId() == R.id.saveCourse) {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String startDateString = sdf.format(myCalendarStart.getTime());
            String endDateString = sdf.format(myCalendarEnd.getTime());
            try {
                Date startDate = sdf.parse(startDateString);
                Date endDate = sdf.parse(endDateString);
                if (endDate.before(startDate)) {
                    Toast.makeText(CourseDetails.this, "Class end date must be AFTER class start date.", Toast.LENGTH_LONG).show();
                } else {
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
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Delete Course
        if (item.getItemId() == R.id.deleteCourse) {
            for (Course crse : repository.getmAllCourses()) {
                if (crse.getCourseID() == courseID) currentCourse = crse;
            }
            numAssignments = 0;
            for (Assignment assignment : repository.getmAllAssignments()) {
                if (assignment.getCourseID() == courseID) ++numAssignments;
            }
            if (numAssignments == 0) {
                repository.delete(currentCourse);
                Toast.makeText(CourseDetails.this, currentCourse.getCourseName() + " was deleted.", Toast.LENGTH_LONG).show();
                CourseDetails.this.finish();
            } else {
                Toast.makeText(CourseDetails.this, "Can't delete a class with assignments.", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        // Set Course Start Date Alert
        if (item.getItemId() == R.id.startCourseAlert) {
            String dateFromScreen = editCourseStartDate.getText().toString();
            String alert = "Class Starting Today: " + courseName;
            alertPicker(dateFromScreen, alert);
            Toast.makeText(CourseDetails.this, "Class Start Date Alert has been set", Toast.LENGTH_LONG).show();
            this.finish();
            return true;
        }

        // Set Course End Date Alert
        if (item.getItemId() == R.id.endCourseAlert) {
            String dateFromScreen = editCourseEndDate.getText().toString();
            String alert = "Class Ending Today: " + courseName;
            alertPicker(dateFromScreen, alert);
            Toast.makeText(CourseDetails.this, "Class End Date Alert has been set.", Toast.LENGTH_LONG).show();
            this.finish();
            return true;
        }

        // Set Course Start Date and End Date Alert
        if (item.getItemId() == R.id.bothCourseAlert) {
            String dateFromScreen = editCourseStartDate.getText().toString();
            String alert = "Class Starting Today: " + courseName;
            alertPicker(dateFromScreen, alert);
            dateFromScreen = editCourseEndDate.getText().toString();
            alert = "Class Ending Today: " + courseName;
            alertPicker(dateFromScreen, alert);
            Toast.makeText(CourseDetails.this, "Class Start and End Date Alert has been set.", Toast.LENGTH_LONG).show();
            this.finish();
            return true;
        }


        // Share Course Details (Course + Assignment information)
        if (item.getItemId() == R.id.shareCourseDetails) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TITLE, "Sharing Class Details");
            StringBuilder shareData = new StringBuilder();
            shareData.append("Class Name: " + editCourseName.getText().toString() + "\n");
            shareData.append("Instructor: " + editCourseInstructor.getText().toString() + "\n");
            shareData.append("Start Date: " + editCourseStartDate.getText().toString() + "\n");
            shareData.append("End Date: " + editCourseEndDate.getText().toString() + "\n");
            for (int i = 0; i < filteredAssignments.size(); i++) {
                shareData.append((i + 1) + ") " + "Assignment Name: " + filteredAssignments.get(i).getAssignmentName() + "\n");
                shareData.append((i + 1) + ") " + "Assignment Due Date: " + filteredAssignments.get(i).getAssignmentDueDate() + "\n");
            }
            sentIntent.putExtra(Intent.EXTRA_TEXT, shareData.toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }
//        return true;
        return super.onOptionsItemSelected(item);
    }

    // Used for Course Date(s) Alerts
    public void alertPicker(String dateFromScreen, String alert) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date myDate = null;
        try {
            myDate = sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
        intent.putExtra("key", alert);
//        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        numAlert = rand.nextInt(99999);
        System.out.println("numAlert Vacation = " + numAlert);
    }


    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView recyclerView = findViewById(R.id.assignmentRecyclerView);
        repository = new Repository(getApplication());
        final AssignmentAdapter assignmentAdapter = new AssignmentAdapter(this);
        recyclerView.setAdapter(assignmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assignment> filteredAssignments = new ArrayList<>();
        for (Assignment a :repository.getmAllAssignments()) {
            if (a.getCourseID() == courseID) filteredAssignments.add(a);
        }
        assignmentAdapter.setAssignments(filteredAssignments);

        // Class start and end dates
        updateLabelStart();
        updateLabelEnd();
    }

}