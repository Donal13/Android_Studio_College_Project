package com.example.myclassschedule.Database;

import android.app.Application;

import com.example.myclassschedule.DAO.TermDAO;
import com.example.myclassschedule.Entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TermRepository {
    private final TermDAO mTermDAO;
    private List<Term> mAllTerms;
    private static final int NUM_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    public TermRepository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
    }

    public List<Term> getmAllTerms() {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Term term) {
        databaseExecutor.execute(() -> mTermDAO.insert(term));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Term term) {
        databaseExecutor.execute(() -> mTermDAO.update(term));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Term term) {
        databaseExecutor.execute(() -> mTermDAO.delete(term));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}