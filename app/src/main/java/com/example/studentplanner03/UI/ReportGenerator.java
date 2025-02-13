package com.example.studentplanner03.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
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

import java.util.List;

public class ReportGenerator extends AppCompatActivity {

    private Repository mRepository;
    private TableLayout reportTable;
    private RadioGroup reportTypeGroup;
    private TextView titleOfReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_generator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mRepository = new Repository(getApplication());
        reportTable = findViewById(R.id.generatedReportTable);
        reportTypeGroup = findViewById(R.id.reportOptions);
        Button generateReportButton = findViewById(R.id.reportButton);

        generateReportButton.setOnClickListener(v -> generateReport());

        titleOfReport = findViewById(R.id.reportTitle);

    }

    private void generateReport() {
        int selectedReportType = reportTypeGroup.getCheckedRadioButtonId();
        reportTable.removeAllViews();

        if (selectedReportType == R.id.courseReport) {
            titleOfReport.setText("Class Report");
            generateCourseReport();
        } else if (selectedReportType == R.id.assignmentReport) {
            titleOfReport.setText("Assignment Report");
            generateAssignmentReport();
        } else if (selectedReportType == R.id.bothReport) {
            titleOfReport.setText("Class and Assignment Report");
            generateCourseAndAssignmentReport();
        }
    }

    // Course Report
    private void generateCourseReport() {
        List<Course> courses = mRepository.getmAllCourses();

        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("Course Name"));
        headerRow.addView(createTextView("Instructor"));
        headerRow.addView(createTextView("Start Date"));
        headerRow.addView(createTextView("End Date"));
        headerRow.addView(createTextView("Description"));
        reportTable.addView(headerRow);

        for (Course course : courses) {
            TableRow row = new TableRow(this);
            row.addView(createTextView(course.getCourseName()));
            row.addView(createTextView(course.getCourseInstructor()));
            row.addView(createTextView(course.getCourseStartDate()));
            row.addView(createTextView(course.getCourseEndDate()));
            row.addView(createTextView(course.getCourseDescription()));
            reportTable.addView(row);
        }
    }

    // Assignment Report
    private void generateAssignmentReport() {
        List<Assignment> assignments = mRepository.getmAllAssignments();

        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("Assignment Name"));
        headerRow.addView(createTextView("Due Date"));
        headerRow.addView(createTextView("Description"));
//        headerRow.addView(createTextView("Course Name"));
        reportTable.addView(headerRow);

        for (Assignment assignment : assignments) {
            Course course = mRepository.getCourseById(assignment.getCourseID());
            TableRow row = new TableRow(this);
            row.addView(createTextView(assignment.getAssignmentName()));
            row.addView(createTextView(assignment.getAssignmentDueDate()));
            row.addView(createTextView(assignment.getAssignmentDescription()));
//            row.addView(createTextView(course.getCourseName()));
            reportTable.addView(row);
        }
    }

    // Course and Assignment Report
    private void generateCourseAndAssignmentReport() {
        List<Course> courses = mRepository.getmAllCourses();

        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("Course Name"));
        headerRow.addView(createTextView("Assignment Name"));
        headerRow.addView(createTextView("Assignment Due Date"));
        reportTable.addView(headerRow);

        for (Course course : courses) {
            List<Assignment> assignments = mRepository.getAssociatedAssignments(course.getCourseID());
            for (Assignment assignment : assignments) {
                TableRow row = new TableRow(this);
                row.addView(createTextView(course.getCourseName()));
                row.addView(createTextView(assignment.getAssignmentName()));
                row.addView(createTextView(assignment.getAssignmentDueDate()));
                reportTable.addView(row);
            }
        }
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        textView.setTextSize(16);
        textView.setBackgroundResource(R.drawable.tableborder);
        return textView;
    }
}