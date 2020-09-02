package com.project.polishedlms.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.project.polishedlms.activity.VideoActivity;
import com.project.polishedlms.model.LoginModel;
import com.project.polishedlms.model.MyErrorModel;
import com.project.polishedlms.model.VideoClassModel;
import com.project.polishedlms.model.VideoListModel;
import com.project.polishedlms.util.MySharedPreference;
import com.project.polishedlms.util.Utility;
import com.project.polishedlms.webServices.APIClient;
import com.project.polishedlms.webServices.OnResponseInterface;
import com.project.polishedlms.webServices.ResponseListner;
import com.project.polishedlms.R;
import com.project.polishedlms.activity.DashboardActivity;
import com.project.polishedlms.activity.VideoAllActivity;
import com.project.polishedlms.adapter.NoDataAdapter;
import com.project.polishedlms.adapter.VideoClassAdapter;
import com.project.polishedlms.model.SubjectClassModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

public class VideoClassFragment extends Fragment implements VideoClassAdapter.ItemClick, OnResponseInterface {

    View view;
    Spinner spr_course, spr_subject;
    private ArrayList<String> subject = new ArrayList<>();
    private ArrayAdapter<String> courseAdapter;
    private ArrayAdapter<String> subjectAdapter;
    private RecyclerView recycler_video_class;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.online_test_fragment, container, false);

        DashboardActivity.setActionBarTitle("Video Class");

        init();

        courseList.add(0, "Select");
        setCourseAdapter(courseList);

        subjectList.add(0, "Select");
        setSubjectAdapter(subjectList);

        getCourseData();

        getVideoClassList();

        return view;
    }

    ProgressDialog progressDialog;


    private void getCourseData() {
        try {
            if (Utility.checkNetwork(getContext())) {
                progressDialog.show();
                String user_data = MySharedPreference.getInstance(getContext()).getUserData();
                loginModel = new Gson().fromJson(user_data, LoginModel.class);
                String course_id = convertStringArrayToString(loginModel.getCourse_id(), ",");

                Call<VideoClassModel> call = APIClient.getInstance().getApiInterface().getCourseData(course_id);
                call.request().url();
                Log.e("MyUrl", call.request().url() + "");
                new ResponseListner(getContext(), this).getResponse(call);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    LoginModel loginModel;

    private void getVideoClassList() {
        try {
            if (Utility.checkNetwork(getContext())) {
                progressDialog.show();
                String user_data = MySharedPreference.getInstance(getContext()).getUserData();
                loginModel = new Gson().fromJson(user_data, LoginModel.class);
                String course_id = convertStringArrayToString(loginModel.getCourse_id(), ",");
                String user_id = loginModel.getUsersigndata().getStudent_id();
                String language_code = loginModel.getUsersigndata().getStudent_language_id();
                Call<VideoListModel> call = APIClient.getInstance().getApiInterface().
                        getVideoClass(course_id, user_id, language_code);
                call.request().url();
                Log.e("MyUrl", call.request().url() + "");
                new ResponseListner(getContext(), this).getResponse(call);
            } else {
                setNoDataAdapter();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private static String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }

    private void getSubjectData(String course_id) {
        try {
            if (Utility.checkNetwork(getContext())) {
                progressDialog.show();

                Call<SubjectClassModel> call = APIClient.getInstance().getApiInterface().getSubjectData(course_id);
                call.request().url();
                Log.e("MyUrl", call.request().url() + "");
                new ResponseListner(getContext(), this).getResponse(call);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void init() {
        spr_course = view.findViewById(R.id.spr_course);
        spr_subject = view.findViewById(R.id.spr_subject);
        recycler_video_class = view.findViewById(R.id.recycler_video_class);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Connecting");
        progressDialog.setCancelable(false);
    }

    private boolean courseSelected = false;

    private String course_id;

    private void setCourseAdapter(ArrayList<String> courseList) {
        try {


            courseAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, courseList);
            courseAdapter.setDropDownViewResource(R.layout.spinner_list_item);
            spr_course.setAdapter(courseAdapter);
            spr_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Utility.showToast(getContext(), courseAdapter.getItem(position));
                    if (position != 0) {
                        courseSelected = true;
                        course_id = /*courseHashMap.get(*/courseAdapter.getItem(position)/*)*/;
                        subject_id = null;
                        //mAdapter.updateList(videoListModel.getData());
                        filterRecycler(course_id, subject_id);
                        getSubjectData(courseHashMap.get(courseAdapter.getItem(position)));
                    } else if (position == 0) {
                        courseSelected = false;
                        subjectSelected = false;
                        spr_subject.setSelection(0);
                        spr_subject.setVisibility(View.GONE);
                        if (videoListModel != null) {
                            mAdapter.updateList(videoListModel.getData());
                            filterList = null;
                        }
                        course_id = null;
                        subject_id = null;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private boolean subjectSelected = false;
    private String subject_id;


    private void setSubjectAdapter(ArrayList<String> subjectList) {
        try {


            subjectAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, subjectList);
            subjectAdapter.setDropDownViewResource(R.layout.spinner_list_item);
            spr_subject.setAdapter(subjectAdapter);
            spr_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        subjectSelected = true;
                        subject_id = /*subjectHashMap.get(*/subjectAdapter.getItem(position)/*)*/;

                        filterRecycler(course_id, subject_id);
                    } else if (position == 0) {
                        subjectSelected = false;
                        subject_id = null;
                        if (course_id != null) {
                            filterRecycler(course_id, subject_id);

                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private ArrayList<VideoListModel.Data> filterList = null;

    private void filterRecycler(String course_id, String subject_id) {
        try {
            filterList = new ArrayList<>();
            for (VideoListModel.Data temp1 : videoListModel.getData()) {
                if (subject_id != null) {
                    if (temp1.getCourse_name().equalsIgnoreCase(course_id) &&
                            temp1.getSubject_name().equalsIgnoreCase(subject_id)) {
                        filterList.add(temp1);
                    }
                } else if (subject_id == null) {
                    if (temp1.getCourse_name().equalsIgnoreCase(course_id)) {
                        filterList.add(temp1);
                    }
                }
            }
            mAdapter.updateList(filterList);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    VideoClassAdapter mAdapter = null;

    private void setRecyclerAdapter(ArrayList<VideoListModel.Data> videoListModel) {
        try {

            //mAdapter = new HomeAdapter(getContext(),this);
            mAdapter = new VideoClassAdapter(getContext(), videoListModel, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
            recycler_video_class.setLayoutManager(linearLayoutManager);
            recycler_video_class.setItemAnimator(new DefaultItemAnimator());
            recycler_video_class.setAdapter(mAdapter);
        } catch (Exception e) {
            Utility.showToast(getContext(), e.getMessage());
        }
    }

    private void setNoDataAdapter() {
        try {

            NoDataAdapter noAdapter = new NoDataAdapter(getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            //RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
            //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
            recycler_video_class.setLayoutManager(linearLayoutManager);
            recycler_video_class.setItemAnimator(new DefaultItemAnimator());

            recycler_video_class.setAdapter(noAdapter);
        } catch (Exception e) {
            Utility.showToast(getContext(), e.getMessage());
        }
    }

    @Override
    public void onItemClick(int pos) {
        try {
            //Intent intent = new Intent(getContext(), VideoViewActivity.class);
            Intent intent = new Intent(getContext(), VideoActivity.class);
            intent.putExtra("video_link",
                    videoListModel.getData().get(pos).getAllVideoData().get(0).get(0).getCourse_subject_video_file_name());
            startActivity(intent);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void onViewAllVideoClick(int pos) {
        try {
            //Intent intent = new Intent(getContext(), VideoViewActivity.class);
            Intent intent = new Intent(getContext(), VideoAllActivity.class);
            if (filterList == null || filterList.size() == 0) {
                intent.putExtra("data", videoListModel.getData().get(pos).getAllVideoData());
                intent.putExtra("course", videoListModel.getData().get(pos).getCourse_name());
                intent.putExtra("subject", videoListModel.getData().get(pos).getSubject_name());
            } else {
                intent.putExtra("data", filterList.get(pos).getAllVideoData());
                intent.putExtra("course", filterList.get(pos).getCourse_name());
                intent.putExtra("subject", filterList.get(pos).getSubject_name());
            }

            startActivity(intent);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private VideoClassModel videoClassModel = new VideoClassModel();
    private ArrayList<VideoClassModel.Data> videoClassModelArrayList = new ArrayList<>();
    private HashMap<String, String> courseHashMap = new HashMap<>();
    private ArrayList<String> courseList = new ArrayList<>();


    private SubjectClassModel subjectClassModel = new SubjectClassModel();
    private ArrayList<SubjectClassModel.Data> subjectClassModelArrayList = new ArrayList<>();
    private HashMap<String, String> subjectHashMap = new HashMap<>();
    private ArrayList<String> subjectList = new ArrayList<>();


    private VideoListModel videoListModel;

    @Override
    public void onApiResponse(Object response) {
        try {
            //progressDialog.dismiss();
            if (response != null) {
                if (response instanceof VideoClassModel) {
                    //progressDialog.dismiss();

                    videoClassModel = (VideoClassModel) response;
                    if (videoClassModel.getStatus() == 1 && videoClassModel.getData() != null) {
                        videoClassModelArrayList = videoClassModel.getData();
                        courseHashMap = new HashMap<>();
                        for (VideoClassModel.Data temp : videoClassModelArrayList) {

                            courseHashMap.put(temp.getSubject_name(), temp.getSubject_id());
                        }
                        courseList = new ArrayList<>();
                        courseList.add(0, "All");
                        courseList.addAll(courseHashMap.keySet());
                        setCourseAdapter(courseList);

                    }
                    Log.d("", "");
                } else if (response instanceof SubjectClassModel) {
                    progressDialog.dismiss();

                    subjectClassModel = (SubjectClassModel) response;
                    if (subjectClassModel.getStatus() == 1 &&
                            subjectClassModel.getData() != null &&
                            subjectClassModel.getData().size() > 0) {
                        subjectClassModelArrayList = subjectClassModel.getData();
                        subjectHashMap = new HashMap<>();
                        for (SubjectClassModel.Data temp : subjectClassModelArrayList) {

                            subjectHashMap.put(temp.getClass_name(), temp.getClass_id());
                        }
                        subjectList = new ArrayList<>();
                        subjectList.add(0, "All");
                        subjectList.addAll(subjectHashMap.keySet());
                        spr_subject.setVisibility(View.VISIBLE);
                        setSubjectAdapter(subjectList);
                    } else if (subjectClassModel.getData() == null || subjectClassModel.getData().size() == 0) {
                        subjectList = new ArrayList<>();
                        subjectList.add(0, "Select");
                        setSubjectAdapter(subjectList);
                    }

                } else if (response instanceof VideoListModel) {
                    progressDialog.dismiss();
                    videoListModel = (VideoListModel) response;
                    if (videoListModel.getData().size() > 0)
                        setRecyclerAdapter(videoListModel.getData());
                    else
                        setNoDataAdapter();
                } else if (response instanceof MyErrorModel) {

                    MyErrorModel myErrorModel = (MyErrorModel) response;
                    if (myErrorModel.getStatus() == 0) {
                        Utility.showToast(getContext(), myErrorModel.getMessage());
                    } else {
                        //DO GENERAL Error Code Specific handling
                    }
                }
            } else {
                progressDialog.dismiss();
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
}
