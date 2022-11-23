/*
 * *
 *  * Created by thaituan on 11/22/22, 11:22 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/22/22, 11:22 AM
 *
 */

package com.example.fshiperapplication;

import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.literal;
import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.rgb;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.concat;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.get;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.gt;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.subtract;
import static com.mapbox.maps.extension.style.expressions.generated.Expression.toNumber;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableKt;

import com.example.fshiperapplication.Utils.DateTimeUtils;
import com.example.fshiperapplication.Utils.LogUtil;
import com.example.fshiperapplication.Utils.PermissionUtils;
import com.example.fshiperapplication.Utils.Utilities;
import com.example.fshiperapplication.databinding.ActivityMainBinding;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.QueriedFeature;
import com.mapbox.maps.RenderedQueryGeometry;
import com.mapbox.maps.RenderedQueryOptions;
import com.mapbox.maps.ScreenBox;
import com.mapbox.maps.ScreenCoordinate;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.StyleContract;
import com.mapbox.maps.extension.style.StyleExtensionImpl;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.image.ImageUtils;
import com.mapbox.maps.extension.style.layers.generated.CircleLayer;
import com.mapbox.maps.extension.style.layers.generated.RasterLayer;
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer;
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor;
import com.mapbox.maps.extension.style.sources.SourceUtils;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
import com.mapbox.maps.extension.style.sources.generated.ImageSource;
import com.mapbox.maps.extension.style.sources.generated.VectorSource;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class MainActivity extends AppCompatActivity implements OnMapClickListener {

    private ActivityMainBinding binding;
    private RxPermissions rxPermissions;
    private CompositeDisposable compositeDisposable;
    private MapboxMap mapboxMap;


    // Constant
    private static final String IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Mapbox_logo_2019.svg/2560px-Mapbox_logo_2019.svg.png";
    private static final String GEOJSON_URL = "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson";
    private static final String SOURCE_ID = "earthquakes";
    private static final String IMAGE_SOURCE_ID = "image";
    private static final String CIRCLE_LAYER_ID = "earthquakeCircle";
    private static final String SYMBOL_LAYER_ID = "earthquakeText";
    private static final String RASTER_LAYER_ID = "raster";
    private static final String IMAGE_9_PATCH_ID = "image9patch";
    private static final String IMAGE_ID = "image";
    private static final Expression MAG_KEY = literal("mag");
    private static final String POLYGON_SOURCE_ID = "polygon";
    private static final List<String> QUERY_LIST = new ArrayList() {
        {
            add(CIRCLE_LAYER_ID);
            add(SYMBOL_LAYER_ID);
        }
    };
    public static final ArrayList<ArrayList<Double>> POINT_LIST = new ArrayList<ArrayList<Double>>() {
        {
            add(new ArrayList<Double>() {
                {
                    add(-35.859375);
                    add(58.44773280389084);
                }
            });
            add(new ArrayList<Double>() {
                {
                    add(-16.171875);
                    add(58.44773280389084);
                }
            });
            add(new ArrayList<Double>() {
                {
                    add(-16.171875);
                    add(54.7246201949245);
                }
            });
            add(new ArrayList<Double>() {
                {
                    add(-35.859375);
                    add(54.7246201949245);
                }
            });
        }
    };

    private static final String FILL_FEATURE_COLLECTION = "{\n" +
            "\"type\": \"FeatureCollection\",\n" +
            "\"features\": [\n" +
            "  {\n" +
            "    \"type\": \"Feature\",\n" +
            "    \"properties\": {},\n" +
            "    \"geometry\": {\n" +
            "      \"type\": \"Polygon\",\n" +
            "      \"coordinates\": [\n" +
            "        [\n" +
            "          [\n" +
            "            -366.85546875,\n" +
            "            18.145851771694467\n" +
            "          ],\n" +
            "          [\n" +
            "            -373.27148437499994,\n" +
            "            12.726084296948196\n" +
            "          ],\n" +
            "          [\n" +
            "            -364.39453125,\n" +
            "            6.577303118123887\n" +
            "          ],\n" +
            "          [\n" +
            "            -366.85546875,\n" +
            "            18.145851771694467\n" +
            "          ]\n" +
            "        ]\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "]\n" +
            "      }";

    public static final ArrayList<String> TEXT_FONT = new ArrayList<String>() {
        {
            add("Open Sans Regular");
            add("Arial Unicode MS Regular");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mapboxMap = binding.mapView.getMapboxMap();

        rxPermissions = new RxPermissions(this);
        compositeDisposable = new CompositeDisposable();

        mapboxMap.loadStyle(createStyle(),this::setUpStyle);



        GesturesUtils.addOnMapClickListener(mapboxMap, this);



    }

    private void setUpStyle(Style style) {
        addImage(style);

        final VectorSource source = (VectorSource) SourceUtils.getSource(style, "composite");
        LogUtil.e("getSource: " + source);
    }



    private void addImage(Style style) {
        final Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_location_on);
        final Bitmap bitmap = DrawableKt.toBitmap(drawable, 64, 64, null);
        ImageUtils.addImage(style, delegate -> delegate.addImage(IMAGE_ID, bitmap));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestAppPermissions()) {
            LogUtil.d("Request permission ok");
        }
    }

    private boolean requestAppPermissions() {
        /**Request location permission*/
        if (!PermissionUtils.isLocationPermission(rxPermissions)) {
            requestAppPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
            return false;
        }
        return true;
    }

    /**
     * Request app permissions
     *
     * @param permissions
     */
    private void requestAppPermissions(String[] permissions) {
        requestPermissions(permissions, 0);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @Override
    public boolean onMapClick(@NonNull Point point) {
        ScreenCoordinate clicked = mapboxMap.pixelForCoordinate(point);
        mapboxMap.queryRenderedFeatures(
                new RenderedQueryGeometry(new ScreenBox(
                        new ScreenCoordinate(clicked.getX() - 50, clicked.getY() - 50),
                        new ScreenCoordinate(clicked.getX() + 50, clicked.getY() + 50)
                )),
                new RenderedQueryOptions(QUERY_LIST, literal(true)), features -> {
                    List<QueriedFeature> featureList = features.getValue();
                    if (featureList != null && !featureList.isEmpty()) {
                        Number time = featureList.get(0).getFeature().getNumberProperty("time");
                        Utilities.showToast(this, DateTimeUtils.getDateTime(time.longValue()));
                    }
                });
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    private StyleContract.StyleExtension createStyle() {
        StyleExtensionImpl.Builder builder = new StyleExtensionImpl.Builder(Style.TRAFFIC_DAY);

        // Add a image source
        builder.addSource(
                new ImageSource.Builder(IMAGE_SOURCE_ID)
                        .coordinates(POINT_LIST)
                        .url(IMAGE_URL)
                        .build()
        );
        // Add a rasterLayer show the image
        RasterLayer rasterLayer = new RasterLayer(RASTER_LAYER_ID, IMAGE_SOURCE_ID);
        rasterLayer.rasterOpacity(0.8);
        builder.addLayer(rasterLayer);
        // Add the earthquake source
        builder.addSource(new GeoJsonSource.Builder(SOURCE_ID)
                .url(GEOJSON_URL)
                .cluster(false)
                .build()
        );

        // Add circleLayer which will show the earthquake locations
        CircleLayer circleLayer = new CircleLayer(CIRCLE_LAYER_ID, SOURCE_ID);
        circleLayer.circleRadius(get(MAG_KEY));
        circleLayer.circleColor(rgb(255.0, 0.0, 0.0));
        circleLayer.circleOpacity(0.3);
        circleLayer.circleStrokeColor(Color.WHITE);
        builder.addLayer(circleLayer);

        // Add symbolLayer show earthquakes those greater than mag 5.0
        SymbolLayer symbolLayer = new SymbolLayer(SYMBOL_LAYER_ID, SOURCE_ID);
        symbolLayer.filter(gt(get(MAG_KEY), literal(5.0)));
        Expression concat = concat(MAG_KEY, Expression.toString(get(MAG_KEY)));
        symbolLayer.textField(concat);
        symbolLayer.textFont(TEXT_FONT);
        symbolLayer.textColor(Color.BLACK);
        symbolLayer.textHaloColor(Color.WHITE);
        symbolLayer.textHaloWidth(1.0);
        symbolLayer.textAnchor(TextAnchor.TOP);
        symbolLayer.textOffset(new ArrayList<Double>() {{
            add(0.0);
            add(1.0);
        }});
        symbolLayer.textSize(10.0);
        symbolLayer.textIgnorePlacement(false);
        symbolLayer.symbolSortKey(subtract(toNumber(get(MAG_KEY))));
        builder.addLayerAtPosition(builder.layerAtPosition(symbolLayer, CIRCLE_LAYER_ID));

        return builder.build();
    }


}