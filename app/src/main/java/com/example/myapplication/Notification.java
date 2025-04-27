package com.example.myapplication;

public class Notification {
    private int id;
    private String message;
    private long datetime;

    public Notification(int id, String message, long datetime) {
        this.id       = id;
        this.message  = message;
        this.datetime = datetime;
    }

    public int getId()         { return id; }
    public String getMessage(){ return message; }
    public long getDatetime() { return datetime; }
}
