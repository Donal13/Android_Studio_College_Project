package com.example.myclassschedule.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Assessment")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseID;
    private String assessmentName;
    private Date assessmentStartDate;
    private Date assessmentEndDate;
    private String assessmentType;

    public Assessment(int assessmentID, int courseID, String assessmentName, Date assessmentStartDate, Date assessmentEndDate, String assessmentType) {
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.assessmentName = assessmentName;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Date getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(Date assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public Date getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(Date assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }


}
