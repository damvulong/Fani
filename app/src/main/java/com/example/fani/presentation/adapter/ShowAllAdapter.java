/*
 * *
 *  * Created by damvulong on 4/20/22, 2:47 AM
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
import com.example.fani.data.model.ShowAllModel;
import com.example.fani.presentation.DetailedActivity;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolder> {

    private Context context;
    private List<ShowAllModel> showAllModelList;

    public ShowAllAdapter(Context context, List<ShowAllModel> showAllModelList) {
        this.context = context;
        this.showAllModelList = showAllModelList;
    }

    @NonNull
    @Override
    public ShowAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_items, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(showAllModelList.get(position).getImg_url()).into(holder.mItemImg);
        holder.mCost.setText("$"+showAllModelList.get(position).getPrice());
        holder.mName.setText(showAllModelList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed", showAllModelList.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return showAllModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mItemImg;
        private TextView mCost;
        private TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mItemImg = itemView.findViewById(R.id.item_img);
            mCost = itemView.findViewById(R.id.item_cost);
            mName = itemView.findViewById(R.id.item_name);
        }
    }
}
