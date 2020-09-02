package com.project.polishedlms.model;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoListModel implements Serializable {

    private int status;
    private String message;
    private ArrayList<Data> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public static class Data implements Serializable {
        private String class_duration;
        private String course_name;
        private String end_date;
        private String statr_data;
        private String subject_name;
        private String teacher_name;

        public String getClass_duration() {
            return class_duration;
        }

        public void setClass_duration(String class_duration) {
            this.class_duration = class_duration;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getStatr_data() {
            return statr_data;
        }

        public void setStatr_data(String statr_data) {
            this.statr_data = statr_data;
        }

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public ArrayList<ArrayList<AllVideoData>> getAllVideoData() {
            return allVideoData;
        }

        public void setAllVideoData(ArrayList<ArrayList<AllVideoData>> allVideoData) {
            this.allVideoData = allVideoData;
        }

        private ArrayList<ArrayList<AllVideoData>> allVideoData;

        public static class AllVideoData implements Serializable {
            private String course_subject_video_file_id;
            private String course_subject_video_file_name;
            private String course_subject_video_file_name_2;
            private String course_subject_video_file_name_order;
            private String course_subject_video_file_student_id;
            private String course_subject_video_file_table_id;
            private String course_subject_video_file_title;
            private String video_type;
            private String youtube_video_link;

            public String getVideo_type() {
                return video_type;
            }

            public void setVideo_type(String video_type) {
                this.video_type = video_type;
            }

            public String getYoutube_video_link() {
                return youtube_video_link;
            }

            public void setYoutube_video_link(String youtube_video_link) {
                this.youtube_video_link = youtube_video_link;
            }

            public String getCourse_subject_video_file_id() {
                return course_subject_video_file_id;
            }

            public void setCourse_subject_video_file_id(String course_subject_video_file_id) {
                this.course_subject_video_file_id = course_subject_video_file_id;
            }

            public String getCourse_subject_video_file_name() {
                return course_subject_video_file_name;
            }

            public void setCourse_subject_video_file_name(String course_subject_video_file_name) {
                this.course_subject_video_file_name = course_subject_video_file_name;
            }

            public String getCourse_subject_video_file_name_2() {
                return course_subject_video_file_name_2;
            }

            public void setCourse_subject_video_file_name_2(String course_subject_video_file_name_2) {
                this.course_subject_video_file_name_2 = course_subject_video_file_name_2;
            }

            public String getCourse_subject_video_file_name_order() {
                return course_subject_video_file_name_order;
            }

            public void setCourse_subject_video_file_name_order(String course_subject_video_file_name_order) {
                this.course_subject_video_file_name_order = course_subject_video_file_name_order;
            }

            public String getCourse_subject_video_file_student_id() {
                return course_subject_video_file_student_id;
            }

            public void setCourse_subject_video_file_student_id(String course_subject_video_file_student_id) {
                this.course_subject_video_file_student_id = course_subject_video_file_student_id;
            }

            public String getCourse_subject_video_file_table_id() {
                return course_subject_video_file_table_id;
            }

            public void setCourse_subject_video_file_table_id(String course_subject_video_file_table_id) {
                this.course_subject_video_file_table_id = course_subject_video_file_table_id;
            }

            public String getCourse_subject_video_file_title() {
                return course_subject_video_file_title;
            }

            public void setCourse_subject_video_file_title(String course_subject_video_file_title) {
                this.course_subject_video_file_title = course_subject_video_file_title;
            }


        }
    }
}
