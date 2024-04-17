package com.example.myclassschedule.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myclassschedule.Entities.Term;
import com.example.myclassschedule.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TermDetails extends RecyclerView.Adapter<TermDetails.TermContainer> {


    class TermContainer extends RecyclerView.ViewHolder {

        private final TextView listItem1;
        private final TextView listItem2;
        private final TextView listItem3;


        private TermContainer(View item){
            super(item);
            listItem1 = item.findViewById(R.id.textView1);
            listItem2 = item.findViewById(R.id.textView2);
            listItem3 = item.findViewById(R.id.textView3);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermCreation.class);
                    intent.putExtra("termStart", DateUtility.parseDateString(current.getTermStartDate()));
                    intent.putExtra("termEnd", DateUtility.parseDateString(current.getTermEndDate()));
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("termID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mLayoutInflater;

    @NonNull
    @Override
    public TermContainer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item=mLayoutInflater.inflate(R.layout.item_list, parent, false);
        return new TermContainer(item);
    }

    @Override
    public void onBindViewHolder(@NonNull TermContainer holder, int position) {
        if(mTerms != null) {
            Term current = mTerms.get(position);
            int id = current.getTermID();
            if (holder.listItem1 != null) {
                holder.listItem1.setText(current.getTermName());
            } else {
                Log.e("TermDetailsAdapter", "listItem1 is null");
            }

            if (holder.listItem2 != null) {
                holder.listItem2.setText(DateUtility.parseDateString(current.getTermStartDate()));
            } else {
                Log.e("TermDetailsAdapter", "listItem2 is null");
            }

            if (holder.listItem3 != null) {
                holder.listItem3.setText(DateUtility.parseDateString(current.getTermEndDate()));
            } else {
                Log.e("TermDetailsAdapter", "listItem3 is null");
            }
        } else {
            holder.listItem1.setText("No Name!");
            holder.listItem2.setText("No Start Date!");
            holder.listItem3.setText("No End Date!");
        }
    }

    public TermDetails(Context context) {
        mLayoutInflater=LayoutInflater.from(context);
        this.context=context;
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    public String dateFormat(String date) throws ParseException {
        String format = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        return simpleDateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
        return mTerms.size();
        else return 0;
    }
}