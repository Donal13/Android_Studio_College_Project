package com.example.myclassschedule.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclassschedule.Database.AssessmentRepository;
import com.example.myclassschedule.Database.CourseRepository;
import com.example.myclassschedule.Database.DateUtility;
import com.example.myclassschedule.Entities.Assessment;
import com.example.myclassschedule.Entities.Course;
import com.example.myclassschedule.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CourseCreation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String name;
    String startDate;
    String endDate;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String status;
    String optionalNotes;

    EditText editName;
    EditText editSDate;
    EditText editEDate;
    EditText editInstructor;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    EditText editOptionalText;

    int courseID;
    int termID;
    int numAlert;
    CourseRepository courseRepository;
    AssessmentRepository assessmentRepository;

    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_creation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.courseStatus, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        status = getIntent().getStringExtra("status");
        spinner.setSelection(arrayAdapter.getPosition(status));

        name = getIntent().getStringExtra("courseTitle");
        editName = findViewById(R.id.courseName);
        editName.setText(name);

        startDate = getIntent().getStringExtra("courseStart");
        editSDate = findViewById(R.id.courseStart);
        editSDate.setText(startDate);

        endDate = getIntent().getStringExtra("courseEnd");
        editEDate = findViewById(R.id.courseEnd);
        editEDate.setText(endDate);

        instructorName = getIntent().getStringExtra("instructorName");
        editInstructor = findViewById(R.id.instructorName);
        editInstructor.setText(instructorName);

        instructorEmail = getIntent().getStringExtra("instructorEmail");
        editInstructorEmail = findViewById(R.id.instructorEmail);
        editInstructorEmail.setText(instructorEmail);

        instructorPhone = getIntent().getStringExtra("instructorPhone");
        editInstructorPhone = findViewById(R.id.instructorPhone);
        editInstructorPhone.setText(instructorPhone);

        optionalNotes = getIntent().getStringExtra("optionalNotes");
        editOptionalText =findViewById(R.id.optionalNotes);
        editOptionalText.setText(optionalNotes);

        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        final TextView fabLabel = findViewById(R.id.label_add_assessment);

        if (termID==-1) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                termID = extras.getInt("key");
            }
        }

        if (name==null){
            FloatingActionButton button = findViewById(R.id.floatingActionButton);
            fabLabel.setTextColor(Color.TRANSPARENT);
            button.hide();
        }
        courseRepository = new CourseRepository(getApplication());
        assessmentRepository = new AssessmentRepository(getApplication());

        List<Assessment> allAssessment = new ArrayList<>();
        for (Assessment assessment : assessmentRepository.getAllAssessments()) {
            if (assessment.getCourseID() == courseID)
                allAssessment.add(assessment);
        }

        editOptionalText.setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.recyclerAssessmentView);
        if (recyclerView != null) {
            final AssessmentDetails assessmentAdapter = new AssessmentDetails(this);
            recyclerView.setAdapter(assessmentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            assessmentAdapter.setAssessments(allAssessment);
        } else {
            Log.e("CourseCreation", "RecyclerView is null. Check the layout and ID.");

        }
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
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
        editSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseCreation.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseCreation.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        } else if (id == R.id.courseDelete) {
            for (Course course : courseRepository.getAllCourses()) {
                if (course.getCourseID() == courseID) {
                    courseRepository.delete(course);
                    Toast.makeText(this, "Course Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), TermList.class);
                    startActivity(intent);
                }
            }
            return true;
        } else if (id == R.id.courseShare) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    ("Course Name: " + name.trim() + "\n" +
                            "Start Date: " + startDate.trim() + "\n" +
                            "End Date: " + endDate.trim() + "\n" +
                            "Status: " + status.trim() + "\n" +
                            "\n" +
                            "Course Instructor: " + instructorName.trim() + "\n" +
                            "Email: " + instructorEmail.trim() + "\n" +
                            "Phone: " + instructorPhone.trim() + "\n" +
                            "Courses Notes: " + optionalNotes.trim()).trim()); // Also trim the final concatenated string
            sendIntent.putExtra(Intent.EXTRA_TITLE, ("Share " + name.trim() + " Course Info").trim());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        } else if (id == R.id.courseNotifyStart) {
            String dateFromString = editSDate.getText().toString();
            long trigger = DateUtility.parseDate(dateFromString).getTime();
            Intent intentCStart = new Intent(CourseCreation.this, CustomReceiver.class);
            intentCStart.putExtra("key", "Alert! Course: " + name + " starts: " + dateFromString);
            PendingIntent sender = PendingIntent.getBroadcast(CourseCreation.this, ++numAlert, intentCStart, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        } else if (id == R.id.courseNotifyEnd) {
            String dateFromString2 = editEDate.getText().toString();
            long trigger2 = DateUtility.parseDate(dateFromString2).getTime();
            Intent intentCEnd = new Intent(CourseCreation.this, CustomReceiver.class);
            intentCEnd.putExtra("key", "Alert! Course: " + name + " Ends: " + dateFromString2);
            PendingIntent senderCEndDate = PendingIntent.getBroadcast(CourseCreation.this, ++numAlert, intentCEnd, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, senderCEndDate);
            return true;
        } else if (id == R.id.courseShowNotes) {
            editOptionalText.setText(optionalNotes);
            editOptionalText.setVisibility(View.VISIBLE);
            return true;
        } else if (id == R.id.courseRefresh) {
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            List<Assessment> allAssessment = new ArrayList<>();
            for (Assessment assessment : assessmentRepository.getAllAssessments()) {
                if (assessment.getCourseID() == courseID)
                    allAssessment.add(assessment);
            }
            final AssessmentDetails assessmentAdapter = new AssessmentDetails(this);
            recyclerView.setAdapter(assessmentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            assessmentAdapter.setAssessments(allAssessment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void addCAssessment(View view) {
        Intent intent = new Intent(CourseCreation.this, AssessmentCreation.class);
        courseID= getIntent().getIntExtra("courseID", -1);
        intent.putExtra("key2", courseID);
        startActivity(intent);

    }

    public void saveCourse(View view) {
        if (editEDate.getText().toString().trim().isEmpty() || editSDate.getText().toString().trim().isEmpty()|| editName.getText().toString().trim().isEmpty()|| editInstructor.getText().toString().trim().isEmpty()
                || editInstructorEmail.getText().toString().trim().isEmpty()|| editInstructorPhone.getText().toString().trim().isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "Ensure all text fields are filled in prior to saving.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            String screenName = editName.getText().toString();
            Date screenDate = DateUtility.parseDate(editSDate.getText().toString());
            Date screenDate2 = DateUtility.parseDate(editEDate.getText().toString());
            String screenInstructor = editInstructor.getText().toString();
            String screenInstructorEmail = editInstructorEmail.getText().toString();
            String screenInstructorPhone = editInstructorPhone.getText().toString();
            String optionNotes= editOptionalText.getText().toString();

            if (courseID == -1) {
                courseID = (assessmentRepository.getAllAssessments().size()-1);
                // courseID = 1;
                System.out.println(courseRepository.getAllCourses().size());
                Course newCourse = new Course(
                        ++courseID,
                        termID,
                        screenName,
                        screenDate,
                        screenDate2,
                        status,
                        screenInstructor,
                        screenInstructorPhone,
                        screenInstructorEmail,
                        optionNotes);
                courseRepository.insert(newCourse);

            } else {
                System.out.println(termID);
                Course oldCourse = new Course(courseID, termID, screenName, screenDate, screenDate2, status, screenInstructor, screenInstructorPhone, screenInstructorEmail, optionNotes);
                courseRepository.update(oldCourse);
            }
        }
        this.finish();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        status = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}