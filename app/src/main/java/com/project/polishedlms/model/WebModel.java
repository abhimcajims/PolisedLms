package com.project.polishedlms.model;


public class WebModel {

    private String message;
    private int statu;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return statu;
    }

    public Data iframeurl;

    public Data getIframeData() {
        return iframeurl;
    }

    public static class Data {
        public String status;
        public String method;
        public String class_id;
        public String launchurl;
        public String encryptedlaunchurl;
    }
}

