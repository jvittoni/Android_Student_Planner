package com.example.studentplanner03.UI;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.studentplanner03.R;
import com.example.studentplanner03.database.Repository;
import com.example.studentplanner03.entities.Assignment;
import com.example.studentplanner03.entities.Course;

import java.util.List;

public class AssignmentReport implements Report {
    @Override
    public void generateReport(TableLayout reportTable, Repository mRepository, TextView titleOfReport) {
        titleOfReport.setText("Assignment Report");
        List<Assignment> assignments = mRepository.getmAllAssignments();

        TableRow headerRow = new TableRow(reportTable.getContext());
        headerRow.addView(createTextView(reportTable.getContext(), "Assignment Name"));
        headerRow.addView(createTextView(reportTable.getContext(), "Due Date"));
        headerRow.addView(createTextView(reportTable.getContext(), "Description"));
        reportTable.addView(headerRow);

        for (Assignment assignment : assignments) {
            Course course = mRepository.getCourseById(assignment.getCourseID());
            TableRow row = new TableRow(reportTable.getContext());
            row.addView(createTextView(reportTable.getContext(), assignment.getAssignmentName()));
            row.addView(createTextView(reportTable.getContext(), assignment.getAssignmentDueDate()));
            row.addView(createTextView(reportTable.getContext(), assignment.getAssignmentDescription()));
            reportTable.addView(row);
        }
    }

    private TextView createTextView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setPadding(18, 12, 18, 12);
        textView.setTextSize(16);
        textView.setBackgroundResource(R.drawable.tableborder);
        return textView;
    }
}
