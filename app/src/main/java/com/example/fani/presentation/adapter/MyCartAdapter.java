/*
 * *
 *  * Created by damvulong on 4/20/22, 5:17 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/20/22, 5:17 AM
 *
 */

package com.example.fani.presentation.adapter;

import android.content.Context;
import android.util.Log;
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
import com.example.fani.data.model.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList = new ArrayList<>();

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public void updateItemsCartListModel(List<MyCartModel> items) {
        cartModelList.clear();
        cartModelList.addAll(items);
        notifyDataSetChanged();
       // this.cartModelList = cartModelList;
    }

    public MyCartAdapter(Context context) {
        this.context = context;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(cartModelList.get(position).getImg_url()).into(holder.imgCart);
        holder.name.setText(cartModelList.get(position).getProductName());
        Log.e("name", cartModelList.get(position).getProductName());

        holder.quantityCart.setText(String.valueOf(cartModelList.get(position).getTotalQuantity()));
        Log.e("quantity", String.valueOf(cartModelList.get(position).getTotalQuantity()));

        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));
        holder.priceCart.setText(cartModelList.get(position).getProductPrice());

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(cartModelList.get(holder.getAdapterPosition()).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    cartModelList.remove(cartModelList.get(holder.getAdapterPosition()));
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
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCart;
        TextView name;
        TextView totalPrice;
        TextView quantityCart;
        TextView priceCart;
        ImageButton deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCart = itemView.findViewById(R.id.img_cart);
            name = itemView.findViewById(R.id.product_name_cart);
            quantityCart = itemView.findViewById(R.id.quantity_cart);
            totalPrice = itemView.findViewById(R.id.totalPrice_cart);
            priceCart = itemView.findViewById(R.id.price_cart);
            deleteItem = itemView.findViewById(R.id.delete_item);

        }
    }
}
