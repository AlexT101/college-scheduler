package com.example.collegescheduler;

public class ClassCard {
    private String title;
    private String time;
    private String repeat;
    private String professor;
    private String section;
    private String location;
    private String room;

    public ClassCard(String title, String time, String location) {
        this.title = title;
        this.time = time;
        this.location = location;
    }
    public ClassCard(String title, String time, String location, String repeat, String professor, String section, String room) {
        this.title = title;
        this.time = time;
        this.location = location;
        this.repeat = repeat;
        this.professor = professor;
        this.section = section;
        this.room = room;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
