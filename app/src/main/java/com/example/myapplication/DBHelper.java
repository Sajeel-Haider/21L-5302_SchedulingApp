package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "tasks.db";
    private static final int DB_VER   = 2;

    // Tasks table
    public static final String TABLE_TASKS     = "tasks";
    public static final String COL_ID          = "id";
    public static final String COL_TITLE       = "title";
    public static final String COL_DESC        = "description";
    public static final String COL_DATETIME    = "datetime";
    public static final String COL_STATUS      = "status";

    // Notifications table
    public static final String TABLE_NOTIFS       = "notifications";
    public static final String COL_NOTIF_ID       = "id";
    public static final String COL_NOTIF_MESSAGE  = "message";
    public static final String COL_NOTIF_DATETIME = "datetime";

    public DBHelper(Context c) { super(c, DB_NAME, null, DB_VER); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tasks table
        db.execSQL(
                "CREATE TABLE tasks(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "title TEXT," +
                        "description TEXT," +
                        "datetime INTEGER," +
                        "status INTEGER)"
        );
        // notifications table
        db.execSQL(
                "CREATE TABLE notifications(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "message TEXT," +
                        "datetime INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop both and recreate
        db.execSQL("DROP TABLE IF EXISTS tasks");
        db.execSQL("DROP TABLE IF EXISTS notifications");
        onCreate(db);
    }

    // ----- TASK METHODS (unchanged) -----
    public long addTask(Task t) {
        ContentValues v = new ContentValues();
        v.put(COL_TITLE,    t.getTitle());
        v.put(COL_DESC,     t.getDescription());
        v.put(COL_DATETIME, t.getDatetime());
        v.put(COL_STATUS,   t.getStatus());
        return getWritableDatabase().insert(TABLE_TASKS, null, v);
    }

    public List<Task> getUpcomingTasks() {
        List<Task> out = new ArrayList<>();
        long now = System.currentTimeMillis();
        Cursor c = getReadableDatabase().query(
                TABLE_TASKS, null,
                COL_DATETIME+">?",
                new String[]{ String.valueOf(now) },
                null, null,
                COL_DATETIME+" ASC"
        );
        while(c.moveToNext()) {
            out.add(new Task(
                    c.getInt    (c.getColumnIndexOrThrow(COL_ID)),
                    c.getString (c.getColumnIndexOrThrow(COL_TITLE)),
                    c.getString (c.getColumnIndexOrThrow(COL_DESC)),
                    c.getLong   (c.getColumnIndexOrThrow(COL_DATETIME)),
                    c.getInt    (c.getColumnIndexOrThrow(COL_STATUS))
            ));
        }
        c.close();
        return out;
    }

    public List<Task> getPastTasks() {
        List<Task> out = new ArrayList<>();
        long now = System.currentTimeMillis();
        Cursor c = getReadableDatabase().query(
                TABLE_TASKS, null,
                COL_DATETIME+"<?",
                new String[]{ String.valueOf(now) },
                null, null,
                COL_DATETIME+" DESC"
        );
        while(c.moveToNext()) {
            out.add(new Task(
                    c.getInt    (c.getColumnIndexOrThrow(COL_ID)),
                    c.getString (c.getColumnIndexOrThrow(COL_TITLE)),
                    c.getString (c.getColumnIndexOrThrow(COL_DESC)),
                    c.getLong   (c.getColumnIndexOrThrow(COL_DATETIME)),
                    c.getInt    (c.getColumnIndexOrThrow(COL_STATUS))
            ));
        }
        c.close();
        return out;
    }

    // ----- NOTIFICATION METHODS -----
    public long addNotification(Notification n) {
        ContentValues v = new ContentValues();
        v.put(COL_NOTIF_MESSAGE,  n.getMessage());
        v.put(COL_NOTIF_DATETIME, n.getDatetime());
        return getWritableDatabase().insert(TABLE_NOTIFS, null, v);
    }

    public List<Notification> getAllNotifications() {
        List<Notification> out = new ArrayList<>();
        Cursor c = getReadableDatabase().query(
                TABLE_NOTIFS, null,
                null, null,
                null, null,
                COL_NOTIF_DATETIME+" DESC"
        );
        while(c.moveToNext()) {
            out.add(new Notification(
                    c.getInt (c.getColumnIndexOrThrow(COL_NOTIF_ID)),
                    c.getString(c.getColumnIndexOrThrow(COL_NOTIF_MESSAGE)),
                    c.getLong (c.getColumnIndexOrThrow(COL_NOTIF_DATETIME))
            ));
        }
        c.close();
        return out;
    }
}
