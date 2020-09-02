package com.project.polishedlms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.polishedlms.R;

public class NoDataAdapter extends RecyclerView.Adapter<NoDataAdapter.MyViewHolder> {

    Context context;

    public NoDataAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NoDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.no_data, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoDataAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;

        public MyViewHolder(View view) {
            super(view);
        }
    }
}
