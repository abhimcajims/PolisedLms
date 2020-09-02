package com.project.polishedlms.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.polishedlms.R;

import java.util.ArrayList;

public class AllVideoListAdapter extends RecyclerView.Adapter<AllVideoListAdapter.MyViewHolder> {

    private Context cnt;
    private ItemClick click;
    ArrayList<String> video;
    ArrayList<String> title;

    public interface ItemClick {
        void onItemClick(int pos);
    }

    public AllVideoListAdapter(Context cnt, ArrayList<String> video, ArrayList<String> title, ItemClick click) {
        try {
            this.cnt = cnt;
            this.click = click;
            this.title = title;
            this.video = video;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_video, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            holder.tv_title.setText(title.get(position).trim());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public int getItemCount() {
        return video.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        private ImageView img_play;

        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);


            img_play = view.findViewById(R.id.img_play);

            img_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}

