package com.example.myclassschedule.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myclassschedule.DAO.AssessmentDAO;
import com.example.myclassschedule.DAO.CourseDAO;
import com.example.myclassschedule.DAO.TermDAO;
import com.example.myclassschedule.Entities.Assessment;
import com.example.myclassschedule.Entities.Course;
import com.example.myclassschedule.Entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 2, exportSchema = false)
@TypeConverters({DateConversion.class})
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (DatabaseBuilder.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class,"TheTermDatabase.db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
