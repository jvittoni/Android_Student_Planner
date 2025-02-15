package com.example.studentplanner03.UI;

import static com.google.common.base.Verify.verify;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import android.view.Menu;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.studentplanner03.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class AssignmentDetailsTest {

    private AssignmentDetails assignmentDetails;

    @Test
    public void testDateFormatValidation() {
        String expectedDateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(expectedDateFormat, Locale.US);
        String validDate = "02/15/25";
        try {
            sdf.parse(validDate);
        } catch (ParseException e) {
            fail("ParseException should not be thrown for valid date: " + validDate);
        }
    }


//    @Test
//    public void testAssignmentNameInput() {
//        // Create an instance of AssignmentDetails
//        AssignmentDetails activity = new AssignmentDetails();
//
//        // Simulate calling onCreate to initialize the activity
//        activity.onCreate(null);
//
//        // Set some input text in the EditText field
//        String inputText = "Test Assignment";
//        activity.editAssignmentName.setText(inputText);
//
//        // Assert that the text entered is the same as the input
//        assertEquals("Text entered in Assignment Name field is incorrect.", inputText, activity.editAssignmentName.getText().toString());
//    }



    @Test
    public void onOptionsItemSelected() {
    }
}