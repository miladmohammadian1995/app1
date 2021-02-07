package com.google.asli;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.asli.Activity.Activity_About;
import com.google.asli.Activity.Activity_AllProduct;
import com.google.asli.Activity.Activity_Basket;
import com.google.asli.Activity.Activity_Category;
import com.google.asli.Activity.Activity_Favorit;
import com.google.asli.Activity.Activity_Login;
import com.google.asli.Activity.Activity_Order;
import com.google.asli.Activity.Activity_Profile;
import com.google.asli.Activity.Activity_Search;
import com.google.asli.Adapter.AdapterBanner;
import com.google.asli.Adapter.AdapterFree;
import com.google.asli.Adapter.AdapterOnly;
import com.google.asli.Adapter.AdapterSales;
import com.google.asli.Adapter.AdapterVisit;
import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.put;
import com.google.asli.Model.ModelBanner;
import com.google.asli.Model.ModelBestSales;
import com.google.asli.Model.ModelFree;
import com.google.asli.Model.ModelOnly;
import com.google.asli.Model.ModelVisit;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Home_Activity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private TextView textfreeAll,textOnlyAll;
    private SliderLayout sliderLayout;
    AdapterBanner adapterBanner;
    List<ModelBanner> modelBanners = new ArrayList<>();
    RecyclerView recyFree, recyOnly, recyVisit, recySales, recyBanner;
    AdapterSales adapterSales;
    List<ModelBestSales> modelBestSales = new ArrayList<>();
    AdapterVisit adapterVisit;
    List<ModelVisit> visits = new ArrayList<>();
    AdapterOnly adapterOnly;
    List<ModelOnly> modelOnlies = new ArrayList<>();
    AdapterFree adapterFree;
    List<ModelFree> modelFrees = new ArrayList<>();
    ImageView imageNav;
    DrawerLayout drawerLayout;
    TextView textLogin, textfreedate;
    CardView cardCat;
    private String data = "";
    ArrayList<String> strings = new ArrayList<>();
    private TextView textcount;
    String session;
    private CircleImageView circleImageView;
    String imageuser;
    ImageView imageViewBaket, imagesearch;
    LinearLayout lnrFav, lnrBuy, lnrCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        findView();

        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        textLogin.setText(preferences.getString(put.email, "ورود/عضویت"));
        session = preferences.getString(put.email, "ورود/عضویت");
        textcount = findViewById(R.id.textCount);
        imageuser = preferences.getString(put.image, "");
        if (imageuser.equals("")) {
            Picasso
                    .with(getApplicationContext())
                    .load(R.drawable.userprofile)
                    .into(circleImageView);
        } else {
            Picasso
                    .with(getApplicationContext())
                    .load(imageuser)
                    .into(circleImageView);
        }


        // Toast.makeText(this, "creatr", Toast.LENGTH_SHORT).show();
    }

    private void findView() {

        textOnlyAll = findViewById(R.id.textOnlyAll);
        textfreeAll = findViewById(R.id.textfreeAll);
        lnrBuy = findViewById(R.id.lnrbuy);
        lnrFav = findViewById(R.id.lnrFav);
        lnrCategory = findViewById(R.id.lnrCtegory);
        imagesearch = findViewById(R.id.imagesearch);
        imageViewBaket = findViewById(R.id.imagebasketHome);
        circleImageView = findViewById(R.id.circleImageUser);
        textfreedate = findViewById(R.id.textfreedate);
        textLogin = findViewById(R.id.textLogin);
        drawerLayout = findViewById(R.id.drawer);
        sliderLayout = findViewById(R.id.slider);
        recyBanner = findViewById(R.id.recybanner);
        recyFree = findViewById(R.id.recyfree);
        recyOnly = findViewById(R.id.recyonly);
        recyVisit = findViewById(R.id.recyvisit);
        recySales = findViewById(R.id.recysales);
        imageNav = findViewById(R.id.imageNav);
        cardCat = findViewById(R.id.cardCat);

       onclick();
       setUpRecyclerview();

        getFreeData();
      getOnlyData();
       getVisitData();
       getSaleData();
      getBannerData();
       installImageSlider();
    }

    private void onclick() {

        textOnlyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this,Activity_AllProduct.class);
                intent.putExtra("idText","1");
                startActivity(intent);
            }
        });
        textfreeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, Activity_AllProduct.class);
                intent.putExtra("idText","2");
                startActivity(intent);
            }
        });
        lnrCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Activity_Category.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
        LinearLayout lnrabout = findViewById(R.id.lnrabout);
        lnrabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Activity_About.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }
        });
        lnrBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Activity_Order.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
        lnrFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Activity_Favorit.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
        imagesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Home_Activity.this, Activity_Search.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        imageViewBaket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Activity_Basket.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
        imageNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home_Activity.this, "navigation", Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textLogin.getText().equals("ورود/عضویت")) {
                    Intent intent = new Intent(Home_Activity.this, Activity_Login.class);
                    startActivityForResult(intent, put.REQUEST_CODE);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else {
                    Intent intent = new Intent(Home_Activity.this, Activity_Profile.class);
                    startActivityForResult(intent, put.REQUEST_EXITE);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }

            }
        });
        cardCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Activity_Category.class));
            }
        });
    }
//
    private void setUpRecyclerview() {
        adapterFree = new AdapterFree(getApplicationContext(), modelFrees);
        recyFree.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recyFree.setAdapter(adapterFree);

        adapterOnly = new AdapterOnly(getApplicationContext(), modelOnlies);
        recyOnly.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recyOnly.setAdapter(adapterOnly);

        adapterVisit = new AdapterVisit(getApplicationContext(), visits);
        recyVisit.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recyVisit.setAdapter(adapterVisit);

        adapterSales = new AdapterSales(getApplicationContext(), modelBestSales);
        recySales.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recySales.setAdapter(adapterSales);

        adapterBanner = new AdapterBanner(getApplicationContext(), modelBanners);
        recyBanner.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyBanner.setAdapter(adapterBanner);

    }
