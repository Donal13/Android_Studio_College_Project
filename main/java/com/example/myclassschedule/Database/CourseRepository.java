package com.example.myclassschedule.Database;

import android.app.Application;

import com.example.myclassschedule.DAO.CourseDAO;
import com.example.myclassschedule.Entities.Course;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseRepository {
    private final CourseDAO mCourseDAO;
    private List<Course> mAllCourses;
    private static final int NUM_THREADS =4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUM_THREADS);

    public CourseRepository(Application application){
        DatabaseBuilder db= DatabaseBuilder.getDatabase(application);
        mCourseDAO = db.courseDAO();
    }

    public List<Course> getAllCourses(){
        databaseExecutor.execute(()-> mAllCourses=mCourseDAO.getAllCourses());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(Course course){
        databaseExecutor.execute(()-> mCourseDAO.insert(course));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Course course){
        databaseExecutor.execute(()-> mCourseDAO.update(course));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Course course){
        databaseExecutor.execute(()-> mCourseDAO.delete(course));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
