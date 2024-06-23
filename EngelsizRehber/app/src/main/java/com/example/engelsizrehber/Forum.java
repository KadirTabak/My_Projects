package com.example.engelsizrehber;

import com.google.firebase.firestore.DocumentId;

public class Forum {
    @DocumentId
    public String id;
    public String baslik;
    public String yazar;
    public String tarih;
    public String resim;

    public String aciklama;

}
