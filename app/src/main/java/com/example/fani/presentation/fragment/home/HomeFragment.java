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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fani.R;
import com.example.fani.data.model.CategoryModel;
import com.example.fani.data.model.NewProductsModel;
import com.example.fani.data.model.PopularProductsModel;
import com.example.fani.databinding.FragmentHomeBinding;
import com.example.fani.presentation.ShowAllActivity;
import com.example.fani.presentation.adapter.CategoryAdapter;
import com.example.fani.presentation.adapter.NewProductsAdapter;
import com.example.fani.presentation.adapter.PopularProductsAdapter;
import com.example.fani.utils.LogUtil;
import com.example.fani.utils.Utilities;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.zoho.commons.Fonts;
import com.zoho.commons.InitConfig;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

  //  RecyclerView catRecyclerview;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

  //  RecyclerView newProductsRecyclerview;
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

  //  RecyclerView popularProductsRecyclerview;
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    //FireStore
    FirebaseFirestore db;

    private FragmentHomeBinding binding;

    //init ViewModel
    private HomeViewModel homeViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        //define viewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        db = FirebaseFirestore.getInstance();

        InitConfig initConfig = new InitConfig();
        initConfig.setFont(Fonts.REGULAR, "Error");

        // init view when loading data
        binding.sflCategory.startShimmer();
        binding.sflNewProducts.startShimmer();
        binding.sflAllProducts.startShimmer();

        //event click See All Category
        binding.tvCategorySeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });

        //event click See All New Products
        binding.tvNewProductsSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });

        //event click See All Popular Products
        binding.tvPopularSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ShowAllActivity.class);
            startActivity(intent);
        });

        //image slider
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://online.visual-paradigm.com/repository/images/3da74839-096a-40ed-8519-0cf1f2c098d9/facebook-ads-design/orange-home-furniture-sale-facebook-ad.png", "Discount On Furniture", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.discount2, "Discount On Furniture", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://cdn.shopify.com/s/files/1/1993/8987/t/7/assets/MAR---Black-Friday-Ext----Mobile-Banner---Nov-2022.jpg?v=157220347907399318461669477839", "Discount On Furniture", ScaleTypes.FIT));

        binding.imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        categoryAdapter = new CategoryAdapter(getContext());
        //Category
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        binding.rvCategory.setHasFixedSize(true);
        binding.rvCategory.setAdapter(categoryAdapter);

        homeViewModel.getCategories();
        homeViewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), categoryModelList -> {
            categoryAdapter.setCategoryListModels(categoryModelList);
            binding.sflCategory.setVisibility(View.GONE);
            binding.rvCategory.setVisibility(View.VISIBLE);
            categoryAdapter.notifyDataSetChanged();
        });

        //New Products
        binding.rvNewProduct.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        binding.rvNewProduct.setHasFixedSize(true);
        newProductsAdapter = new NewProductsAdapter(getContext());
        binding.rvNewProduct.setAdapter(newProductsAdapter);

        homeViewModel.getNewProduct();
        homeViewModel.getNewProductLiveData().observe(getViewLifecycleOwner(), newProductsModelList -> {
            if (newProductsModelList != null) {
                newProductsAdapter.updateItemsNewProduct(newProductsModelList);
                binding.sflNewProducts.setVisibility(View.GONE);
                binding.rvNewProduct.setVisibility(View.VISIBLE);
            }
        });

        popularProductsAdapter = new PopularProductsAdapter(getContext());
        //Popular Products
        binding.rvPopular.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularProductsModelList = new ArrayList<>();
        binding.rvPopular.setHasFixedSize(true);
        popularProductsAdapter = new PopularProductsAdapter(getContext());
        binding.rvPopular.setAdapter(popularProductsAdapter);

        homeViewModel.getPopularProduct();
        homeViewModel.getPopularProductLiveData().observe(getViewLifecycleOwner(), popularProductsModelList -> {
            if (popularProductsModelList != null) {
                popularProductsAdapter.updateItemPopularProduct(popularProductsModelList);
                binding.sflAllProducts.setVisibility(View.GONE);
                binding.rvPopular.setVisibility(View.VISIBLE);
            }
        });


        /*db.collection("AllProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                            popularProductsModelList.add(popularProductsModel);
                            binding.sflAllProducts.setVisibility(View.GONE);
                            binding.rvPopular.setVisibility(View.VISIBLE);
                            popularProductsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Utilities.showToast(getActivity(), "" + task.getException());
                        LogUtil.e("" + task.getException());
                    }
                });*/

        return binding.getRoot();
    }
}