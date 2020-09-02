package com.project.polishedlms.model;

import java.util.ArrayList;

public class VideoClassModel {

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
        private String subject_id;
        private String subject_name;
        private String subject_duration;
        private String subject_duration_time;
        private String subject_end_date;
        private String subject_feature;
        private String subject_language_id;
        private String subject_language_rid;
        private String subject_school_user_id;
        private String subject_start_date;
        private String subject_status;
        private String subject_image;

        public String getSubject_image() {
            return subject_image;
        }

        public void setSubject_image(String subject_image) {
            this.subject_image = subject_image;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }

        public String getSubject_duration() {
            return subject_duration;
        }

        public void setSubject_duration(String subject_duration) {
            this.subject_duration = subject_duration;
        }

        public String getSubject_duration_time() {
            return subject_duration_time;
        }

        public void setSubject_duration_time(String subject_duration_time) {
            this.subject_duration_time = subject_duration_time;
        }

        public String getSubject_end_date() {
            return subject_end_date;
        }

        public void setSubject_end_date(String subject_end_date) {
            this.subject_end_date = subject_end_date;
        }

        public String getSubject_feature() {
            return subject_feature;
        }

        public void setSubject_feature(String subject_feature) {
            this.subject_feature = subject_feature;
        }

        public String getSubject_language_id() {
            return subject_language_id;
        }

        public void setSubject_language_id(String subject_language_id) {
            this.subject_language_id = subject_language_id;
        }

        public String getSubject_language_rid() {
            return subject_language_rid;
        }

        public void setSubject_language_rid(String subject_language_rid) {
            this.subject_language_rid = subject_language_rid;
        }

        public String getSubject_school_user_id() {
            return subject_school_user_id;
        }

        public void setSubject_school_user_id(String subject_school_user_id) {
            this.subject_school_user_id = subject_school_user_id;
        }

        public String getSubject_start_date() {
            return subject_start_date;
        }

        public void setSubject_start_date(String subject_start_date) {
            this.subject_start_date = subject_start_date;
        }

        public String getSubject_status() {
            return subject_status;
        }

        public void setSubject_status(String subject_status) {
            this.subject_status = subject_status;
        }




    }
}
