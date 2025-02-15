package com.example.studentplanner03.UI;

import static org.junit.Assert.*;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AssignmentDetailsTest {

    @Test
    public void testDateFormatValidation() throws ParseException {
        String expectedDateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(expectedDateFormat, Locale.US);
        String validDate = "02/15/25";
//        assertEquals("Date should match the format", validDate, sdf.format(sdf.parse(validDate)));
        String formattedDate = sdf.format(sdf.parse(validDate));
        assertTrue("Date matches the format", validDate.equals(formattedDate));
    }

    @Test
    public void testInvalidDateFormat() throws ParseException {
        String expectedDateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(expectedDateFormat, Locale.US);
        String invalidDate = "3/3/2025";
//        assertNotEquals("Date does not match the format", invalidDate, sdf.format(sdf.parse(invalidDate)));
        String formattedDate = sdf.format(sdf.parse(invalidDate));
        assertFalse("Date does not match the format", invalidDate.equals(formattedDate));
    }

    @Test
    public void assignmentDueDateIsValid() throws ParseException {
        String expectedDateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(expectedDateFormat, Locale.US);
        String startDateString = "02/14/25";
        String endDateString = "02/27/25";
        String assignmentDueDate = "02/18/25";

        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);
        Date dueDate = sdf.parse(assignmentDueDate);

        boolean isValid = validateAssignmentDate(startDate, endDate, dueDate);
        assertTrue("Assignment Due date is between Course Start and End date.", isValid);
    }

    @Test
    public void assignmentDueDateIsNotValid() throws ParseException {
        String expectedDateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(expectedDateFormat, Locale.US);
        String startDateString = "02/14/25";
        String endDateString = "02/27/25";
        String assignmentDueDate = "03/10/25";

        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);
        Date dueDate = sdf.parse(assignmentDueDate);

        boolean isNotValid = validateAssignmentDate(startDate, endDate, dueDate);
        assertFalse("Assignment Due date is not between Course Start and End date.", isNotValid);
    }

    private boolean validateAssignmentDate(Date courseStartDate, Date courseEndDate, Date assignmentDueDate) {
        if (assignmentDueDate.before(courseStartDate) || assignmentDueDate.after(courseEndDate)) {
            return false;
        }
        return true;
    }

}