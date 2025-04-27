package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ScheduleFragment extends Fragment {
    private DBHelper db;
    private TaskAdapter adapter;
    private List<Task> tasks;

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup cnt, Bundle bs) {
        return inf.inflate(R.layout.fragment_schedule, cnt, false);
    }

    @Override
    public void onViewCreated(View v, Bundle bs) {
        db = new DBHelper(getContext());
        RecyclerView rv = v.findViewById(R.id.recyclerViewTasks);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        tasks = db.getUpcomingTasks();
        adapter = new TaskAdapter(tasks);
        rv.setAdapter(adapter);

        FloatingActionButton fab = v.findViewById(R.id.fabAddTask);
        fab.setOnClickListener(x -> showAddDialog());
    }

    private void showAddDialog() {
        View d = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_task, null);
        EditText etT = d.findViewById(R.id.etTitle);
        EditText etD = d.findViewById(R.id.etDescription);
        TextView tvDate = d.findViewById(R.id.tvDate);
        TextView tvTime = d.findViewById(R.id.tvTime);
        Calendar cal = Calendar.getInstance();

        tvDate.setOnClickListener(x -> {
            new DatePickerDialog(getContext(),
                    (p,y,mo,day) -> {
                        cal.set(Calendar.YEAR,y);
                        cal.set(Calendar.MONTH,mo);
                        cal.set(Calendar.DAY_OF_MONTH,day);
                        tvDate.setText(String.format(Locale.getDefault(),
                                "%02d/%02d/%04d", day, mo+1, y));
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        tvTime.setOnClickListener(x -> {
            new TimePickerDialog(getContext(),
                    (p,h,m) -> {
                        cal.set(Calendar.HOUR_OF_DAY,h);
                        cal.set(Calendar.MINUTE,m);
                        tvTime.setText(String.format(Locale.getDefault(),
                                "%02d:%02d", h, m));
                    },
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
            ).show();
        });

        new AlertDialog.Builder(requireContext())
                .setView(d)
                .setTitle("Add New Task")
                .setPositiveButton("Save", (dlg, w) -> {
                    String title = etT.getText().toString().trim();
                    String desc = etD.getText().toString().trim();
                    long dt = cal.getTimeInMillis();
                    db.addTask(new Task(0, title, desc, dt, 0));
                    tasks.clear();
                    tasks.addAll(db.getUpcomingTasks());
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
