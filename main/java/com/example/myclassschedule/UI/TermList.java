package com.example.myclassschedule.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclassschedule.Database.AssessmentRepository;
import com.example.myclassschedule.Database.CourseRepository;
import com.example.myclassschedule.Database.TermRepository;
import com.example.myclassschedule.Entities.Term;
import com.example.myclassschedule.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {

    private TermRepository termRepository;
    private CourseRepository courseRepository;
    private AssessmentRepository assessmentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        termRepository = new TermRepository(getApplication());
        courseRepository = new CourseRepository(getApplication());
        assessmentRepository = new AssessmentRepository(getApplication());

        List<Term> allTerms=termRepository.getmAllTerms();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);

        final TermDetails termAdapter = new TermDetails(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

    }

    public void floatingActionButton(View view) {
        Intent intent = new Intent(TermList.this, TermCreation.class);
        startActivity(intent);
    }


}