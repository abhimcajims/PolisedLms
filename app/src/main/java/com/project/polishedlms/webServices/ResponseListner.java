package com.project.polishedlms.webServices;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.project.polishedlms.model.MyErrorModel;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseListner {

    private OnResponseInterface onResponseInterface;
    private String message;
    private Context cnt;

    public ResponseListner(Context cnt, OnResponseInterface onResponseInterface) {
        this.cnt = cnt;
        this.onResponseInterface = onResponseInterface;
    }

    /*public ResponseListner(NewsEvent newsEvent) {
    }*/

    public void getResponse(Call call) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    message = response.message();
                    Log.d("TAG", "onResponse: " + message);
                    //if (response.code() == 200)
                    //    onResponseInterface.onApiResponse(response.body());
                    //else onResponseInterface.onApiFailure(message);

                    if (response.code() == 200 && response.body() != null) {
                        onResponseInterface.onApiResponse(response.body());
                    } else if (response.errorBody() != null) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<MyErrorModel>() {
                        }.getType();
                        MyErrorModel myErrorModel = gson.fromJson(response.errorBody().charStream(), type);
                        onResponseInterface.onApiResponse(myErrorModel);
                    } else {
                        onResponseInterface.onApiFailure(message);
                    }
                } catch (JsonIOException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                try {
                    message = t.getMessage();
                    Log.d("TAG", "onFailure: " + message);
                    onResponseInterface.onApiFailure(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
