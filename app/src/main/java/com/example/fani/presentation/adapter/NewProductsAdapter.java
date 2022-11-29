/*
 * *
 *  * Created by damvulong on 4/18/22, 10:33 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:33 PM
 *
 */

package com.example.fani.presentation.adapter;

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
import com.example.fani.data.model.CategoryModel;
import com.example.fani.data.model.NewProductsModel;
import com.example.fani.databinding.CategoryListBinding;
import com.example.fani.databinding.NewProductsBinding;
import com.example.fani.presentation.DetailedActivity;
import com.example.fani.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder> {

    private Context context;
    private List<NewProductsModel> newProductsModelList = new ArrayList<>();

    public void updateItemsNewProduct(List<NewProductsModel> items) {
        newProductsModelList.clear();
        newProductsModelList.addAll(items);
        notifyDataSetChanged();
    }

    public NewProductsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NewProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductsAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(newProductsModelList.get(position).getImg_url()).into(holder.newImg);
        holder.newName.setText(newProductsModelList.get(position).getName());
        holder.newPrice.setText(Utilities.currencyUnit(newProductsModelList.get(position).getPrice()));

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra("detailed", newProductsModelList.get(holder.getAdapterPosition()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newProductsModelList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        ImageView newImg;
        TextView newName;
        TextView newPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newImg = itemView.findViewById(R.id.imgNew);
            newName = itemView.findViewById(R.id.tvTitleNewProduct);
            newPrice = itemView.findViewById(R.id.tvNewPrice);
        }
    }
}
