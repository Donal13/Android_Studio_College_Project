package com.example.myclassschedule.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Term")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termName;
    private Date termStartDate;
    private Date termEndDate;
    public Term(int termID, String termName, Date termStartDate, Date termEndDate) {
        this.termID = termID;
        this.termName = termName;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    @Override
    public String toString() {
        return "Term{" +
                "termID=" + termID +
                ", termName='" + termName + '\'' +
                '}';
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(Date termStartDate) {
        this.termStartDate = termStartDate;
    }

    public Date getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(Date termEndDate) {
        this.termEndDate = termEndDate;
    }


}
