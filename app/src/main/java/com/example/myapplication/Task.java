package com.example.myapplication;

public class Task {
    private int id;
    private String title;
    private String description;
    private long datetime;
    private int status;

    public Task(int id, String title, String description, long datetime, int status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.status = status;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public long getDatetime() { return datetime; }
    public int getStatus() { return status; }
}

