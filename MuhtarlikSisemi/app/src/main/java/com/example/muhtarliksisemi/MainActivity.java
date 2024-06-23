package com.example.muhtarliksisemi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database;
    MuhtarlikAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = this.openOrCreateDatabase("MuhtarlikListesiDB", MODE_PRIVATE, null);

        String tablo = "CREATE TABLE IF NOT EXISTS muhtarlik(id INTEGER PRIMARY KEY, adSoyad TEXT,tc TEXT ,tel_no TEXT, adres TEXT)";
        database.execSQL(tablo);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        ArrayList<Muhtarlik> arrayList = new ArrayList<>();
        String sorgu = "SELECT id,tc,adsoyad FROM muhtarlik";
        Cursor cursor = database.rawQuery(sorgu,null);
        while(cursor.moveToNext()) {
            Muhtarlik yapilacak = new Muhtarlik();
            yapilacak.id = cursor.getInt(0);
            yapilacak.adsoyad = cursor.getString(1);
            arrayList.add(yapilacak);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MuhtarlikAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);
    }

    public void ekle(View view) {
        Intent intent = new Intent(this,EkleActivity.class);
        startActivity(intent);
    }
}


