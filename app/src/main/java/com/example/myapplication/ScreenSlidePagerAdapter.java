package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
    public ScreenSlidePagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull @Override
    public Fragment createFragment(int pos) {
        switch(pos) {
            case 0: return new ScheduleFragment();
            case 1: return new PastFragment();
            case 2: return new NotificationsFragment();
            case 3: return new ProfileFragment();
            default: return new ScheduleFragment();
        }
    }

    @Override public int getItemCount() { return 4; }
}

