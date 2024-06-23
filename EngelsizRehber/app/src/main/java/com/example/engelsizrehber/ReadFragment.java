package com.example.engelsizrehber;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.Locale;

public class ReadFragment extends Fragment {

    TextToSpeech tts;
    EditText edtText;
    Button okuButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_read, container, false);
        edtText=view.findViewById(R.id.textEditText);
        okuButton = view.findViewById(R.id.okuButton);

        okuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            tts.setLanguage(new Locale("tr", "TR"));
                            tts.setSpeechRate(1.0f);
                            tts.speak(edtText.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                        }
                    }
                });
            }
        });

        return view;
    }
}