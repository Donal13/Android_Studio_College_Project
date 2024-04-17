package com.example.myclassschedule.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Course")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private int termID;
    private String courseName;
    private Date courseStartDate;
    private Date courseEndDate;
    private String courseStatus;
    private String instructorName;
    private String instructorPhoneNum;
    private String instructorEmail;
    private String Notes;

    public Course(int courseID, int termID, String courseName, Date courseStartDate, Date courseEndDate, String courseStatus, String instructorName, String instructorPhoneNum, String instructorEmail, String Notes) {
        this.courseID = courseID;
        this.termID = termID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.instructorName = instructorName;
        this.instructorPhoneNum = instructorPhoneNum;
        this.instructorEmail = instructorEmail;
        this.Notes = Notes;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + termID +
                ", courseName='" + courseName + '\'' +
                '}';
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhoneNum() {
        return instructorPhoneNum;
    }

    public void setInstructorPhoneNum(String instructorPhoneNum) {
        this.instructorPhoneNum = instructorPhoneNum;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }





}
