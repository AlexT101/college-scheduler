package com.example.collegescheduler;

public class AssignmentCard {
    private String title;
    private String course;
    private String date;
    private String time;

    public AssignmentCard(String title, String course,String time) {
        this.title = title;
        this.course = course;
        this.date = date;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

}
