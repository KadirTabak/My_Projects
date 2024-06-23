package com.example.winorlose1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    TextView textView1,textView2,textView3;
    int [] resimler={R.drawable.tas,R.drawable.kagit,R.drawable.makas};
    MediaPlayer player1=null;
    MediaPlayer player2=null;
    int oyuncuPuan,Sistempuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        imageView1=findViewById(R.id.imgSen);
        imageView2=findViewById(R.id.imgSistem);
        imageView3=findViewById(R.id.btnTaş);
        imageView4=findViewById(R.id.btnKağıt);
        imageView5=findViewById(R.id.btnmakas);
        textView1=findViewById(R.id.puan);
        textView2=findViewById(R.id.Sistem);
        textView3=findViewById(R.id.Kazanan);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oyna(0);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oyna(1);
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oyna(2);
            }
        });



    }
    private void oyna(int OyuncuSecim) {
        textView3.setText("");
        int SistemSecim=(int)(Math.random()*3);
        imageView1.setImageResource(resimler[OyuncuSecim]);
        imageView2.setImageResource(resimler[SistemSecim]);

        if (OyuncuSecim==SistemSecim){
            Toast.makeText(this, "BERABERE!", Toast.LENGTH_SHORT).show();
        }
        else if (OyuncuSecim==0 && SistemSecim==2||OyuncuSecim==1 && SistemSecim==0||OyuncuSecim==2 && SistemSecim==1) {
            oyuncuPuan++;
            Toast.makeText(this, "KAZANDINIZ!", Toast.LENGTH_SHORT).show();
        }
        else {
            Sistempuan++;
            Toast.makeText(this, "KAYBETTİNİZ!", Toast.LENGTH_SHORT).show();
        }
        textView1.setText("SEN:"+oyuncuPuan);
        textView2.setText("SİSTEM:"+Sistempuan);
        if (oyuncuPuan==3){
            textView3.setTextColor(Color.GREEN);
            textView3.setText("Sen Kazandın!");
            oyuncuPuan=0;
            Sistempuan=0;
            player1=MediaPlayer.create(this,R.raw.kazanma);
            player1.start();

        }
        if (Sistempuan==3){
            textView3.setTextColor(Color.RED);
            textView3.setText("Sistem Kazandı!");
            oyuncuPuan=0;
            Sistempuan=0;
            player2=MediaPlayer.create(this,R.raw.kaybetme);
            player2.start();

        }

    }

}