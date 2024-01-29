package com.example.collegescheduler;

public class ExamCard extends ClassCard {

    private String date;


    public ExamCard(String title, String time, String location, String date) {
        super(title, time, location);
        this.date = date;
    }
}
