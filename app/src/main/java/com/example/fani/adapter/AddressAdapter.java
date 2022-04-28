/*
 * *
 *  * Created by damvulong on 4/20/22, 6:11 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/20/22, 6:11 AM
 *
 */

package com.example.fani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fani.R;
import com.example.fani.model.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private RadioButton radiobtnSelected;

    Context context;
    List<AddressModel> addressModelList;
    SelectedAddress selectedAddress;

    public AddressAdapter(Context context, List<AddressModel> addressModelList, SelectedAddress selectedAddress) {
        this.context = context;
        this.addressModelList = addressModelList;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {

        holder.address.setText(addressModelList.get(position).getUserAddress());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (AddressModel address:addressModelList) {
                    address.setSelected(false);
                }
                addressModelList.get(holder.getAdapterPosition()).setSelected(true);

                if (radiobtnSelected!=null) {
                    radiobtnSelected.setChecked(false);
                }
                radiobtnSelected = (RadioButton) view;
                radiobtnSelected.setChecked(true);
                selectedAddress.setAddress(addressModelList.get(holder.getAdapterPosition()).getUserAddress());

            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.tv_add_address);
            radioButton = itemView.findViewById(R.id.select_address);
        }
    }

    public interface SelectedAddress {
        void setAddress(String address);
    }
}
