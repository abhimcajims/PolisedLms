package com.project.polishedlms.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.project.polishedlms.model.MyErrorModel;
import com.project.polishedlms.model.WebModel;
import com.project.polishedlms.util.MySharedPreference;
import com.project.polishedlms.util.Utility;
import com.project.polishedlms.webServices.APIClient;
import com.project.polishedlms.webServices.OnResponseInterface;
import com.project.polishedlms.R;
import com.project.polishedlms.activity.DashboardActivity;
import com.project.polishedlms.activity.WebActivity;
import com.project.polishedlms.adapter.ClassAdapter;
import com.project.polishedlms.model.ClassModel;
import com.project.polishedlms.webServices.ResponseListner;

import java.util.ArrayList;

import retrofit2.Call;


public class ClassFragment extends Fragment implements View.OnClickListener, OnResponseInterface, ClassAdapter.ItemClick {
    private RecyclerView recycler_class;
    ProgressDialog progressDialog;

    ClassAdapter mAdapter = null;

    final Handler handler = new Handler();
    final int delay = 60000; //milliseconds

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.activity_class, container, false);


            DashboardActivity.setActionBarTitle("Online Class");

            init(view);
            getData();
            checkPermission();

            handler.postDelayed(new Runnable() {
                public void run() {
                    //do something
                    getData();
                    handler.postDelayed(this, delay);
                }
            }, delay);

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                            Log.d("token", token);

                        }
                    });
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    private void init(View v) {
        try {
            recycler_class = v.findViewById(R.id.recycler_class);

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Connecting");
            progressDialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(ArrayList<ClassModel.Data> data) {
        try {
            //mAdapter = new HomeAdapter(getContext(),this);
            mAdapter = new ClassAdapter(getContext(), data, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
            recycler_class.setLayoutManager(linearLayoutManager);
            recycler_class.setItemAnimator(new DefaultItemAnimator());
            recycler_class.setAdapter(mAdapter);
        } catch (Exception e) {
            Utility.showToast(getContext(), e.getMessage());
        }
    }

    private void getData() {
        try {
            if (Utility.checkNetwork(getContext())) {
                progressDialog.show();

                Call<ClassModel> call = APIClient.getInstance().getApiInterface()
                        .getClassData(MySharedPreference.getInstance(getContext()).getStudentId());
                call.request().url();
                Log.e("MyUrl", call.request().url() + "");
                new ResponseListner(getContext(), this).getResponse(call);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    ClassModel classModel = new ClassModel();
    WebModel webModel = new WebModel();

    @Override
    public void onApiResponse(Object response) {
        try {
            progressDialog.dismiss();
            if (response != null) {
                if (response instanceof ClassModel) {
                    classModel = (ClassModel) response;
                    if(classModel.getStatus()==1 && classModel.getUsersigndata()!=null)
                        setAdapter(classModel.getUsersigndata());
                } else if (response instanceof WebModel) {
                    webModel = (WebModel) response;
                    if (webModel.getIframeData() != null && (webModel.getIframeData().launchurl != null ||
                            !webModel.getIframeData().launchurl.equalsIgnoreCase(""))) {
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("url", webModel.getIframeData().launchurl);
                        startActivity(intent);


                    }
                    Log.d("", "");
                }else if (response instanceof MyErrorModel) {

                    MyErrorModel myErrorModel = (MyErrorModel) response;
                    if (myErrorModel.getStatus() == 0) {
                        Utility.showToast(getContext(), myErrorModel.getMessage());
                    } else {
                        //DO GENERAL Error Code Specific handling
                    }
                }
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            e.getMessage();
        }
    }

    @Override
    public void onApiFailure(String message) {
        try {
            progressDialog.dismiss();

        } catch (Exception e) {
            progressDialog.dismiss();

            e.getMessage();
        }
    }

    int position = -1;

    @Override
    public void onItemClick(int pos) {
        try {
            if (classModel.getUsersigndata().get(pos).status.equalsIgnoreCase("live")) {

                position = pos;
                if (checkPermission()) {
                    getIframeDetails(pos);

                } else {
                    checkPermission();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void getIframeDetails(int pos) {
        try {
            String class_id = classModel.getUsersigndata().get(pos).id;
            String isTeacher = "0";
            String userid = classModel.getUsersigndata().get(pos).user_id;
            String courseName = classModel.getUsersigndata().get(pos).title;
            String lessonName = classModel.getUsersigndata().get(pos).title;
            String userName = MySharedPreference.getInstance(getContext()).getStudentName();

            if (Utility.checkNetwork(getContext())) {
                progressDialog.show();
                Call<WebModel> call = APIClient.getInstance().getApiInterface()
                        .getWebData(class_id, isTeacher, userid, userName, courseName, lessonName);
                call.request().url();
                Log.e("MyUrl", call.request().url() + "");
                new ResponseListner(getContext(), this).getResponse(call);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        try {
            super.onResume();
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;

    protected boolean checkPermission() {
        try {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                    + ContextCompat.checkSelfPermission(
                    getContext(), Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {

                // Do something, when permissions not granted
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        getActivity(), Manifest.permission.CAMERA)
                        || ActivityCompat.shouldShowRequestPermissionRationale(
                        getActivity(), Manifest.permission.RECORD_AUDIO)) {

                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO
                            },
                            MY_PERMISSIONS_REQUEST_CODE
                    );
                } else {
                    // Directly request for required permissions, without explanation
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO
                            },
                            MY_PERMISSIONS_REQUEST_CODE
                    );
                }
            } else {
                // Do something, when permissions are already granted
                //Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_CODE: {
                    // When request is cancelled, the results array are empty
                    if (
                            (grantResults.length > 0) &&
                                    (grantResults[0]
                                            + grantResults[1]
                                            == PackageManager.PERMISSION_GRANTED
                                    )
                    ) {
                        // Permissions are granted
                        //Toast.makeText(this,"Permissions granted.",Toast.LENGTH_SHORT).show();
                        if (position != -1) {
                            getIframeDetails(position);
                        }
                    } else if (grantResults[0] == -1 && grantResults[1] == -1) {
                        Toast.makeText(getContext(), "Both Permissions denied.", Toast.LENGTH_SHORT).show();
                    } else if (grantResults[0] == -1) {
                        // Permissions are denied
                        Toast.makeText(getContext(), "Camera Permissions denied.", Toast.LENGTH_SHORT).show();
                    } else if (grantResults[1] == -1) {
                        Toast.makeText(getContext(), "Mic Permissions denied.", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
