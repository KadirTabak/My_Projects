package com.example.muhtarliksisemi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EkleActivity extends AppCompatActivity {

    EditText editText1,editText2,editText3,editText4;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekle);

        editText1=findViewById(R.id.editText1);
        editText2=findViewById(R.id.editText2);
        editText3=findViewById(R.id.editText3);
        editText4=findViewById(R.id.editText4);
        database=this.openOrCreateDatabase("MuhtarlikListesiDB",MODE_PRIVATE,null);


    }
    public void ekle(View view){
        String adsoyad=editText1.getText().toString();
        String tc=editText2.getText().toString();
        String tel_no=editText3.getText().toString();
        String adres=editText4.getText().toString();

        String sorgu="INSERT INTO muhtarlik (adsoyad,tc,tel_no,adres) VALUES(?,?,?,?)";
        SQLiteStatement durumlar=database.compileStatement(sorgu);
        durumlar.bindString(1,adsoyad);
        durumlar.bindString(2,tc);
        durumlar.bindString(3,tel_no);
        durumlar.bindString(4,adres);
        durumlar.execute();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}