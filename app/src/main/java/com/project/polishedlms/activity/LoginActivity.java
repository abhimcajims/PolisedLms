package com.project.polishedlms.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.project.polishedlms.model.LoginModel;
import com.project.polishedlms.model.LogoutModel;
import com.project.polishedlms.model.MyErrorModel;
import com.project.polishedlms.util.MySharedPreference;
import com.project.polishedlms.util.Utility;
import com.project.polishedlms.webServices.APIClient;
import com.project.polishedlms.webServices.OnResponseInterface;
import com.project.polishedlms.webServices.ResponseListner;
import com.project.polishedlms.R;

import java.util.UUID;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnResponseInterface {

    TextView tv_proceed;
    EditText et_password, et_email;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_activity);
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void init() {
        try {
            et_password = findViewById(R.id.et_password);
            et_email = findViewById(R.id.et_email);
            tv_proceed = findViewById(R.id.tv_proceed);
            tv_proceed.setOnClickListener(this);
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Connecting");
            progressDialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_proceed:
                sendLogin();
                break;
            default:
                break;
        }
    }

    private void sendLogin() {
        try {
            if (!et_email.getText().toString().trim().equalsIgnoreCase("")) {
                if (!et_password.getText().toString().trim().equalsIgnoreCase("")) {
                    getLoginData(et_email.getText().toString().trim(), et_password.getText().toString().trim());
                } else {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter user-id", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private String uniqueID;

    private void getLoginData(String email, String password) {
        try {
            if (Utility.checkNetwork(LoginActivity.this)) {
                progressDialog.show();
                uniqueID = UUID.randomUUID().toString();

                Call<LoginModel> call = APIClient.getInstance().getApiInterface()
                        .getLogin(email, password, uniqueID);
                call.request().url();
                Log.e("MyUrl", call.request().url() + "");
                new ResponseListner(getApplicationContext(), this).getResponse(call);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private LoginModel loginModel;

    @Override
    public void onApiResponse(Object response) {
        try {
            progressDialog.dismiss();
            if (response != null) {
                if (response instanceof LoginModel) {
                    loginModel = (LoginModel) response;
                    if (loginModel.getStatus() == 1) {

                        if (loginModel.getUsersigndata().login_status.equalsIgnoreCase("0")) {
                            doLogin();
                        } else if (loginModel.getUsersigndata().login_status.equalsIgnoreCase("1")) {
                            //showLoginDialog(loginModel.getUsersigndata().student_id);
                            showAlertDialog();
                        }

                    } else if (loginModel.getStatus() == 0) {
                        Toast.makeText(this, loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response instanceof MyErrorModel) {

                    MyErrorModel myErrorModel = (MyErrorModel) response;
                    if (myErrorModel.getStatus() == 0) {
                        Utility.showToast(this, myErrorModel.getMessage());
                    } else {
                        //DO GENERAL Error Code Specific handling
                    }
                } else if (response instanceof LogoutModel) {
                    logoutModel = (LogoutModel) response;
                    if (logoutModel.getStatus() == 1 && logoutModel.getLogin_status().equalsIgnoreCase("0")) {
                        //doLogin();
                        sendLogin();
                    } else {
                        Utility.showToast(this, "Some error happened with login");
                    }
                }
            }
        } catch (Exception e) {
            progressDialog.dismiss();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private LogoutModel logoutModel;

    @Override
    public void onApiFailure(String message) {
        try {
            progressDialog.dismiss();
            Utility.showToast(this, message);
        } catch (Exception e) {
            progressDialog.dismiss();

            e.getMessage();
        }
    }

    private void doLogin() {
        try {
            MySharedPreference.getInstance(this).saveUserData(new Gson().toJson(loginModel));
            MySharedPreference.getInstance(this).saveStudentId(loginModel.getUsersigndata().student_id);
            MySharedPreference.getInstance(this).saveStudentName(loginModel.getUsersigndata().student_name);
            MySharedPreference.getInstance(this).saveDeviceId(uniqueID);

            finish();
            //Intent it = new Intent(LoginActivity.this, ClassFragment.class);
            Intent it = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(it);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void showLoginDialog(String student_id) {
        try {
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(LoginActivity.this);

            builder.setMessage("Want to login on this device?");
            builder.setTitle("User already login on another device");
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
                                    setLogout(student_id);

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

    private void showAlertDialog() {
        try {
            ViewGroup viewGroup = findViewById(android.R.id.content);

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);


            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);
            builder.setCancelable(false);

            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();

            alertDialog.show();
            Button btn = dialogView.findViewById(R.id.buttonOk);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //Using this to logout from other devices
    private void setLogout(String user_id) {
        try {

            if (Utility.checkNetwork(LoginActivity.this)) {
                if (!user_id.equalsIgnoreCase("")) {
                    progressDialog.show();

                    Call<LogoutModel> call = APIClient.getInstance().getApiInterface().setLogout(user_id);
                    call.request().url();
                    Log.e("MyUrl", call.request().url() + "");
                    new ResponseListner(LoginActivity.this, this).getResponse(call);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
