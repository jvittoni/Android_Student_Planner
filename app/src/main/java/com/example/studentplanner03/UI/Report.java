package com.example.studentplanner03.UI;

import android.widget.TableLayout;
import android.widget.TextView;

import com.example.studentplanner03.database.Repository;

public interface Report {
    void generateReport(TableLayout reportTable, Repository mRepository, TextView titleOfReport);
}
