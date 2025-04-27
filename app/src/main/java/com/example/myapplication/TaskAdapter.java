package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.VH> {
    private List<Task> tasks;
    private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup p, int v) {
        return new VH(LayoutInflater.from(p.getContext())
                .inflate(R.layout.item_task, p, false));
    }

    @Override
    public void onBindViewHolder(VH h, int pos) {
        Task t = tasks.get(pos);
        h.tvTitle.setText(t.getTitle());
        h.tvDesc.setText(t.getDescription());
        h.tvDateTime.setText(fmt.format(new Date(t.getDatetime())));
    }

    @Override
    public int getItemCount() { return tasks.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvDateTime;
        VH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvDesc = v.findViewById(R.id.tvDesc);
            tvDateTime = v.findViewById(R.id.tvDateTime);
        }
    }
}

