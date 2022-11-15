/*
 * *
 *  * Created by damvulong on 11/9/22, 2:35 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:33 PM
 *
 */

package com.example.fani.presentation.fragment.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fani.R;
import com.example.fani.presentation.adapter.CategoryAdapter;
import com.example.fani.presentation.adapter.NewProductsAdapter;
import com.example.fani.presentation.adapter.PopularProductsAdapter;
import com.example.fani.data.model.CategoryModel;
import com.example.fani.data.model.NewProductsModel;
import com.example.fani.data.model.PopularProductsModel;
import com.example.fani.presentation.ShowAllActivity;
import com.example.fani.utils.LogUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.zoho.commons.Fonts;
import com.zoho.commons.InitConfig;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    TextView catShowAll;
    TextView newShowAll;
    TextView popShowAll;

    RecyclerView catRecyclerview;
    //Category recyclerview
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    RecyclerView newProductsRecyclerview;
    //New Products recyclerview
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    RecyclerView popularProductsRecyclerview;
    //Popular Products recyclerview
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    //FireStore
    FirebaseFirestore db;

    private ShimmerFrameLayout mCategory;
    private ShimmerFrameLayout mAllProducts;
    private ShimmerFrameLayout mNewProducts;

    //init ViewModel
    private HomeViewModel homeViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //define viewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        catRecyclerview = root.findViewById(R.id.rcv_category);
        newProductsRecyclerview = root.findViewById(R.id.rcv_new_product);
        popularProductsRecyclerview = root.findViewById(R.id.rcv_popular);

        catShowAll = root.findViewById(R.id.category_see_all);
        newShowAll = root.findViewById(R.id.newProducts_see_all);
        popShowAll = root.findViewById(R.id.popular_see_all);

        db = FirebaseFirestore.getInstance();

        mCategory = root.findViewById(R.id.sflCategory);
        mAllProducts = root.findViewById(R.id.sflAllProducts);
        mNewProducts = root.findViewById(R.id.sflNewProducts);

        InitConfig initConfig = new InitConfig();
        initConfig.setFont(Fonts.REGULAR, "Error");

        // init view when loading data
        mCategory.startShimmer();
        mAllProducts.startShimmer();
        mNewProducts.startShimmer();


        //event click See All Category
        catShowAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });

        //event click See All New Products
        newShowAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });

        //event click See All Popular Products
        popShowAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });

        //image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.discount1, "Discount On Furniture", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.discount2, "Discount On Furniture", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.discount3, "Discount On Furniture", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        categoryAdapter = new CategoryAdapter(getContext());

        //Category
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        catRecyclerview.setHasFixedSize(true);
        catRecyclerview.setAdapter(categoryAdapter);

        homeViewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categoryModelList) {
                categoryAdapter.setCategoryListModels(categoryModelList);
                mCategory.setVisibility(View.GONE);
                catRecyclerview.setVisibility(View.VISIBLE);
                categoryAdapter.notifyDataSetChanged();
            }
        });

        //New Products
        newProductsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getActivity(), newProductsModelList);
        newProductsRecyclerview.setAdapter(newProductsAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                            newProductsModelList.add(newProductsModel);
                            mNewProducts.setVisibility(View.GONE);
                            newProductsRecyclerview.setVisibility(View.VISIBLE);
                            newProductsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        LogUtil.e("" + task.getException());
                    }
                });

        //Popular Products
        popularProductsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getActivity(), popularProductsModelList);
        popularProductsRecyclerview.setAdapter(popularProductsAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                            popularProductsModelList.add(popularProductsModel);
                            mAllProducts.setVisibility(View.GONE);
                            popularProductsRecyclerview.setVisibility(View.VISIBLE);
                            popularProductsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        LogUtil.e("" + task.getException());
                    }
                });
        return root;
    }
}