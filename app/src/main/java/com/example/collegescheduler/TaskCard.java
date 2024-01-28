package com.example.collegescheduler;

public class TaskCard {
    private String task;
    private String toDoTitle;

    public TaskCard(String task, String title) {
        this.task = task;
        this.toDoTitle = title;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getToDoTitle() {
        return toDoTitle;
    }

    public void setToDoTitle(String title) {
        this.toDoTitle = title;
    }
}
