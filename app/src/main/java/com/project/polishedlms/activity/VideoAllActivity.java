package com.project.polishedlms.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.polishedlms.model.VideoListModel;
import com.project.polishedlms.util.Utility;
import com.project.polishedlms.R;
import com.project.polishedlms.adapter.AllVideoListAdapter;
import com.project.polishedlms.adapter.NoDataAdapter;

import java.util.ArrayList;

public class VideoAllActivity extends AppCompatActivity implements AllVideoListAdapter.ItemClick {

    ArrayList<ArrayList<VideoListModel.Data.AllVideoData>> videoListModel;
    RecyclerView recycler_allVideo;
    private String course, subject;
    private TextView tv_header_title;
    private ImageView img_home;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_all_video);
            recycler_allVideo = findViewById(R.id.recycler_allVideo);
            tv_header_title = findViewById(R.id.tv_header_title);
            img_home = findViewById(R.id.img_home);

            videoListModel = (ArrayList<ArrayList<VideoListModel.Data.AllVideoData>>)
                    getIntent().getSerializableExtra("data");

            course = getIntent().getStringExtra("course");
            subject = getIntent().getStringExtra("subject");

            tv_header_title.setText(course + " - " + subject);
            if (videoListModel.size() > 0) {
                collectAllData();
                setRecyclerAdapter();
            } else {
                setNoDataAdapter();
            }
            img_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VideoAllActivity.this.finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> video = new ArrayList<>();
    private ArrayList<String> video_type = new ArrayList<>();

    private void collectAllData() {
        try {
            for (ArrayList<VideoListModel.Data.AllVideoData> temp1 : videoListModel) {
                for (VideoListModel.Data.AllVideoData temp2 : temp1) {
                    if (temp2.getVideo_type().equalsIgnoreCase("0")) {
                        video.add(temp2.getCourse_subject_video_file_name());
                        video_type.add(temp2.getVideo_type());
                    } else if (temp2.getVideo_type().equalsIgnoreCase("1")) {
                        video.add(temp2.getYoutube_video_link());
                        video_type.add(temp2.getVideo_type());
                    }
                    title.add(temp2.getCourse_subject_video_file_title());
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private AllVideoListAdapter mAdapter;

    private void setRecyclerAdapter() {
        try {
            mAdapter = new AllVideoListAdapter(this, video, title, this);
            //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
            //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
            recycler_allVideo.setLayoutManager(linearLayoutManager);
            recycler_allVideo.setItemAnimator(new DefaultItemAnimator());

            recycler_allVideo.setAdapter(mAdapter);
        } catch (Exception e) {
            Utility.showToast(this, e.getMessage());
        }
    }

    private void setNoDataAdapter() {
        try {

            NoDataAdapter noAdapter = new NoDataAdapter(this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            //RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
            //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
            recycler_allVideo.setLayoutManager(linearLayoutManager);
            recycler_allVideo.setItemAnimator(new DefaultItemAnimator());

            recycler_allVideo.setAdapter(noAdapter);
        } catch (Exception e) {
            Utility.showToast(this, e.getMessage());
        }
    }

    @Override
    public void onItemClick(int pos) {
        try {
            if (video_type.get(pos).equalsIgnoreCase("0")) {
                Intent intent = new Intent(this, VideoActivity.class);
                intent.putExtra("video_link", video.get(pos));
                startActivity(intent);
            } else if (video_type.get(pos).equalsIgnoreCase("1")) {
                //Intent intent = new Intent(this, VideoViewActivity.class);
                //intent.putExtra("video_link", video.get(pos));
                //startActivity(intent);
                watchYoutubeVideo(video.get(pos));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void watchYoutubeVideo(String id) {
        Intent webIntent = null;
        try {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
