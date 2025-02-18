package com.example.studentplanner03.UI;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.studentplanner03.R;
import com.example.studentplanner03.database.Repository;
import com.example.studentplanner03.entities.Course;

import java.util.List;

public class CourseReport implements Report {

    @Override
    public void generateReport(TableLayout reportTable, Repository mRepository, TextView titleOfReport) {
        titleOfReport.setText("Class Report");
        List<Course> courses = mRepository.getmAllCourses();

        TableRow headerRow = new TableRow(reportTable.getContext());
        headerRow.addView(createTextView(reportTable.getContext(), "Course Name"));
        headerRow.addView(createTextView(reportTable.getContext(), "Instructor"));
        headerRow.addView(createTextView(reportTable.getContext(), "Start Date"));
        headerRow.addView(createTextView(reportTable.getContext(), "End Date"));
        headerRow.addView(createTextView(reportTable.getContext(), "Description"));
        reportTable.addView(headerRow);

        for (Course course : courses) {
            TableRow row = new TableRow(reportTable.getContext());
            row.addView(createTextView(reportTable.getContext(), course.getCourseName()));
            row.addView(createTextView(reportTable.getContext(), course.getCourseInstructor()));
            row.addView(createTextView(reportTable.getContext(), course.getCourseStartDate()));
            row.addView(createTextView(reportTable.getContext(), course.getCourseEndDate()));
            row.addView(createTextView(reportTable.getContext(), course.getCourseDescription()));
            reportTable.addView(row);
        }
    }

    private TextView createTextView(Context context, String text) {
        TextView textView = new TextView(context);  // Use the context passed as an argument
        textView.setText(text);
        textView.setPadding(18, 12, 18, 12);
        textView.setTextSize(16);
        textView.setBackgroundResource(R.drawable.tableborder);
        return textView;
    }
}

