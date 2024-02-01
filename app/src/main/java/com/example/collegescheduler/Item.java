package com.example.collegescheduler;

public class Item {
    private String type; //Either "todo", "exam", or "assignment"
    private String title; //Text to be displayed
    private String date; //Either date todo/assignment is due or date the assignment is on
    private String time; //Same as above
    private String course; //Course the todo/exam/assignment is for
    private boolean complete; //True if complete, false otherwise
    private String location; //Location of the exam (do not set or display for todo/assignment)

}
