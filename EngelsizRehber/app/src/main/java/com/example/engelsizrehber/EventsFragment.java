package com.example.engelsizrehber;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class EventsFragment extends Fragment {

    EventsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events,container,false);

        ArrayList<Etkinlik> arrayList = new ArrayList<>();
        RecyclerView recyclerView = v.findViewById(R.id.recyclerview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new EventsAdapter(v.getContext(), arrayList);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document:task.getResult()) {
                                Etkinlik event = new Etkinlik();
                                event.title = document.getData().get("title").toString();
                                event.content = document.getData().get("longtext").toString();
                                event.startDate = document.getData().get("startDate").toString();
                                event.finishDate = document.getData().get("finishDate").toString();
                                event.eventLocal = document.getData().get("eventLocal").toString();
                                arrayList.add(event);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(v.getContext(), "Hata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return v;
    }
}