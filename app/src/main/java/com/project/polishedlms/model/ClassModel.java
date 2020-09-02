package com.project.polishedlms.model;

import java.util.ArrayList;

public class ClassModel {

    private String message;
    private int statu;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return statu;
    }

    private ArrayList<Data> user_data;

    public ArrayList<Data> getUsersigndata() {
        return user_data;
    }

    public static class Data {
        public String title;
        public String start_time;
        public String end_time;
        public String status;
        public String user_id;
        public String date;
        public String ispaid;
        public String duration;
        public String id;
        public String courseName;
        public String subject_name;
    }
}
