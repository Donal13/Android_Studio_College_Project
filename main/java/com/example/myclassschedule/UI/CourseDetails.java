package com.example.myclassschedule.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclassschedule.Database.DateUtility;
import com.example.myclassschedule.Entities.Course;
import com.example.myclassschedule.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends RecyclerView.Adapter<CourseDetails.CourseViewHolder> {

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflator;

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView listItemView;
        private final TextView listItemView1;
        private final TextView listItemView2;

        private CourseViewHolder(View itemView) {
            super(itemView);
            listItemView = itemView.findViewById(R.id.textView1);
            listItemView1 = itemView.findViewById(R.id.textView2);
            listItemView2 = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseCreation.class);
                    intent.putExtra("courseID" ,current.getCourseID());
                    intent.putExtra("courseTitle", current.getCourseName());
                    intent.putExtra("courseStart", DateUtility.parseDateString(current.getCourseStartDate()));
                    intent.putExtra("courseEnd", DateUtility.parseDateString(current.getCourseEndDate()));
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("instructorPhone", current.getInstructorPhoneNum());
                    intent.putExtra("status" ,current.getCourseStatus());
                    intent.putExtra("optionalNotes",current.getNotes());
                    context.startActivity(intent);

                }
            });
        }
    }
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflator.inflate(R.layout.item_list, parent, false);
        return new CourseDetails.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if(mCourses!=null){
            Course current=mCourses.get(position);
            holder.listItemView.setText((current.getCourseName()));
            holder.listItemView1.setText(DateUtility.parseDateString(current.getCourseStartDate()));
            holder.listItemView2.setText(DateUtility.parseDateString(current.getCourseEndDate()));
        }
        else{
            holder.listItemView.setText("No Name!");
            holder.listItemView1.setText("No Start Date!");
            holder.listItemView2.setText("No End Date!");
        }
    }

    public CourseDetails(Context context){
        mInflator=LayoutInflater.from(context);
        this.context=context;
    }


    public void setCourse(List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }
    public String dateFormat(String date) throws ParseException {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(date);
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }
}