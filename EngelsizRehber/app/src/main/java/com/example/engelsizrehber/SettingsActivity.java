package com.example.engelsizrehber;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.engelsizrehber.util.ModeTheme;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList; 

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat switchCompat;

    TextView name, email;
    boolean isNightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        name = findViewById(R.id.nameview);
        email = findViewById(R.id.emailview);
        switchCompat = findViewById(R.id.switchCompat);
        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(),MODE_PRIVATE);
        editor = sharedPreferences.edit();
        name.setText(sharedPreferences.getString("user-name", ""));
        email.setText(sharedPreferences.getString("user-email", ""));

        isNightMode = sharedPreferences.getBoolean("nightMode",false);

        if (isNightMode){
            switchCompat.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
        else {
            switchCompat.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        switchCompat.setOnClickListener(v -> {
            themeMode();
        });
    }

    private void themeMode() {
        if (isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("nightMode",false);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("nightMode",true);
        }
        editor.apply();
    }

    public void backAccount(View view){


        Intent intent = new Intent(getApplicationContext(), NavbarActivity.class);
        startActivity(intent);

    }
    public void exitAccount(View view){

        editor.putString("user-email", "");
        editor.putString("user-password", "");
        editor.putString("user-name", "");
        editor.putInt("user-giris",0);

        editor.remove("loginusername");
        editor.commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }
    public void privateAccount(View view){

        Intent intent = new Intent(getApplicationContext(), PrivateActivity.class);
        startActivity(intent);

    }
}