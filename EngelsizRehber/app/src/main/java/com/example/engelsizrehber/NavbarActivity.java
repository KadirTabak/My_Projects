package com.example.engelsizrehber;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.engelsizrehber.databinding.ActivityNavbarBinding;
import com.example.engelsizrehber.util.ModeTheme;
import com.google.android.material.snackbar.Snackbar;

public class NavbarActivity extends ModeTheme {

    ActivityNavbarBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);
        binding = ActivityNavbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbar = findViewById(R.id.toolbar);

        if (intent.getBooleanExtra("snack", false)) {
            Snackbar.make(findViewById(android.R.id.content), intent.getStringExtra("message"), Snackbar.LENGTH_SHORT).show();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        replaceFragment(new EventsFragment());
        binding.bottomNavigationView.setBackground(null);


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.events){
                replaceFragment(new EventsFragment());
                toolbar.setVisibility(View.VISIBLE);
            }
            else if (itemId == R.id.news){
                replaceFragment(new NewsFragment());
                toolbar.setVisibility(View.VISIBLE);
            }
            else if (itemId == R.id.forum){
                replaceFragment(new ForumFragment());
                toolbar.setVisibility(View.VISIBLE);
            }
            else if (itemId == R.id.read){
                replaceFragment(new ReadFragment());
                toolbar.setVisibility(View.VISIBLE);
            }
            else if (itemId == R.id.where){
                replaceFragment(new WhereFragment());
                toolbar.setVisibility(View.VISIBLE);

            }
            return true;
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settings){
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void  replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }


}