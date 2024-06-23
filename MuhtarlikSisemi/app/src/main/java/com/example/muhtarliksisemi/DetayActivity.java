package com.example.muhtarliksisemi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetayActivity extends AppCompatActivity {
    SQLiteDatabase database;
    int id;
    TextView textView1,textView2,textView3,textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        Intent intent= getIntent();
        id=intent.getIntExtra("id",0);
        if (id==0){
            return;
        }
        database=this.openOrCreateDatabase("MuhtarlikListesiDB",MODE_PRIVATE,null);
        String sorgu="SELECT * FROM muhtarlik WHERE id=?";
        Cursor cursor=database.rawQuery(sorgu,new String[]{String.valueOf(id)});
        Muhtarlik muhtarlık= new Muhtarlik();
        while (cursor.moveToNext()){
            muhtarlık.id=cursor.getInt(0);
            muhtarlık.adsoyad=cursor.getString(1);
            muhtarlık.tc=cursor.getString(2);
            muhtarlık.tel_no=cursor.getString(3);
            muhtarlık.adres=cursor.getString(4);
        }
        textView1.setText(muhtarlık.adsoyad);
        textView2.setText(muhtarlık.tc);
        textView3.setText(muhtarlık.tel_no);
        textView4.setText(muhtarlık.adres);
    }
    public void sil(View view){
        String sorgu="DELETE FROM muhtarlik WHERE id=?";
        SQLiteStatement durumlar=database.compileStatement(sorgu);
        durumlar.bindLong(1,id);
        durumlar.execute();

        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void guncelle(View view){
        Intent intent = new Intent(this,GuncelleActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}