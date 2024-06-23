package com.example.muhtarliksisemi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class GuncelleActivity extends AppCompatActivity {
    int id;
    EditText editText1,editText2,editText3,editText4;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guncelle);
        editText1=findViewById(R.id.editText1);
        editText2=findViewById(R.id.editText2);
        editText3=findViewById(R.id.editText3);
        editText4=findViewById(R.id.editText4);

        database = this.openOrCreateDatabase("MuhtarlikListesiDB", MODE_PRIVATE, null);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        if(id==0) {
            return;
        }
        database = this.openOrCreateDatabase("MuhtarlikListesiDB", MODE_PRIVATE, null);
        String sorgu = "SELECT * FROM muhtarlik WHERE id=?";
        Cursor cursor = database.rawQuery(sorgu,new String[]{String.valueOf(id)});
        Muhtarlik muhtarlık = new Muhtarlik();
        while(cursor.moveToNext()) {
            muhtarlık.id=cursor.getInt(0);
            muhtarlık.adsoyad=cursor.getString(1);
            muhtarlık.tc=cursor.getString(2);
            muhtarlık.tel_no=cursor.getString(3);
            muhtarlık.adres=cursor.getString(4);
        }
        editText1.setText(muhtarlık.adsoyad);
        editText2.setText(muhtarlık.tc);
        editText3.setText(muhtarlık.tel_no);
        editText4.setText(muhtarlık.adres);


    }
    public void guncelle(View view) {
        String adsoyad = editText1.getText().toString();
        String tc = editText2.getText().toString();
        String tel_no = editText3.getText().toString();
        String adres = editText4.getText().toString();

        String sorgu="UPDATE muhtarlik SET adsoyad=? ,tc=?,tel_no=?,adres=? WHERE id=?";
        SQLiteStatement durumlar=database.compileStatement(sorgu);
        durumlar.bindString(1,adsoyad);
        durumlar.bindString(2,tc);
        durumlar.bindString(3,tel_no);
        durumlar.bindString(4,adres);
        durumlar.bindLong(5, id);
        durumlar.execute();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}