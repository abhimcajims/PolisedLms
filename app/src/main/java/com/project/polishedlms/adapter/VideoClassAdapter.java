package com.project.polishedlms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.polishedlms.model.VideoListModel;
import com.project.polishedlms.R;

import java.util.ArrayList;

public class VideoClassAdapter extends RecyclerView.Adapter<VideoClassAdapter.MyViewHolder> {
    private Context cnt;
    private ItemClick click;
    ArrayList<VideoListModel.Data> videoListModel;

    public interface ItemClick {
        void onItemClick(int pos);

        void onViewAllVideoClick(int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_startDate, tv_topic, tv_endDate, tv_course,
                tv_subject, tv_teacher, tv_viewAllVideo;
        private CardView card;
        private ImageView img_play;
        private LinearLayout ll_viewAllVideo;

        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);

            tv_startDate = view.findViewById(R.id.tv_startDate);
            tv_topic = view.findViewById(R.id.tv_topic);
            tv_endDate = view.findViewById(R.id.tv_endDate);

            card = view.findViewById(R.id.card);
            img_play = view.findViewById(R.id.img_play);
            tv_course = view.findViewById(R.id.tv_course);
            tv_subject = view.findViewById(R.id.tv_subject);
            tv_teacher = view.findViewById(R.id.tv_teacher);
            tv_viewAllVideo = view.findViewById(R.id.tv_viewAllVideo);
            ll_viewAllVideo = view.findViewById(R.id.ll_viewAllVideo);

            /*img_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.onItemClick(getAdapterPosition());
                }
            });
            tv_viewAllVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.onViewAllVideoClick(getAdapterPosition());
                }
            });*/
            ll_viewAllVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.onViewAllVideoClick(getAdapterPosition());
                }
            });

        }
    }


    public VideoClassAdapter(Context cnt, ArrayList<VideoListModel.Data> videoListModel, ItemClick click) {
        try {
            this.cnt = cnt;
            this.click = click;
            this.videoListModel = videoListModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_class_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            //holder.tv_title.setText(videoListModel.getData().get(position).);
            holder.tv_course.setText(videoListModel.get(position).getCourse_name());
            holder.tv_subject.setText(videoListModel.get(position).getSubject_name());
            holder.tv_teacher.setText(videoListModel.get(position).getTeacher_name());
            //holder.tv_topic.setText(classList.data.get(0).getTopic());
            holder.tv_startDate.setText(videoListModel.get(position).getStatr_data());
            holder.tv_endDate.setText(videoListModel.get(position).getEnd_date());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public int getItemCount() {
        return videoListModel.size();
    }


    public void updateList(ArrayList<VideoListModel.Data> list) {
        videoListModel = list;
        notifyDataSetChanged();
    }

}
