package com.project.polishedlms.webServices;

import com.project.polishedlms.model.LoginModel;
import com.project.polishedlms.model.LoginStatusModel;
import com.project.polishedlms.model.LogoutModel;
import com.project.polishedlms.model.TokenModel;
import com.project.polishedlms.model.VideoClassModel;
import com.project.polishedlms.model.VideoListModel;
import com.project.polishedlms.model.WebModel;
import com.project.polishedlms.model.ClassModel;
import com.project.polishedlms.model.SubjectClassModel;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiInterface {


    //@FormUrlEncoded
    //@POST("auth/signup")
    //Call<SignupModel> getSignUp(@Body JsonObject jsonObject);

    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> getLogin(@Field("username") String username,
                              @Field("password") String password,
                              @Field("device_name") String device_name);

    @FormUrlEncoded
    @POST("onlineclasslist")
    Call<ClassModel> getClassData(@Field("student_id") String username);

    @FormUrlEncoded
    @POST("launchclass")
    Call<WebModel> getWebData(@Field("class_id") String class_id,
                              @Field("isTeacher") String isTeacher,
                              @Field("userId") String userid,
                              @Field("userName") String userName,
                              @Field("courseName") String courseName,
                              @Field("lessonName") String lessonName);

    @FormUrlEncoded
    @POST("coursedata")
    Call<VideoClassModel> getCourseData(@Field("course_id") String course_id);


    @GET("subjectdata/{id}")
    Call<SubjectClassModel> getSubjectData(@Path("id") String course_id);

    @FormUrlEncoded
    @POST("logout")
    Call<LogoutModel> setLogout(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("studentvideolist")
    Call<VideoListModel> getVideoClass(@Field("course_id") String course_id,
                                       @Field("user_id") String user_id,
                                       @Field("language_code") String language_code);


    @FormUrlEncoded
    @POST("splashscreen")
    Call<LoginStatusModel> getLoginStatus(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("notification_student")
    Call<TokenModel> sendToken(@Field("userId") String user_id,
                               @Field("token") String token);

    //@PUT("getkyclist/{user_id}")
    //Call<KycModel> getKyc(@Path("user_id") String user_id);


}
