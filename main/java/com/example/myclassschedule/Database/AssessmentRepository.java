package com.example.myclassschedule.Database;

import android.app.Application;

import com.example.myclassschedule.DAO.AssessmentDAO;
import com.example.myclassschedule.Entities.Assessment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssessmentRepository {


    private final AssessmentDAO mAssessmentDAO;
    private List<Assessment> mAllAssessments;
    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public AssessmentRepository(Application application){
        DatabaseBuilder db= DatabaseBuilder.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
    }

    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()-> mAllAssessments=mAssessmentDAO.getAllAssessments());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.insert(assessment));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.update(assessment));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.delete(assessment));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
