/*
 * *
 *  * Created by damvulong on 4/18/22, 10:33 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/18/22, 6:55 PM
 *
 */

package com.example.fani.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fani.R;
import com.example.fani.model.PopularProductsModel;
import com.example.fani.ui.DetailedActivity;

import java.util.List;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {

    private Context context;
    private List<PopularProductsModel> popularProductsModelList;

    public PopularProductsAdapter(Context context, List<PopularProductsModel> popularProductsModelList) {
        this.context = context;
        this.popularProductsModelList = popularProductsModelList;
    }

    @NonNull
    @Override
    public PopularProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductsAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(popularProductsModelList.get(position).getImg_url()).into(holder.popularImg);
        holder.popularName.setText(popularProductsModelList.get(position).getName());
        holder.popularPrice.setText(String.valueOf(popularProductsModelList.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed", popularProductsModelList.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularProductsModelList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        ImageView popularImg;
        TextView popularName;
        TextView popularPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popularImg = itemView.findViewById(R.id.popular_img);
            popularName = itemView.findViewById(R.id.popular_product_name);
            popularPrice = itemView.findViewById(R.id.popular_price);
        }
    }
}