//
//
//
//
//
    private void installImageSlider() {

        Map<String, String> url_image = new TreeMap<>();
        url_image.put("image1", "https://uupload.ir/files/iwrn_purepng.com-red-lotus-e23-f1-carcarvehicletransportlotusracing-car-961524658284rztha.png");
        url_image.put("image2", "https://uupload.ir/files/xkrx_580b585b2edbce24c47b2c8f.png");
        url_image.put("image3", "https://uupload.ir/files/frjm_222-2227819_transparent-f1-car-png-formula-1-car-png.png");
        url_image.put("image4", "https://uupload.ir/files/m4vp_kisspng-2016-fia-formula-one-world-championship-red-bull-r-toyota-f1-circuit-5a697fe6722f49.6913715015168634624677.png");

        for (int i = 0; i < url_image.keySet().size(); i++) {

            String keyname = url_image.keySet().toArray()[i].toString();
            TextSliderView textSliderView = new TextSliderView(this);
            // textSliderView.description(keyname)
            textSliderView.image(url_image.get(keyname))
//                           .empty(R.drawable.ic_launcher_background)
//                           .error(R.drawable.ic_launcher_foreground)

                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", keyname);
            sliderLayout.setDuration(3000);
            sliderLayout.addSlider(textSliderView);
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

//        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
        String s = slider.getBundle().get("extra") + "";
        if (s.equals("image1")) {
            Toast.makeText(this, "عکس شماره یک", Toast.LENGTH_SHORT).show();
        } else if (s.equals("image2")) {
            Toast.makeText(this, "عکس شماره دو", Toast.LENGTH_SHORT).show();

        } else if (s.equals("image3")) {
            Toast.makeText(this, "عکس شماره سه", Toast.LENGTH_SHORT).show();

        } else if (s.equals("image4")) {
            Toast.makeText(this, "عکس شماره چهار", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == put.REQUEST_CODE && resultCode == RESULT_OK) {
            String email = data.getStringExtra(put.email);
            String image = data.getStringExtra(put.image);
            textLogin.setText(email);
            SharedPreferences sharedPreferences = getSharedPreferences(put.shared, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(put.email, email);
            editor.putString(put.image, image);
            //  Toast.makeText(this, image, Toast.LENGTH_SHORT).show();
            editor.apply();
            recreate();


        } else if (requestCode == put.REQUEST_EXITE && resultCode == RESULT_OK) {
            String email = data.getStringExtra(put.email);
            String image = data.getStringExtra(put.image);
            if (image.equals("")) {
                Picasso.with(getApplicationContext())
                        .load(R.drawable.userprofile)
                        .into(circleImageView);
            } else {
                Picasso
                        .with(getApplicationContext())
                        .load(image)
                        .into(circleImageView);

            }

            textLogin.setText(email);
            recreate();

        }
   }
//
    private void getBannerData() {

        String url = Link.linkGetBanner;
        final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelBanner[] banners = gson.fromJson(response.toString(), ModelBanner[].class);
                for (int i = 0; i < banners.length; i++) {
                    modelBanners.add(banners[i]);
                    adapterBanner.notifyDataSetChanged();

                }
                    progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Home_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                //    progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
//
    private void getFreeData() {

        String url = Link.linkFree;
       final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelFree[] frees = gson.fromJson(response.toString(), ModelFree[].class);
                for (int i = 0; i < frees.length; i++) {
                    modelFrees.add(frees[i]);
                    adapterFree.notifyDataSetChanged();

                }
                  progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Home_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                 progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void getOnlyData() {

        String url = Link.linkOnly;
        final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
       progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelOnly[] onlies = gson.fromJson(response.toString(), ModelOnly[].class);
                for (int i = 0; i < onlies.length; i++) {
                    modelOnlies.add(onlies[i]);
                    adapterOnly.notifyDataSetChanged();

                }
                progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Home_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                 progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
//
    private void getVisitData() {

        String url = Link.linkVisit;
       final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
       progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelVisit[] visit = gson.fromJson(response.toString(), ModelVisit[].class);
                for (int i = 0; i < visit.length; i++) {
                    visits.add(visit[i]);
                    adapterVisit.notifyDataSetChanged();

                }
                  progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Home_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                 progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void getSaleData() {

        String url = Link.linkSlaes;
       final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
       progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelBestSales[] sales = gson.fromJson(response.toString(), ModelBestSales[].class);
                for (int i = 0; i < sales.length; i++) {
                    modelBestSales.add(sales[i]);
                    adapterSales.notifyDataSetChanged();

                }
                 progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Home_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
//
    private void getCount(final String email) {
        String url = Link.linkGetCount;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                textcount.setText(response.toString());

                SharedPreferences preferences = getSharedPreferences("c", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("count", textcount.getText().toString());
                editor.apply();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };


        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.email, email);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
//
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //   Toast.makeText(this, "Destory", Toast.LENGTH_SHORT).show();
    }
//
    @Override
    protected void onStart() {
        super.onStart();
        //  Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
        getCount(session);
    }
//
    @Override
    protected void onStop() {
        super.onStop();
        //  Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
    }
//
    @Override
    protected void onPause() {
        super.onPause();
        // Toast.makeText(this, "pauas", Toast.LENGTH_SHORT).show();
    }
//
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//   }
}