package com.example.muhtarliksisemi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MuhtarlikAdapter extends RecyclerView.Adapter<MuhtarlikAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Muhtarlik> arrayList;

    public MuhtarlikAdapter(Context context, ArrayList<Muhtarlik> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.muhtarlik_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int possition){
        holder.textView.setText(arrayList.get(possition).adsoyad);
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Muhtarlik muhtarlik=arrayList.get(holder.getAdapterPosition());
                Intent intent = new Intent(context,DetayActivity.class);
                intent.putExtra("id",muhtarlik.id);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){
        return  arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }

}

