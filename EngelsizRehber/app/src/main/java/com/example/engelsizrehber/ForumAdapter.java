package com.example.engelsizrehber;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Forum> arrayList;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public ForumAdapter(Context context, ArrayList<Forum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forum_item,parent,false);
        sharedPreferences = view.getContext().getSharedPreferences(view.getContext().getPackageName(),MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtBaslik.setText(arrayList.get(position).baslik);
        holder.txtYazar.setText(arrayList.get(position).yazar + " tarafından yazıldı");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("images").child(arrayList.get(position).resim);
        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imageView.setImageBitmap(bitmap);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Forum forum = arrayList.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, ForumDetayActivity.class);
                intent.putExtra("id",forum.id);
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (arrayList.get(position).yazar.equals("")) {

                }
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Uyarı");
                    builder.setMessage("Bu Yazıyı Şikayet Etmek mi İstiyorsunuz?");
                    builder.setNegativeButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(context, "Şikayetiniz İletildi", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setPositiveButton("Hayır", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                    return true;
            }
        });
    }

    public void deleteForum(){




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBaslik, txtYazar;
        CardView cardView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.ForumCardView);
            txtBaslik = itemView.findViewById(R.id.baslik);
            txtYazar = itemView.findViewById(R.id.yazar);
            imageView = itemView.findViewById(R.id.imgResim);
        }
    }

}