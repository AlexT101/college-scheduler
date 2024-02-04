package com.example.collegescheduler;

public class Item {
    private String type; //Either "todo", "exam", or "assignment"
    private String title; //Text to be displayed
    private String date; //Either date todo/assignment is due or date the assignment is on
    private String time; //Same as above
    private String course; //Course the todo/exam/assignment is for
    private boolean complete; //True if complete, false otherwise
    private String location; //Location of the exam (do not set or display for todo/assignment)


    //Constructor for todos and assignments
    public Item (String type, String title, String date, String time, String course) {
        this.type = type;
        this.title = title;
        this.date = date;
        this.time = time;
        this.course = course;
        this.complete = false;
        this.location = "";
    }

    //Constructor for exams (additional parameter for setting exam location)
    public Item (String type, String title, String date, String time, String course, String location) {
        this.type = type;
        this.title = title;
        this.date = date;
        this.time = time;
        this.course = course;
        this.complete = false;
        this.location = location;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public boolean getComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
