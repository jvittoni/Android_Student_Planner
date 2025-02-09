package com.example.studentplanner03.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assignments")
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    private int assignmentID;
    private String assignmentName;
    private String assignmentDueDate;
    private String assignmentDescription;
    private int courseID;

    public Assignment(int assignmentID, String assignmentName, String assignmentDueDate, String assignmentDescription, int courseID) {
        this.assignmentID = assignmentID;
        this.assignmentName = assignmentName;
        this.assignmentDueDate = assignmentDueDate;
        this.assignmentDescription = assignmentDescription;
        this.courseID = courseID;
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentDueDate() {
        return assignmentDueDate;
    }

    public void setAssignmentDueDate(String assignmentDueDate) {
        this.assignmentDueDate = assignmentDueDate;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
