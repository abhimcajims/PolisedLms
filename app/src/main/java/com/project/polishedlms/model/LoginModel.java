package com.project.polishedlms.model;


public class LoginModel {

    private String message;
    private int status;
    private String[] course_id;

    public String[] getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String[] course_id) {
        this.course_id = course_id;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    private Data user_data;

    public Data getUsersigndata() {
        return user_data;
    }

    public static class Data {
        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getLogin_status() {
            return login_status;
        }

        public void setLogin_status(String login_status) {
            this.login_status = login_status;
        }

        public String student_id;
        public String student_name;
        public String login_status;

        private String student_other_id;
        private String student_class_id;
        private String student_section_id;
        private String student_school_name;
        private String student_country;
        private String student_state;
        private String student_city;
        private String student_course_id;
        private String student_stream_id;
        private String student_address1;
        private String student_address2;
        private String student_email;
        private String student_username;
        private String student_password;
        private String student_roll;
        private String student_phonecode;
        private String student_phone;
        private String student_dob;
        private String student_gender;
        private String student_address;
        private String student_image;
        private String student_unique;
        private String religion;

        public String getStudent_language_id() {
            return student_language_id;
        }

        public void setStudent_language_id(String student_language_id) {
            this.student_language_id = student_language_id;
        }

        private String student_language_id;
        private String student_status;
        private String student_school_user_id;
    }
}
