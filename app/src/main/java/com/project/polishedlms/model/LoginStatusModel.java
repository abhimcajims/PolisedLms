package com.project.polishedlms.model;

public class LoginStatusModel {

    private String message;
    private int status;

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

    public Data getData() {
        return user_data;
    }

    public void setData(Data user_data) {
        this.user_data = user_data;
    }

    private Data user_data;

    public static class Data {
        private String login_status;

        public String getLogin_status() {
            return login_status;
        }

        public void setLogin_status(String login_status) {
            this.login_status = login_status;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        private String device_name;
    }
}
