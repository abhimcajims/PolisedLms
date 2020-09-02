package com.project.polishedlms.model;

import java.util.ArrayList;


public class SubjectClassModel {

    public ArrayList<Data> data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public static class Data {
        private String class_course_id;
        private String class_duration;
        private String class_duration_time;
        private String class_end_date;
        private String class_id;
        private String class_language_id;
        private String class_language_rid;
        private String class_name;
        private String class_price;
        private String class_school_user_id;
        private String class_start_date;
        private String class_status;
        private String class_stream_id;

        public String getClass_course_id() {
            return class_course_id;
        }

        public void setClass_course_id(String class_course_id) {
            this.class_course_id = class_course_id;
        }

        public String getClass_duration() {
            return class_duration;
        }

        public void setClass_duration(String class_duration) {
            this.class_duration = class_duration;
        }

        public String getClass_duration_time() {
            return class_duration_time;
        }

        public void setClass_duration_time(String class_duration_time) {
            this.class_duration_time = class_duration_time;
        }

        public String getClass_end_date() {
            return class_end_date;
        }

        public void setClass_end_date(String class_end_date) {
            this.class_end_date = class_end_date;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getClass_language_id() {
            return class_language_id;
        }

        public void setClass_language_id(String class_language_id) {
            this.class_language_id = class_language_id;
        }

        public String getClass_language_rid() {
            return class_language_rid;
        }

        public void setClass_language_rid(String class_language_rid) {
            this.class_language_rid = class_language_rid;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getClass_price() {
            return class_price;
        }

        public void setClass_price(String class_price) {
            this.class_price = class_price;
        }

        public String getClass_school_user_id() {
            return class_school_user_id;
        }

        public void setClass_school_user_id(String class_school_user_id) {
            this.class_school_user_id = class_school_user_id;
        }

        public String getClass_start_date() {
            return class_start_date;
        }

        public void setClass_start_date(String class_start_date) {
            this.class_start_date = class_start_date;
        }

        public String getClass_status() {
            return class_status;
        }

        public void setClass_status(String class_status) {
            this.class_status = class_status;
        }

        public String getClass_stream_id() {
            return class_stream_id;
        }

        public void setClass_stream_id(String class_stream_id) {
            this.class_stream_id = class_stream_id;
        }
    }
}

