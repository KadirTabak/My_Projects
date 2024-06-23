package com.example.engelsizrehber;

import com.google.firebase.firestore.DocumentId;

public class News {
    @DocumentId
    String id;
    String content;
    String date;
}
