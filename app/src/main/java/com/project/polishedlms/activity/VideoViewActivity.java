package com.project.polishedlms.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebViewClientCompat;

import com.project.polishedlms.util.Utility;
import com.project.polishedlms.R;

public class VideoViewActivity extends AppCompatActivity {

    VideoView video_view;
    WebView web_screen;
    ProgressDialog prDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
            setContentView(R.layout.activity_web);

            web_screen = findViewById(R.id.web_screen);

            web_screen.setWebChromeClient(new WebChromeClient() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    VideoViewActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            request.grant(request.getResources());
                        }
                    });
                }

                @Override
                public void onPermissionRequestCanceled(PermissionRequest request) {
                    Log.d("", "onPermissionRequestCanceled");
                }

                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                }

                @Override
                public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                    super.onReceivedTouchIconUrl(view, url, precomposed);
                }

                @Override
                public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                    return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
                }
            });

            web_screen.setWebChromeClient(new WebChromeClient());
            //web_screen.setWebViewClient(new MyWebViewClient());
            String url = getIntent().getStringExtra("video_link");
            web_screen.getSettings().setJavaScriptEnabled(true);
            web_screen.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            web_screen.getSettings().setDomStorageEnabled(false);
            web_screen.loadUrl(url);

            web_screen.setFocusableInTouchMode(false);
            web_screen.setFocusable(false);
            web_screen.cancelLongPress();

            web_screen.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Utility.showToast(VideoViewActivity.this, "Long press");
                    return true;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyWebViewClient extends WebViewClientCompat {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //view.loadUrl(url);
            try {

                finish();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            try {
                super.onPageStarted(view, url, favicon);
                prDialog = new ProgressDialog(VideoViewActivity.this);
                prDialog.setMessage("Launching ...");
                prDialog.setCancelable(false);
                prDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
                super.onPageFinished(view, url);
                if (prDialog != null) {
                    prDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (web_screen.canGoBack()) {
                        web_screen.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}
