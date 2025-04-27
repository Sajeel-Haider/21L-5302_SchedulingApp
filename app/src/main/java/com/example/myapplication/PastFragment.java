package com.example.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class PastFragment extends Fragment {
    private DBHelper db;
    private TaskAdapter adapter;
    private List<Task> tasks;

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup cnt, Bundle bs) {
        return inf.inflate(R.layout.fragment_past, cnt, false);
    }

    @Override
    public void onViewCreated(View v, Bundle bs) {
        db = new DBHelper(getContext());
        RecyclerView rv = v.findViewById(R.id.recyclerViewPast);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        tasks = db.getPastTasks();
        adapter = new TaskAdapter(tasks);
        rv.setAdapter(adapter);
    }
}

