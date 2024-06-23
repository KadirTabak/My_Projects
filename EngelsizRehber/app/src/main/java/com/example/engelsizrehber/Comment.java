package com.example.engelsizrehber;

import com.google.firebase.firestore.DocumentId;

public class Comment {

    @DocumentId
    public String id;
    public String yazar;
    public String tarih;
    public String aciklama;
}
