package com.example.engelsizrehber;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ForumFragment extends Fragment {

    private ForumAdapter adapter;
    private FloatingActionButton fab1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        fab1 = view.findViewById(R.id.fab1);
        ArrayList<Forum> arrayList = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.frecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new ForumAdapter(view.getContext(), arrayList);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forum")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
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
                            Toast.makeText(view.getContext(), "Hata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddForum.class);
                startActivity(intent);
            }
        });
        return view;
    }
}