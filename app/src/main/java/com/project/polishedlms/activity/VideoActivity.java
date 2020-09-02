package com.project.polishedlms.activity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.polishedlms.util.Constant;
import com.project.polishedlms.util.Utility;
import com.project.polishedlms.R;

public class VideoActivity extends AppCompatActivity {

    private VideoView video_view;

    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;

    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";

    private static String VIDEO_SAMPLE = null;

    MediaController mediaController;
    ProgressDialog progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.video_view);
            video_view = findViewById(R.id.video_view);

            VIDEO_SAMPLE = getIntent().getStringExtra("video_link");

            progressBar = new ProgressDialog(this);
            progressBar.setMessage("Loading Video...");
            progressBar.setCancelable(false);
//            progressBar.show();


            if (savedInstanceState != null) {
                mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
            }

            // Set up the media controller widget and attach it to the video view.
            mediaController = new MediaController(this);
            mediaController.setMediaPlayer(video_view);
            mediaController.setAnchorView(video_view);
            video_view.setMediaController(mediaController);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initializePlayer() {

        try {
            Uri videoUri = getMedia(Constant.VIDEO_BASE_URL + VIDEO_SAMPLE);
            video_view.setVideoURI(videoUri);
            progressBar.show();

            video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    //mediaPlayer.setLooping(true);
                    video_view.setMediaController(mediaController);

                    if (video_state_position != 0) {
                        video_view.seekTo(video_state_position);
                    }
                    video_view.start();
                    progressBar.dismiss();

                }
            });
            video_view.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    progressBar.dismiss();
                    Utility.showToast(VideoActivity.this, "Can't play video");
                    return false;
                }
            });
            video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    releasePlayer();
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        try {
            super.onStart();

            // Load the media each time onStart() is called.
            initializePlayer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // In Android versions less than N (7.0, API 24), onPause() is the
        // end of the visual lifecycle of the app.  Pausing the video here
        // prevents the sound from continuing to play even after the app
        // disappears.
        //
        // This is not a problem for more recent versions of Android because
        // onStop() is now the end of the visual lifecycle, and that is where
        // most of the app teardown should take place.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            video_view.pause();
        }

        video_state_position = video_view.getCurrentPosition();
        Log.d("time", String.valueOf(video_state_position));
    }

    int video_state_position = 0;

    @Override
    protected void onStop() {
        super.onStop();

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer();
    }


    // Release all media-related resources. In a more complicated app this
    // might involve unregistering listeners or releasing audio focus.
    private void releasePlayer() {
        video_view.stopPlayback();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, video_state_position);

    }


    // Get a Uri for the media sample regardless of whether that sample is
    // embedded in the app resources or available on the internet.
    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {
            // Media name is a raw resource embedded in the app.
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);
        }
    }


    @Override
    public void onBackPressed() {
        releasePlayer();
        super.onBackPressed();
        //this.finish();
    }
}
