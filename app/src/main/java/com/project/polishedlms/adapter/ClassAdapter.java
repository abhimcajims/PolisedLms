package com.project.polishedlms.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.polishedlms.R;
import com.project.polishedlms.model.ClassModel;

import java.util.ArrayList;


public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {
    private Context cnt;
    private ItemClick click;
    ArrayList<ClassModel.Data> classList;

    public interface ItemClick {
        void onItemClick(int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_status, tv_endTime, tv_startTime, tv_date, tv_duration;
        public TextView tv_subject, tv_course;
        private CardView card;

        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            tv_status = view.findViewById(R.id.tv_status);
            tv_startTime = view.findViewById(R.id.tv_startTime);
            tv_endTime = view.findViewById(R.id.tv_endTime);
            tv_date = view.findViewById(R.id.tv_date);
            tv_subject = view.findViewById(R.id.tv_subject);
            tv_course = view.findViewById(R.id.tv_course);
            tv_duration = view.findViewById(R.id.tv_duration);
            card = view.findViewById(R.id.card);
            tv_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.onItemClick(getAdapterPosition());
                }
            });
        }
    }


    public ClassAdapter(Context cnt, ArrayList<ClassModel.Data> classList, ItemClick click) {
        try {
            this.cnt = cnt;
            this.click = click;
            this.classList = classList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            holder.tv_title.setText(classList.get(position).title);
            if (classList.get(position).status.equalsIgnoreCase("live")) {
                holder.tv_status.setText("Launch");
                holder.tv_status.setTextColor(cnt.getResources().getColor(R.color.colorGreen));
            } else {
                holder.tv_status.setText(classList.get(position).status);
                holder.tv_status.setTextColor(cnt.getResources().getColor(R.color.colorRed));
            }

            holder.tv_endTime.setText(classList.get(position).end_time);
            holder.tv_startTime.setText(classList.get(position).start_time);
            holder.tv_date.setText(classList.get(position).date);

            holder.tv_course.setText(classList.get(position).courseName);
            holder.tv_subject.setText(classList.get(position).subject_name);

            int a = Integer.valueOf(classList.get(position).duration);
            holder.tv_duration.setText(String.valueOf(a / 60) + "min");


        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}


