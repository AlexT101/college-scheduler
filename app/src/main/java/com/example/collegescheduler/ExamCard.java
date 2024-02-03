package com.example.collegescheduler;

public class ExamCard {
    private String title;
    private String course;
    private String location;
    private String time;
    private String date;

    public ExamCard(String title, String course,String location, String date, String time) {
        this.title = title;
        this.course = course;
        this.location = location;
        this.date = date;
        this.time = time;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDate() {
        return date;
    }

    public void setRoom(String date) {
        this.date = date;
    }
}
