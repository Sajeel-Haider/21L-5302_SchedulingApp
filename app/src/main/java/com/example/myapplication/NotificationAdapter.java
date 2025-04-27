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

public class NotificationAdapter
        extends RecyclerView.Adapter<NotificationAdapter.VH> {
    private List<Notification> items;
    private SimpleDateFormat fmt =
            new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public NotificationAdapter(List<Notification> items) {
        this.items = items;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int pos) {
        Notification n = items.get(pos);
        holder.tvMsg.setText(n.getMessage());
        holder.tvTime.setText(fmt.format(new Date(n.getDatetime())));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvMsg, tvTime;
        VH(View v) {
            super(v);
            tvMsg  = v.findViewById(R.id.tvNotificationMessage);
            tvTime = v.findViewById(R.id.tvNotificationTime);
        }
    }
}

