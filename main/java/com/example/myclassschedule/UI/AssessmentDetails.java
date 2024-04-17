package com.example.myclassschedule.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclassschedule.Database.DateUtility;
import com.example.myclassschedule.Entities.Assessment;
import com.example.myclassschedule.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AssessmentDetails extends RecyclerView.Adapter<AssessmentDetails.AssessmentViewHolder> {

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflator;
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView listItemView;
        private final TextView listItemView1;
        private final TextView listItemView2;


        private AssessmentViewHolder(View itemView) {
            super(itemView);
            listItemView = itemView.findViewById(R.id.textView1);
            listItemView1 = itemView.findViewById(R.id.textView2);
            listItemView2 = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentCreation.class);
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("startDate", DateUtility.parseDateString(current.getAssessmentStartDate()));
                    intent.putExtra("endDate", DateUtility.parseDateString(current.getAssessmentEndDate()));
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("courseID",current.getCourseID());
                    intent.putExtra("assessmentType",current.getAssessmentType());
                    context.startActivity(intent);

                }
            });
        }
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.item_list, parent, false);
        return new AssessmentViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            holder.listItemView.setText((current.getAssessmentName()));
            holder.listItemView1.setText(DateUtility.parseDateString(current.getAssessmentStartDate()));
            holder.listItemView2.setText(DateUtility.parseDateString(current.getAssessmentEndDate()));
        } else {
            holder.listItemView.setText("No Name!");
            holder.listItemView1.setText("No Start Date!");
            holder.listItemView2.setText("No End Date!");
        }
    }

    public AssessmentDetails(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }


    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
    public String dateFormat(String date) throws ParseException {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(date);
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}