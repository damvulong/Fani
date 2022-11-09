/*
 * *
 *  * Created by damvulong on 5/5/22, 12:54 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 5/5/22, 12:54 AM
 *
 */


package com.example.fani.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fani.R;
import com.example.fani.data.model.MyFavoriteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyFavoriteAdapter extends RecyclerView.Adapter<MyFavoriteAdapter.ViewHolder>{

    Context context;
    List<MyFavoriteModel> list;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyFavoriteAdapter(Context context, List<MyFavoriteModel> list) {
        this.context = context;
        this.list = list;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_favorite_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyFavoriteAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imgFavorite);
        holder.productName.setText(list.get(position).getProductName());
        holder.productPrice.setText(list.get(position).getProductPrice()+"$");

        holder.deteleFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("AddToFav")
                        .document(list.get(holder.getAdapterPosition()).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    list.remove(list.get(holder.getAdapterPosition()));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFavorite;
        TextView productName;
        TextView productPrice;

        ImageButton deteleFav;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFavorite = itemView.findViewById(R.id.img_fav);
            productName = itemView.findViewById(R.id.product_name_fav);
            productPrice = itemView.findViewById(R.id.price_fav);

            deteleFav = itemView.findViewById(R.id.delete_fav);
        }
    }
}
