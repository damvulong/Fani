package com.example.fani.presentation;

import static com.example.fani.R.string.message_add_to_favorite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fani.R;
import com.example.fani.data.model.NewProductsModel;
import com.example.fani.data.model.PopularProductsModel;
import com.example.fani.data.model.ShowAllModel;
import com.example.fani.databinding.ActivityDetailedBinding;
import com.example.fani.presentation.main.MainActivity;
import com.example.fani.utils.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    private ActivityDetailedBinding binding;

    int totalQuantity = 1;
    int totalPrice = 0;

    //New Products
    NewProductsModel newProductsModel = null;

    //Popular Products
    PopularProductsModel popularProductsModel = null;

    //See All Products
    ShowAllModel showAllModel = null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    String textImg = "";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        /** setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityDetailedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initUI();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) obj;
        } else if (obj instanceof PopularProductsModel) {
            popularProductsModel = (PopularProductsModel) obj;
        } else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        }

        /*New Products*/
        if (newProductsModel != null) {
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(binding.imgDetail);
            binding.txtNameDetail.setText(newProductsModel.getName());
            binding.txtRating.setText(newProductsModel.getRating());

            /*Rating bar*/
            float numberOfStars = Float.parseFloat(newProductsModel.getRating());
            binding.rbRatingStar.setRating(numberOfStars);

            binding.txtDetailedDesc.setText(newProductsModel.getDescription());
            binding.tvPrice.setText(getString(R.string.title_price_unit, String.valueOf(newProductsModel.getPrice())));

            textImg = newProductsModel.getImg_url();

            totalPrice = newProductsModel.getPrice() * totalQuantity;

            if (newProductsModel.getUrlModelAr() == null) {
                binding.btnVirtual.setVisibility(View.GONE);
            } else {
                binding.btnVirtual.setVisibility(View.VISIBLE);
                binding.btnVirtual.setOnClickListener(view13 -> {
                    Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", newProductsModel.getUrlModelAr())
                                    .appendQueryParameter("mode", "ar_only")
                                    .appendQueryParameter("resizable", "false")
                                    .appendQueryParameter("link", "app://myurl.com")
                                    .appendQueryParameter("title", newProductsModel.getName() + " - $" + newProductsModel.getPrice())
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                });

            }
        }

        /*Popular Products*/
        if (popularProductsModel != null) {
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(binding.imgDetail);
            binding.txtNameDetail.setText(popularProductsModel.getName());
            binding.txtRating.setText(popularProductsModel.getRating());
            /*Rating bar*/
            float numberOfStarsPopular = Float.parseFloat(popularProductsModel.getRating());
            binding.rbRatingStar.setRating(numberOfStarsPopular);
            binding.txtDetailedDesc.setText(popularProductsModel.getDescription());
            binding.tvPrice.setText(getString(R.string.title_price_unit, String.valueOf(popularProductsModel.getPrice())));

            textImg = popularProductsModel.getImg_url();

            totalPrice = popularProductsModel.getPrice() * totalQuantity;

            if (popularProductsModel.getUrlModelAr() == null) {
                binding.btnVirtual.setVisibility(View.GONE);
            } else {
                binding.btnVirtual.setVisibility(View.VISIBLE);
                binding.btnVirtual.setOnClickListener(view13 -> {
                    Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", popularProductsModel.getUrlModelAr())
                                    .appendQueryParameter("mode", "ar_only")
                                    .appendQueryParameter("resizable", "false")
                                    .appendQueryParameter("link", "app://myurl.com")
                                    .appendQueryParameter("title", popularProductsModel.getName() + " - $" + popularProductsModel.getPrice())
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                });

            }
        }

        /*Show All Products*/
        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(binding.imgDetail);
            binding.txtNameDetail.setText(showAllModel.getName());
            binding.txtRating.setText(showAllModel.getRating());
            /*Rating bar*/
            float numberOfStarsAllProducts = Float.parseFloat(showAllModel.getRating());
            binding.rbRatingStar.setRating(numberOfStarsAllProducts);
            binding.txtDetailedDesc.setText(showAllModel.getDescription());
            binding.tvPrice.setText(getString(R.string.title_price_unit, String.valueOf(showAllModel.getPrice())));

            textImg = showAllModel.getImg_url();

            totalPrice = showAllModel.getPrice() * totalQuantity;

            if (showAllModel.getUrlModelAr() == null) {
                binding.btnVirtual.setVisibility(View.GONE);
            } else {
                binding.btnVirtual.setVisibility(View.VISIBLE);
                binding.btnVirtual.setOnClickListener(view13 -> {
                    Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", showAllModel.getUrlModelAr())
                                    .appendQueryParameter("mode", "ar_only")
                                    .appendQueryParameter("resizable", "false")
                                    .appendQueryParameter("link", "app://myurl.com")
                                    .appendQueryParameter("title", showAllModel.getName() + " - $" + showAllModel.getPrice())
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                });

            }
        }

        //Event Add to Cart

        binding.btnAddToCart.setOnClickListener(view12 -> {
            addToCart();
            startActivity(new Intent(DetailedActivity.this, MainActivity.class));
        });

        //Event Add to Favorite
        binding.btnFavorite.setOnClickListener(view2 -> addToFav());

        //Plus item
        binding.btnAddItem.setOnClickListener(view3 -> {
            if (totalQuantity < 10) {
                totalQuantity++;
                binding.txtQuantity.setText(String.valueOf(totalQuantity));

                if (newProductsModel != null) {
                    totalPrice = newProductsModel.getPrice() * totalQuantity;
                    binding.tvPrice.setText(Utilities.currencyUnit(totalPrice));
                }
                if (popularProductsModel != null) {
                    totalPrice = popularProductsModel.getPrice() * totalQuantity;
                    binding.tvPrice.setText(Utilities.currencyUnit(totalPrice));
                }
                if (showAllModel != null) {
                    totalPrice = showAllModel.getPrice() * totalQuantity;
                    binding.tvPrice.setText(Utilities.currencyUnit(totalPrice));
                }
            }
        });

        //Minus item
        binding.btnRemoveItem.setOnClickListener(view1 -> {
            if (totalQuantity > 1) {
                totalQuantity--;
                binding.txtQuantity.setText((String.valueOf(totalQuantity)));

                if (newProductsModel != null) {
                    totalPrice = newProductsModel.getPrice() * totalQuantity;
                    binding.tvPrice.setText(Utilities.currencyUnit(totalPrice));
                }
                if (popularProductsModel != null) {
                    totalPrice = popularProductsModel.getPrice() * totalQuantity;
                    binding.tvPrice.setText(Utilities.currencyUnit(totalPrice));
                }
                if (showAllModel != null) {
                    totalPrice = showAllModel.getPrice() * totalQuantity;
                    binding.tvPrice.setText(Utilities.currencyUnit(totalPrice));
                }
            }
        });
    }

    //Add to Favorite
    private void addToFav() {

        final HashMap<String, Object> favMap = new HashMap<>();

        //TODO put img
        favMap.put("img_url", textImg);
        favMap.put("productName", binding.txtNameDetail.getText().toString());
        favMap.put("productPrice", binding.tvPrice.getText().toString());

        firestore.collection("AddToFav").add(favMap).addOnCompleteListener(task -> {
            Toast.makeText(DetailedActivity.this, message_add_to_favorite, Toast.LENGTH_SHORT).show();
            //TODO
            // finish();
        });
    }

    //Add to Cart
    private void addToCart() {

        final HashMap<String, Object> cartMap = new HashMap<>();

        //TODO put img
        cartMap.put("img_url", textImg);
        cartMap.put("productName", binding.txtNameDetail.getText().toString());
        cartMap.put("productPrice", binding.tvPrice.getText().toString());
        cartMap.put("totalQuantity", totalQuantity);
        cartMap.put("totalPrice", totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(task -> {
                    Toast.makeText(DetailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                    finish();

                });
    }

    private void initUI() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
}
