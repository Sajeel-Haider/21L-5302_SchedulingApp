package com.example.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.Arrays;
import java.util.List;

public class NotificationsFragment extends Fragment {
    private DBHelper db;
    private NotificationAdapter adapter;
    private List<Notification> items;

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup cnt, Bundle bs) {
        return inf.inflate(R.layout.fragment_notifications, cnt, false);
    }

    @Override
    public void onViewCreated(View v, Bundle bs) {
        db = new DBHelper(getContext());

        // 1) If empty, seed some dummy notifications
        items = db.getAllNotifications();
        if (items.isEmpty()) {
            List<String> dummy = Arrays.asList(
                    "Welcome back! 🎉",
                    "Don't forget to check your tasks.",
                    "New update available."
            );
            long now = System.currentTimeMillis();
            for (int i = 0; i < dummy.size(); i++) {
                // spaced 1 minute apart
                db.addNotification(new Notification(
                        0, dummy.get(i), now - i * 60_000L
                ));
            }
            items = db.getAllNotifications();
        }

        // 2) Setup RecyclerView
        RecyclerView rv = v.findViewById(R.id.recyclerViewNotifs);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotificationAdapter(items);
        rv.setAdapter(adapter);
    }
}
