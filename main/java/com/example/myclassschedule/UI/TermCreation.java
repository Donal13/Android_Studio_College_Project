package com.example.myclassschedule.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclassschedule.Database.CourseRepository;
import com.example.myclassschedule.Database.DateUtility;
import com.example.myclassschedule.Database.TermRepository;
import com.example.myclassschedule.Entities.Course;
import com.example.myclassschedule.Entities.Term;
import com.example.myclassschedule.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TermCreation extends AppCompatActivity {
    public static int numAlert;
    String name;
    String startDate;
    String endDate;
    EditText editName;
    EditText editSDate;
    EditText editEDate;
    int termId;
    TermRepository termRepository;
    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    final Calendar myCalendar = Calendar.getInstance();
    CourseRepository courseRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_creation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = getIntent().getStringExtra("termName");
        editName = findViewById(R.id.termName);
        editName.setText(name);

        startDate = getIntent().getStringExtra("termStart");
        editSDate = findViewById(R.id.termStart);
        editSDate.setText(startDate);

        endDate = getIntent().getStringExtra("termEnd");
        editEDate = findViewById(R.id.termEnd);
        editEDate.setText(endDate);

        termId = getIntent().getIntExtra("termID", -1);
        termRepository = new TermRepository(getApplication());
        courseRepository = new CourseRepository(getApplication());

        final TextView fabLabel = findViewById(R.id.label_add_course);



        if (name==null){
            FloatingActionButton button = findViewById(R.id.floatingActionButton);
            fabLabel.setTextColor(Color.TRANSPARENT);
            button.hide();
        }


        RecyclerView recyclerView = findViewById(R.id.recyclerCourseView);
        List<Course> allCourses = new ArrayList<>();
        for (Course course : courseRepository.getAllCourses()) {
            if (course.getTermID() == termId)
                allCourses.add(course);
        }

        final CourseDetails courseDetails = new CourseDetails(this);
        recyclerView.setAdapter(courseDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseDetails.setCourse(allCourses);

        date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermCreation.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermCreation.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        } else if (id == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Semester or Term " + editName.getText());
            sendIntent.putExtra(Intent.EXTRA_TITLE, editName.getText());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        } else if (id == R.id.notifyStart) {
            String dateFromString = editSDate.getText().toString();
            long trigger = DateUtility.parseDate(dateFromString).getTime();
            Intent intentTStart = new Intent(TermCreation.this, CustomReceiver.class);
            intentTStart.putExtra("key", "Alert! Term: "+ name+ " Starts: " + dateFromString);
            PendingIntent senderTStart = PendingIntent.getBroadcast(TermCreation.this, ++numAlert, intentTStart, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderTStart);
            return true;
        } else if (id == R.id.notifyEnd) {
            String dateFromString2 = editEDate.getText().toString();
            long trigger2 = DateUtility.parseDate(dateFromString2).getTime();
            Intent intentTEnd = new Intent(TermCreation.this, CustomReceiver.class);
            intentTEnd.putExtra("key", "Alert! Term: "+ name+ " Ends: " + dateFromString2);
            PendingIntent senderTEnd = PendingIntent.getBroadcast(TermCreation.this, ++numAlert, intentTEnd, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, senderTEnd);
            return true;
        } else if (id == R.id.refresh) {
            RecyclerView recyclerView = findViewById(R.id.recyclerCourseView);
            List<Course> allCourse = new ArrayList<>();
            for (Course course : courseRepository.getAllCourses()) {
                if (course.getTermID() == termId)
                    allCourse.add(course);
            }
            final CourseDetails courseAdapter = new CourseDetails(this);
            recyclerView.setAdapter(courseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            courseAdapter.setCourse(allCourse);
            return true;
        } else if (id == R.id.delete) {
            boolean termWithoutCourses = true;
            for (Course course : courseRepository.getAllCourses()) {
                if (course.getTermID() == termId) {
                    termWithoutCourses = false;
                    break;
                }
            }
            if (termWithoutCourses) {
                for (Term term : termRepository.getmAllTerms()) {
                    if (term.getTermID() == termId) {
                        termRepository.delete(term);
                        Toast.makeText(this, "Term Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(getApplicationContext(), TermList.class);
                        startActivity(intent3);
                    }
                }
            } else {
                Toast.makeText(this, "Term Has an associated course, please delete courses first.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTerm(View view) {
        if (editEDate.getText().toString().trim().isEmpty() || editSDate.getText().toString().trim().isEmpty()|| editName.getText().toString().trim().isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "Ensure all text fields are filled in prior to saving.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            String screenName = editName.getText().toString();
            Date screenDate = DateUtility.parseDate(editSDate.getText().toString());
            Date screenDate2 = DateUtility.parseDate(editEDate.getText().toString());

            if (name == null) {
                termId = termRepository.getmAllTerms().size();
                Term newTerm = new Term(
                        ++termId,
                        screenName,
                        screenDate,
                        screenDate2);
                termRepository.insert(newTerm);
            } else {
                Term oldTerm = new Term(getIntent().getIntExtra("termID", -1), screenName, screenDate, screenDate2);
                termRepository.update(oldTerm);
            }
            Intent intent = new Intent(TermCreation.this, TermList.class);
            startActivity(intent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void onCancel(View view) {
        this.finish();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editSDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void addCourse(View view) {
        Intent intent = new Intent(TermCreation.this, CourseCreation.class);
        intent.putExtra("key", termId);
        startActivity(intent);
    }
}
