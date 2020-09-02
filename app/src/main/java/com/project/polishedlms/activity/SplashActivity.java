package com.project.polishedlms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.project.polishedlms.model.LoginStatusModel;
import com.project.polishedlms.model.MyErrorModel;
import com.project.polishedlms.util.MySharedPreference;
import com.project.polishedlms.util.Utility;
import com.project.polishedlms.webServices.APIClient;
import com.project.polishedlms.webServices.OnResponseInterface;
import com.project.polishedlms.webServices.ResponseListner;
import com.project.polishedlms.R;

import retrofit2.Call;

public class SplashActivity extends AppCompatActivity implements OnResponseInterface {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (MySharedPreference.getInstance(SplashActivity.this).isLogin()) {
                        //Intent it = new Intent(SplashActivity.this, DashboardActivity.class);
                        //startActivity(it);
                        checkLoginStatus();

                    } else {
                        Intent it = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(it);
                    }
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkLoginStatus() {
        try {
            if (Utility.checkNetwork(SplashActivity.this)) {

                Call<LoginStatusModel> call = APIClient.getInstance().getApiInterface()
                        .getLoginStatus(MySharedPreference.getInstance(this).getStudentId());
                call.request().url();
                Log.e("MyUrl", call.request().url() + "");
                new ResponseListner(this, this).getResponse(call);
            } else {
                Utility.showToast(SplashActivity.this, "No Internet");
            }

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onApiResponse(Object response) {
        try {
            if (response != null) {
                if (response instanceof LoginStatusModel) {
                    LoginStatusModel loginStatusModel = (LoginStatusModel) response;
                    if (loginStatusModel.getStatus() == 1) {
                        if (loginStatusModel.getData().getLogin_status().equalsIgnoreCase("1")) {
                            String deviceNameSaved = MySharedPreference.getInstance(this).getDeviceId();
                            String deviceNameComing = loginStatusModel.getData().getDevice_name();
                            if (!deviceNameComing.trim().equalsIgnoreCase(deviceNameSaved.trim())) {
                                //userLogout();
                            } else if (deviceNameComing.trim().equalsIgnoreCase(deviceNameSaved.trim())) {
                                Intent it = new Intent(SplashActivity.this, DashboardActivity.class);
                                startActivity(it);
                            }
                        } else if (loginStatusModel.getData().getLogin_status().equalsIgnoreCase("0")) {
                            userLogout();
                        }
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
            Utility.showToast(SplashActivity.this, message);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void userLogout() {
        try {
            MySharedPreference.getInstance(SplashActivity.this).clearUserData();
            MySharedPreference.getInstance(SplashActivity.this).clearStudentId();
            MySharedPreference.getInstance(SplashActivity.this).clearStudentName();
            MySharedPreference.getInstance(SplashActivity.this).clearDeviceId();
            MySharedPreference.getInstance(SplashActivity.this).clearFirebaseToken();
            finishAffinity();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
