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
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fani.data.model.CategoryModel;
import com.example.fani.databinding.CategoryListBinding;
import com.example.fani.presentation.ShowAllActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> categoryModelList;

    public void setCategoryListModels(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    public CategoryAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CategoryListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(categoryModelList.get(position).getImg_url()).into(holder.binding.catImg);
        holder.binding.catName.setText(categoryModelList.get(position).getName());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ShowAllActivity.class);
            intent.putExtra("type", categoryModelList.get(holder.getAdapterPosition()).getType());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CategoryListBinding binding;

        public ViewHolder(CategoryListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
