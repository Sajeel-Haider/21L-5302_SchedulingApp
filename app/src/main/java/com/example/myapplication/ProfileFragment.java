package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ProfileFragment extends Fragment {
    private EditText etName, etEmail;
    private SwitchCompat switchTheme;
    private Button btnSave;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        // 1) Init views
        etName      = v.findViewById(R.id.etName);
        etEmail     = v.findViewById(R.id.etEmail);
        switchTheme = v.findViewById(R.id.switchTheme);
        btnSave     = v.findViewById(R.id.btnSaveProfile);

        // 2) Load prefs
        prefs = requireContext()
                .getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        etName.setText(prefs.getString("key_name", ""));
        etEmail.setText(prefs.getString("key_email", ""));
        boolean darkMode = prefs.getBoolean("key_dark_mode", false);
        switchTheme.setChecked(darkMode);

        // 3) Toggle theme immediately
        switchTheme.setOnCheckedChangeListener((button, isChecked) -> {
            // 3a) Save pref
            prefs.edit()
                    .putBoolean("key_dark_mode", isChecked)
                    .apply();

            // 3b) Apply
            AppCompatDelegate.setDefaultNightMode(
                    isChecked
                            ? AppCompatDelegate.MODE_NIGHT_YES
                            : AppCompatDelegate.MODE_NIGHT_NO
            );
        });

        // 4) Save name/email
        btnSave.setOnClickListener(x -> {
            String name  = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)) {
                Toast.makeText(getContext(),
                        "Name and email cannot be empty",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            prefs.edit()
                    .putString("key_name", name)
                    .putString("key_email", email)
                    .apply();

            Toast.makeText(getContext(),
                    "Profile saved", Toast.LENGTH_SHORT).show();
        });
    }
}


