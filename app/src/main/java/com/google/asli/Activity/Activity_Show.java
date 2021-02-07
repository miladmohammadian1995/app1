package com.google.asli.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.asli.Adapter.AdapterLike;
import com.google.asli.Class.DbSqlite;


import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.put;
import com.google.asli.Model.ModelFav;
import com.google.asli.Model.ModelLike;

import com.google.asli.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Show extends AppCompatActivity {

    TextView textlike;
    RecyclerView recyLike;
    AdapterLike adapterLike;
    List<ModelLike> modelLikeList = new ArrayList<>();
    private SliderLayout sliderLayout;
    private AppBarLayout appBarLayout;
    private ImageView imageBack, imageBasket, imageFavorit;
    ArrayList<String> strings = new ArrayList<>();
    TextView textTitle, textColor, textGaranty, textPrice, textDesvription, textNext, textviewFree;
    CardView cardviewBasket;
    boolean b = true;
    String id;
    String session;
    String image, title, color, garanty, price, visit, label, cat;
    TextView textCountBasket;
    int ratingbar;
    CardView cardPr;
    String freprice;
    private boolean bf = true;
    Context context = this;
    DbSqlite sqlite = new DbSqlite(context);
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__show);


        // Toast.makeText(this, freprice, Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        session = preferences.getString(put.email, "ورود/عضویت");

        // Toast.makeText(this, session, Toast.LENGTH_SHORT).show();
        findview();

        Cursor cursor = sqlite.cu(Integer.parseInt(id));

        if (cursor.getCount() == 1) {
            imageFavorit.setColorFilter(getApplicationContext().getResources().getColor(R.color.red));
            imageFavorit.setImageResource(R.drawable.ic_star_black_24dp);
            bf = false;
        } else {
            imageFavorit.setColorFilter(getApplicationContext().getResources().getColor(R.color.gray));
            imageFavorit.setImageResource(R.drawable.ic_star_border_black_24dp);
            bf = true;
        }

    }


    private void findview() {
        textlike = findViewById(R.id.textlike);

        recyLike = findViewById(R.id.recyLike);
        imageFavorit = findViewById(R.id.imageFavorite);
        freprice = getIntent().getStringExtra(put.freeprice);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Vazir-Medium-FD-WOL.ttf");
        textviewFree = findViewById(R.id.textPriceShowfree);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        // String free = decimalFormat.format(Integer.valueOf(freprice));
        textPrice = findViewById(R.id.textPriceShow);
        textviewFree.setTypeface(typeface);
        cardPr = findViewById(R.id.cardPr);
        textviewFree.setVisibility(View.GONE);

        if (!freprice.equals("")) {
            textviewFree.setVisibility(View.VISIBLE);
            textviewFree.setText(freprice + " " + "تومان");
            textPrice.setTextColor(Color.RED);
            textPrice.setPaintFlags(textPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {

            textviewFree.setVisibility(View.GONE);
        }

        TextView textRat = findViewById(R.id.textRating);
       // ratingbar = getIntent().getIntExtra(put.rating, 0);
        String rat = getIntent().getStringExtra(put.rating);

        int r = rat.length();
        if (r==1)
        {
            rat =rat+"."+"0";
        }
        else {
            rat = rat.substring(0,3);
        }
//        rat = rat.s);
        textRat.setText(rat + " " + "از" + " " + "5");
        textRat.setTypeface(typeface);
        ScaleRatingBar bar = findViewById(R.id.ratingbarFinal);
        bar.setRating(Float.parseFloat(rat));
      //  Toast.makeText(context, rat, Toast.LENGTH_SHORT).show();
        id = getIntent().getStringExtra(put.id);
        CardView cardViewComment = findViewById(R.id.cardViewComment);
        cardViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Activity_Show.this,Activity_Show_Comment.class));
                Intent intent = new Intent(Activity_Show.this, Activity_Show_Comment.class);
                intent.putExtra(put.id, id);
                startActivity(intent);
            }
        });

        //  Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        image = getIntent().getStringExtra(put.image);
        garanty = getIntent().getStringExtra(put.garanty);
        color = getIntent().getStringExtra(put.color);
        title = getIntent().getStringExtra(put.title);


        cardviewBasket = findViewById(R.id.cardViewBasket);
        textTitle = findViewById(R.id.textTitleShow);
        textColor = findViewById(R.id.textcolorShow);
        textGaranty = findViewById(R.id.textGarantyShow);

        textCountBasket = findViewById(R.id.textCountBasket);
        SharedPreferences preferences = getSharedPreferences("c", MODE_PRIVATE);
        textCountBasket.setText(preferences.getString("count", "0"));
        textDesvription = findViewById(R.id.textDescriptionShow);
        textNext = findViewById(R.id.textNextShow);
        textNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b) {
                    textDesvription.setSingleLine(false);
                    textNext.setText("بستن");
                    b = false;
                } else {
                    textDesvription.setSingleLine(true);
                    textNext.setText("ادامه مطلب");
                    b = true;
                }

            }
        });


        imageBack = findViewById(R.id.imageBack);
        imageBasket = findViewById(R.id.imageBasket);
        sliderLayout = findViewById(R.id.slider);
        appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {


                int scroll = -(i);
                Log.i("collaps", scroll + "");
                if (scroll >= 935) {
                    imageBack.setColorFilter(Color.rgb(255, 255, 255));
                    imageBasket.setColorFilter(Color.rgb(255, 255, 255));
                    textCountBasket.setTextColor(Color.rgb(255, 255, 255));
                } else {
                    imageBack.setColorFilter(Color.rgb(189, 189, 189));
                    imageBasket.setColorFilter(Color.rgb(189, 189, 189));
                    textCountBasket.setTextColor(Color.rgb(189, 189, 189));
                }
            }
        });

        //   installImageSlider();
        getSilder(id);
        getIntentData();
        onclick();
        getLikeProduct();
    }

    private void getIntentData() {

        cat = getIntent().getStringExtra(put.cat);
        visit = getIntent().getStringExtra(put.visit);
        label = getIntent().getStringExtra(put.label);
        textTitle.setText(title);
        textColor.setText(color);
        textGaranty.setText(garanty);
        price = getIntent().getStringExtra(put.price);

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price1 = decimalFormat.format(Integer.valueOf(price));
        //  Toast.makeText(context, price1, Toast.LENGTH_SHORT).show();
        textPrice.setText(price1 + " " + "تومان");
        textDesvription.setText(getIntent().getStringExtra(put.description));
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Vazir-Medium-FD-WOL.ttf");
        textPrice.setTypeface(typeface);
        textColor.setTypeface(typeface);
        textGaranty.setTypeface(typeface);
        textTitle.setTypeface(typeface);
        textDesvription.setTypeface(typeface);


    }


    private void getLikeProduct() {
        adapterLike = new AdapterLike(getApplicationContext(), modelLikeList);
        recyLike.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recyLike.setAdapter(adapterLike);
        recyLike.hasFixedSize();

        if (cat.equals("0"))
        {
            recyLike.setVisibility(View.GONE);
            textlike.setText("محصول مشابهی یافت نشد");
        }
        else
        {  sendlikeproduct(cat);

        }


    }

    private void onclick() {

        imageFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bf) {
                    if (!freprice.equals("")) {
                        sqlite.insertFav(new ModelFav(Integer.parseInt(id), image, title, visit, freprice, price, label));
                        imageFavorit.setImageResource(R.drawable.ic_star_black_24dp);
                        imageFavorit.setColorFilter(getApplicationContext().getResources().getColor(R.color.red));
                        Toast.makeText(context, "به لیست علاقه مندی ها اضافه شد", Toast.LENGTH_SHORT).show();
                        bf = false;
                    } else {
                        sqlite.insertFav(new ModelFav(Integer.parseInt(id), image, title, visit, price, price, label));
                        imageFavorit.setImageResource(R.drawable.ic_star_black_24dp);
                        imageFavorit.setColorFilter(getApplicationContext().getResources().getColor(R.color.red));
                        Toast.makeText(context, "به لیست علاقه مندی ها اضافه شد", Toast.LENGTH_SHORT).show();
                        bf = false;
                    }

                } else {
                    sqlite.deleteFav(Integer.parseInt(id));
                    imageFavorit.setImageResource(R.drawable.ic_star_border_black_24dp);
                    imageFavorit.setColorFilter(getApplicationContext().getResources().getColor(R.color.gray));
                    Toast.makeText(context, "از لیست علاقه مندی ها حذف شد", Toast.LENGTH_SHORT).show();
                    bf = true;
                }
            }
        });
        cardPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Show.this, Activity_Prperties.class);
                intent.putExtra(put.id, id);
                intent.putExtra(put.title, title);
                startActivity(intent);
            }
        });

        cardviewBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (session.equals("ورود/عضویت")) {
                    Toast.makeText(Activity_Show.this, "لطفا وارد حساب خود شوید", Toast.LENGTH_SHORT).show();
                } else {
                    if (!freprice.equals(""))
                        sendBasket(id, session, title, color, image, garanty, freprice);
                    else
                        sendBasket(id, session, title, color, image, garanty, price);
                }
                // Toast.makeText(Activity_Show.this, session + " " + id + " " + title + " " + color + " " + garanty + " " + price, Toast.LENGTH_LONG).show();

            }
        });
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Show.this, Activity_Basket.class));
            }
        });

    }

    private void installImageSlider() {

//        Map<String, String> url_image = new TreeMap<>();
//        url_image.put("image1", "https://sari-tech.com/wp-content/uploads/2017/09/iphone-x-iphone-10-3.png");
//        url_image.put("image2", "https://cdn01.zoomit.ir/2018/3/03145a04-4738-47d6-b31f-277beaf5c98b.jpg");
//        url_image.put("image3", "https://static.interestingengineering.com/images/MARCH/sizes/adidas-ultraboost-ocean-plastic_resize_md.jpg");
//        url_image.put("image4", "https://blog.jiji.ng/wp-content/uploads/2017/09/flp_main-758x397.jpg");
//
//        for (int i = 0; i < url_image.keySet().size(); i++) {
//
//            String keyname = url_image.keySet().toArray()[i].toString();
//            TextSliderView textSliderView = new TextSliderView(this);
//            // textSliderView.description(keyname)
//            textSliderView.image(url_image.get(keyname))
////                           .empty(R.drawable.ic_launcher_background)
////                           .error(R.drawable.ic_launcher_foreground)
//
//                    .setScaleType(BaseSliderView.ScaleType.Fit);
//            //  .setOnSliderClickListener(this);
//
//
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra", keyname);
//            sliderLayout.setDuration(3000);
//            sliderLayout.addSlider(textSliderView);
//        }
    }


    private void getSilder(final String idd) {

        final String url = Link.linkImage;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Show.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    // Toast.makeText(Activity_Show.this, response.toString(), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pics = jsonObject.getString("pics");
                        strings.add(pics);
                        TextSliderView textSliderView = new TextSliderView(Activity_Show.this);
                        // textSliderView.description(keyname)
                        textSliderView.image(strings.get(i))
                                .empty(R.drawable.plcaeholder)
                                .error(R.drawable.error)
                                .setScaleType(BaseSliderView.ScaleType.Fit);

//                    textSliderView.bundle(new Bundle());
//                    textSliderView.getBundle()
//                            .putString("extra", keyname);
                        sliderLayout.setDuration(3000);
                        sliderLayout.addSlider(textSliderView);
                    }
                } catch (Exception e) {

                }

                progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //  Toast.makeText(Activity_Show.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id, idd);
                return map;
            }
        };


        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void sendBasket(final String id, final String email, final String title, final String color, final String image, final String garanty, final String price) {

        String url = Link.linkBasket;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Show.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(Activity_Show.this, response.toString(), Toast.LENGTH_SHORT).show();
                String count = textCountBasket.getText().toString();
                int total = 0;
                total = Integer.parseInt(count) + 1;
                textCountBasket.setText(String.valueOf(total));

                progressDialog.dismiss();

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activity_Show.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(put.id, id);
                map.put(put.title, title);
                map.put(put.color, color);
                map.put(put.garanty, garanty);
                map.put(put.image, image);
                map.put(put.price, price);
                map.put(put.email, email);

                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);


    }

    private void sendlikeproduct(final String cat) {

        String url = Link.linkLike;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Show.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Gson gson = new Gson();
                ModelLike[] likes = gson.fromJson(response.toString(), ModelLike[].class);
                for (int i = 0; i < likes.length; i++) {
                    modelLikeList.add(likes[i]);
                    adapterLike.notifyDataSetChanged();

                }

                progressDialog.dismiss();

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activity_Show.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(put.cat, cat);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);


    }


}
