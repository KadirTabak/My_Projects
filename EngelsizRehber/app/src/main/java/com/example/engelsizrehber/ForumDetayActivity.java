package com.example.engelsizrehber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ForumDetayActivity extends AppCompatActivity {

    TextView txtYazarTarih;
    TextView txtAciklama;
    EditText inputComment;
    TextView txtBaslik;
    String silenYazar;
    ForumAdapter adapter;
    CommentAdapter adapter2;
    ImageView imageView;
    String id;
    FirebaseFirestore db;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageButton deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_detay);

        txtBaslik=findViewById(R.id.DtextView1);
        txtAciklama=findViewById(R.id.DtextView2);
        txtYazarTarih=findViewById(R.id.DtextView3);
        deleteBtn = findViewById(R.id.deleteBtn);
        imageView = findViewById(R.id.DimageView);
        inputComment = findViewById(R.id.inputComment);
        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        inputComment.setHint("Yorumunuzu buraya yazın");


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        inputComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputComment.setHint("Yorumunuzu buraya yazın");
            }
        });


        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("forum").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    txtBaslik.setText(Objects.requireNonNull(document.getData()).get("title").toString());
                    txtAciklama.setText(Objects.requireNonNull(document.getData().get("content")).toString());
                    txtYazarTarih.setText(Objects.requireNonNull(document.getData().get("author")).toString() + " - " + Objects.requireNonNull(document.getData().get("date")).toString());
                    String fotoPath = Objects.requireNonNull(document.getData().get("picture").toString());
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageReference = storage.getReference().child("images").child(fotoPath);
                    storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Hata", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayList<Forum> arrayList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.commentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ForumAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forum")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document:task.getResult()) {
                                Forum haber = new Forum();
                                haber.id = document.getId();
                                haber.baslik = document.getData().get("title").toString();
                                haber.aciklama = document.getData().get("content").toString();
                                haber.tarih = document.getData().get("date").toString();
                                haber.yazar = document.getData().get("author").toString();
                                haber.resim = document.getData().get("picture").toString();
                                arrayList.add(haber);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Hata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        ArrayList<Comment> commentArrayList = new ArrayList<>();
        RecyclerView commentRecyclerView = findViewById(R.id.commentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter2 = new CommentAdapter(getApplicationContext(), commentArrayList);
        commentRecyclerView.setAdapter(adapter2);

        db.collection("forum").document(id).collection("comments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document:task.getResult()) {
                                Comment comment = new Comment();
                                comment.id = document.getId();
                                comment.yazar = Objects.requireNonNull(document.getData().get("author")).toString();
                                comment.aciklama = Objects.requireNonNull(document.getData().get("content")).toString();
                                comment.tarih = Objects.requireNonNull(document.getData().get("date")).toString();
                                commentArrayList.add(comment);
                            }
                            adapter2.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Hata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        db.collection("forum").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (Objects.equals(task.getResult().getString("author"), sharedPreferences.getString("user-name", ""))) {
                        deleteBtn.setVisibility(View.VISIBLE);
                    }else{
                        deleteBtn.setVisibility(View.GONE);
                    }
                }
            }
        });



    }

    public void sendComment(View view) {
        adapter2.notifyDataSetChanged();

        LocalDate bugun = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault());
        String content = inputComment.getText().toString().trim();
        String author = sharedPreferences.getString("user-name", "");
        String date = bugun.format(formatter);

        Map<String, Object> comment = new HashMap<>();
        comment.put("author", author);
        comment.put("content", content);
        comment.put("date", date);


        db.collection("forum").document(id)
                .collection("comments").add(comment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        inputComment.setText("");
                        inputComment.setHint("Gönderildi");
                        imageView.findFocus();
                        adapter2.updateData();
                        adapter2.notifyDataSetChanged();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {

                    }
                });

    }


    public void deleteForum(View view) {
        db.collection("forum").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), NavbarActivity.class);
                    startActivity(intent);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}