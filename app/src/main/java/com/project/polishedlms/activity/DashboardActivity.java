package com.project.polishedlms.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.project.polishedlms.R;
import com.project.polishedlms.fragment.AssesmentFragment;
import com.project.polishedlms.fragment.ClassFragment;
import com.project.polishedlms.fragment.MessageFragment;
import com.project.polishedlms.fragment.StudyMaterialFragment;
import com.project.polishedlms.fragment.VideoClassFragment;
import com.project.polishedlms.model.LoginStatusModel;
import com.project.polishedlms.model.LogoutModel;
import com.project.polishedlms.model.MyErrorModel;
import com.project.polishedlms.model.TokenModel;
import com.project.polishedlms.util.MySharedPreference;
import com.project.polishedlms.util.Utility;
import com.project.polishedlms.webServices.APIClient;
import com.project.polishedlms.webServices.OnResponseInterface;
import com.project.polishedlms.webServices.ResponseListner;

import retrofit2.Call;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, OnResponseInterface {
    FrameLayout frame_main;
    ImageView img_home;
    ImageView img_logout;
    static TextView tv_header_title;
    BottomNavigationView bottom_navigation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard);
            init();
            setListener();
            if (getIntent().getExtras() != null) {
                String data = getIntent().getStringExtra("title");
                if (data != null & data.contains("studymaterialvideo")) {
                    Log.d("", "");
                    bottom_navigation.setSelectedItemId(R.id.navigation_video_class);
                    replace(new VideoClassFragment());
                } else if (data != null & data.contains("onlineclass")) {
                    Log.d("", "");
                    bottom_navigation.setSelectedItemId(R.id.navigation_online_class);
                    replace(new ClassFragment());
                } else if (data != null & data.contains("assignquiz")) {
                    Log.d("", "");
                    bottom_navigation.setSelectedItemId(R.id.navigation_online_class);
                    replace(new ClassFragment());
                }
            } else if (getIntent().getExtras() == null) {
                replace(new ClassFragment());
            }


            //FirebaseMessaging.getInstance().subscribeToTopic("");
            //String token=FirebaseInstanceId.getInstance().getToken();

            //Getting firebase token for registration.
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String token = instanceIdResult.getToken();
                    Log.d("token", token);
                    Log.d("id", instanceIdResult.getId());
                    Log.d("", "");
                    String storeToken = MySharedPreference.getInstance(DashboardActivity.this).getFirebaseToken();
                    if (!token.equalsIgnoreCase(storeToken)) {
                        MySharedPreference.getInstance(DashboardActivity.this).saveFirebaseToken(token);
                        sendTokenToServer(token);
                    }
                }
            });

            //replace(new ClassFragment());
            bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    if (id == R.id.navigation_online_class) {
                        replace(new ClassFragment());
                        return true;
                    } else if (id == R.id.navigation_video_class) {
                        replace(new VideoClassFragment());
                        return true;
                    } else if (id == R.id.navigation_document) {
                        replace(new StudyMaterialFragment());
                        return true;
                    } else if (id == R.id.navigation_assesment) {
                        replace(new AssesmentFragment());
                        return true;
                    } else if (id == R.id.navigation_message) {
                        replace(new MessageFragment());
                        return true;
                    }
                    return true;
                }
            });
            handler.postDelayed(new Runnable() {
                public void run() {
                    //do something
                    checkLoginStatus();

                    handler.postDelayed(this, delay);
                }
            }, delay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        try {
            super.onNewIntent(intent);
            if (intent != null) {
                String data = intent.getStringExtra("title");
                if (data != null & data.contains("studymaterialvideo")) {
                    Log.d("", "");
                    bottom_navigation.setSelectedItemId(R.id.navigation_video_class);
                    replace(new VideoClassFragment());
                } else if (data != null & data.contains("onlineclass")) {
                    Log.d("", "");
                    bottom_navigation.setSelectedItemId(R.id.navigation_online_class);
                    replace(new ClassFragment());
                } else if (data != null & data.contains("assignquiz")) {
                    Log.d("", "");
                    bottom_navigation.setSelectedItemId(R.id.navigation_online_class);
                    replace(new ClassFragment());
                }
            } else {
                Log.d("", "");
                replace(new ClassFragment());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final Handler handler = new Handler();
    final int delay = 30000; //milliseconds

    public void checkLoginStatus() {
        try {
            if (Utility.checkNetwork(DashboardActivity.this)) {

                Call<LoginStatusModel> call = APIClient.getInstance().getApiInterface()
                        .getLoginStatus(MySharedPreference.getInstance(this).getStudentId());
                call.request().url();
                Log.e("MyUrl", call.request().url() + "");
                new ResponseListner(this, this).getResponse(call);
            }

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void sendTokenToServer(String token) {
        try {
            if (Utility.checkNetwork(DashboardActivity.this)) {

                Call<TokenModel> call = APIClient.getInstance().getApiInterface()
                        .sendToken(MySharedPreference.getInstance(this).getStudentId(), token);
                call.request().url();
                Log.e("MyUrl", call.request().url() + "");
                new ResponseListner(this, this).getResponse(call);
            }

        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void init() {
        try {
            frame_main = findViewById(R.id.frame_main);
            img_home = findViewById(R.id.img_home);
            img_logout = findViewById(R.id.img_logout);
            tv_header_title = findViewById(R.id.tv_header_title);
            bottom_navigation = findViewById(R.id.bottom_navigation);

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Connecting");
            progressDialog.setCancelable(false);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void setListener() {
        try {
            img_home.setOnClickListener(this);
            img_logout.setOnClickListener(this);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.img_home:
                    //replace(new Dashboard());
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new Dashboard()).commit();

                    //getSupportFragmentManager().popBackStackImmediate();
                    //setTitle();
                    break;
                case R.id.img_logout:
                    showLogout();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void replace(Fragment frag) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, frag).commit();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void showLogout() {
        try {
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(DashboardActivity.this);

            builder.setMessage("Do you want to Logout ?");
            builder.setTitle("Alert !");
            builder.setCancelable(false);
            builder
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // When the user click yes button
                                    // then app will close

                                    setLogout();

                                }
                            });


            builder
                    .setNegativeButton(
                            "No",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // If user click no
                                    // then dialog box is canceled.
                                    dialog.cancel();
                                }
                            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    ProgressDialog progressDialog;

    private void setLogout() {
        try {
            String user_id = MySharedPreference.getInstance(this).getStudentId();

            if (Utility.checkNetwork(DashboardActivity.this)) {
                if (!user_id.equalsIgnoreCase("")) {
                    progressDialog.show();

                    Call<LogoutModel> call = APIClient.getInstance().getApiInterface().setLogout(user_id);
                    call.request().url();
                    Log.e("MyUrl", call.request().url() + "");
                    new ResponseListner(DashboardActivity.this, this).getResponse(call);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    void setTitle() {
        tv_header_title.setText("Dashboard");
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            bottom_navigation.setSelectedItemId(R.id.navigation_online_class);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (doubleBackToExitPressedOnce) {
                //super.onBackPressed();
                finishAffinity();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Utility.showToast(this, "Click again to Exit");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public static void setActionBarTitle(String title) {
        tv_header_title.setText(title);
    }

    private LogoutModel logoutModel;

    @Override
    public void onApiResponse(Object response) {
        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            if (response != null) {
                if (response instanceof LogoutModel) {
                    logoutModel = (LogoutModel) response;
                    if (logoutModel.getStatus() == 1 && logoutModel.getLogin_status().equalsIgnoreCase("0")) {
                        userLogout();
                    }
                } else if (response instanceof LoginStatusModel) {
                    LoginStatusModel loginStatusModel = (LoginStatusModel) response;
                    if (loginStatusModel.getStatus() == 1) {
                        if (loginStatusModel.getData().getLogin_status().equalsIgnoreCase("1")) {
                            String deviceNameSaved = MySharedPreference.getInstance(this).getDeviceId();
                            String deviceNameComing = loginStatusModel.getData().getDevice_name();
                            if (!deviceNameComing.trim().equalsIgnoreCase(deviceNameSaved.trim())) {
                                userLogout();
                            }
                        } else if (loginStatusModel.getData().getLogin_status().equalsIgnoreCase("0")) {
                            userLogout();
                        }
                    }
                } else if (response instanceof TokenModel) {
                    TokenModel tokenModel = (TokenModel) response;
                    if (tokenModel.getStatu() == 1) {

                    }

                } else if (response instanceof MyErrorModel) {

                    MyErrorModel myErrorModel = (MyErrorModel) response;
                    if (myErrorModel.getStatus() == 0) {
                        Utility.showToast(this, myErrorModel.getMessage());
                    } else {
                        //DO GENERAL Error Code Specific handling
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onApiFailure(String message) {
        try {
            progressDialog.dismiss();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void userLogout() {
        try {
            MySharedPreference.getInstance(DashboardActivity.this).clearUserData();
            MySharedPreference.getInstance(DashboardActivity.this).clearStudentId();
            MySharedPreference.getInstance(DashboardActivity.this).clearStudentName();
            MySharedPreference.getInstance(DashboardActivity.this).clearDeviceId();
            MySharedPreference.getInstance(DashboardActivity.this).clearFirebaseToken();
            finishAffinity();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
