package com.example.studentplanner03.UI;

import static org.junit.Assert.*;

import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CourseDetailsTest {




    @Test
    public void testEndDateIsAfterStartDate() throws ParseException {
        String startDateString = "02/14/25";
        String endDateString = "02/15/25";

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);

        boolean isValid = validateEndDate(startDate, endDate);

        assertTrue("End date is after start date.", isValid);
    }

    @Test
    public void testEndDateIsBeforeStartDate() throws ParseException {
        String startDateString = "02/14/25";
        String endDateString = "02/11/25";

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);

        boolean isValid = validateEndDate(startDate, endDate);

        assertFalse("End date should be after start date", isValid);
    }

    private boolean validateEndDate(Date startDate, Date endDate) {
        if (endDate.before(startDate)) {
            return false;
        }
        return true;
    }

}