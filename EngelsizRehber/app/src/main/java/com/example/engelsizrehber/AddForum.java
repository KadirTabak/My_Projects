package com.example.engelsizrehber;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class AddForum extends AppCompatActivity {

    Uri imageUri;
    ImageView imgFoto;
    EditText baslikEditText, aciklamaEditText;
    FirebaseDatabase db;
    FirebaseFirestore firestore;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);
        imgFoto = findViewById(R.id.imgFoto);
        baslikEditText = findViewById(R.id.baslikEditText);
        aciklamaEditText = findViewById(R.id.aciklamaEditText);
        db = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    ActivityResultLauncher<Intent> galeriIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        imageUri = result.getData().getData();
                        imgFoto.setImageURI(imageUri);
                    }
                }
            });
    public void addImage(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        galeriIntent.launch(intent);
    }

    public void ekle(View view) {


        LocalDate bugun = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault());
        String title = baslikEditText.getText().toString().trim();
        String content = aciklamaEditText.getText().toString().trim();
        String author = sharedPreferences.getString("user-name", "");
        String date = bugun.format(formatter);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String fotoAdi = UUID.randomUUID().toString();
        StorageReference storageReference = storage.getReference();
        StorageReference fotoRef = storageReference.child("images/"+fotoAdi);


        Map<String, Object> user = new HashMap<>();
        user.put("title", title);
        user.put("content", content);
        user.put("author", author);
        user.put("date", date);
        user.put("picture", fotoAdi);


        fotoRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (!title.isEmpty() || !content.isEmpty()) {

                    firestore.collection("forum")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent = new Intent(getApplicationContext(), NavbarActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                } else {
                    Snackbar.make(view, "Lütfen Tüm Alanları Doldurun", Snackbar.LENGTH_SHORT).show();
                }

            }
        });


    }
}