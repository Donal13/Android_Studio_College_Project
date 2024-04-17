package com.example.myclassschedule.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myclassschedule.Database.AssessmentRepository;
import com.example.myclassschedule.Database.DateUtility;
import com.example.myclassschedule.Entities.Assessment;
import com.example.myclassschedule.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AssessmentCreation extends AppCompatActivity {

    String assessmentName;
    String startDate;
    String endDate;
    EditText editName;
    EditText editSDate;
    EditText editEDate;
    RadioButton radioButtonOA;
    RadioButton radioButtonPA;
    int courseID;
    int assessmentID;
    public static int numAlert;
    String radioIDSelection;
    AssessmentRepository assessmentRepository;
    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    RadioGroup radioGroup;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_creation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        assessmentName = getIntent().getStringExtra("assessmentName");
        editName = findViewById(R.id.assessmentName);
        editName.setText(assessmentName);

        startDate = getIntent().getStringExtra("startDate");
        editSDate = findViewById(R.id.assessmentStart);
        editSDate.setText(startDate);

        endDate = getIntent().getStringExtra("endDate");
        editEDate = findViewById(R.id.assessmentEnd);
        editEDate.setText(endDate);

        assessmentID = getIntent().getIntExtra("assessmentID",-1);

        assessmentRepository = new AssessmentRepository(getApplication());
        courseID = getIntent().getIntExtra("courseID", -1);
        radioGroup = findViewById(R.id.radioGroup);

        radioIDSelection= getIntent().getStringExtra("assessmentType");

        radioButtonOA =(RadioButton)findViewById(R.id.radioButton2);
        radioButtonPA =(RadioButton)findViewById(R.id.radioButton1);

        if (radioIDSelection!=null) {
            if (radioIDSelection.equalsIgnoreCase("Performance Assessment")) {
                radioButtonPA.setChecked(true);
            } else if (radioIDSelection.equalsIgnoreCase("Objective Assessment")) {
                radioButtonOA.setChecked(true);
            } else
                radioButtonOA.setChecked(true);
        }

        if (courseID==-1) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) { courseID = extras.getInt("key2");
            }
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
                new DatePickerDialog(AssessmentCreation.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentCreation.this, date2, myCalendar
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
        } else if (id == R.id.assessNotifyStart) {
            String dateFromString = editSDate.getText().toString();
            long trigger = DateUtility.parseDate(dateFromString).getTime();
            Intent intentAStart = new Intent(AssessmentCreation.this, CustomReceiver.class);
            intentAStart.putExtra("key", "Alert! Assessment: " + assessmentName + " Starts: " + dateFromString);
            PendingIntent senderAStart = PendingIntent.getBroadcast(AssessmentCreation.this, ++numAlert, intentAStart, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderAStart);
            return true;
        } else if (id == R.id.assessNotifyEnd) {
            String dateFromString2 = editEDate.getText().toString();
            long trigger2 = DateUtility.parseDate(dateFromString2).getTime();
            Intent intentAEnd = new Intent(AssessmentCreation.this, CustomReceiver.class);
            intentAEnd.putExtra("key", "Alert! Assessment: " + assessmentName + " Ends: " + dateFromString2);
            PendingIntent senderAEnd = PendingIntent.getBroadcast(AssessmentCreation.this, ++numAlert, intentAEnd, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, senderAEnd);
            return true;
        } else if (id == R.id.assessDelete) {
            for (Assessment assessment : assessmentRepository.getAllAssessments()) {
                if (assessment.getAssessmentID() == assessmentID) {
                    assessmentRepository.delete(assessment);
                    Toast.makeText(this, "Assessment Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent3 = new Intent(getApplicationContext(), TermList.class);
                    startActivity(intent3);
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assess_menu,menu);
        return true;
    }

    public void onCancel(View view) {
        this.finish();
    }

    public void saveAssessment(View view) {

        if (editEDate.getText().toString().trim().isEmpty() || editSDate.getText().toString().trim().isEmpty()|| editName.getText().toString().trim().isEmpty()) {
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

            if (assessmentName == null) {
                assessmentID = assessmentRepository.getAllAssessments().size();
                Assessment newAssessment = new Assessment(++assessmentID, courseID,screenName,screenDate, screenDate2, radioIDSelection);
                assessmentRepository.insert(newAssessment);

            } else {
                Assessment oldAssessment = new Assessment(getIntent().getIntExtra("assessmentID", -1),courseID, screenName,screenDate, screenDate2,radioIDSelection);
                assessmentRepository.update(oldAssessment);
            }
        }
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

    public void checkButton(View view) {
        int radioID = radioGroup.getCheckedRadioButtonId();
        RadioButton oncSelected = findViewById(radioID);
        radioIDSelection = "" + oncSelected.getText();
    }

}