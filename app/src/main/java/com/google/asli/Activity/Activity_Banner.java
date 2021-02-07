package com.google.asli.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.asli.Adapter.AdapterItemBanner;
import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.put;
import com.google.asli.Model.ModelItemBanner;
import com.google.asli.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Banner extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterItemBanner adapterItemBanner;
    List<ModelItemBanner> modelItemBanners = new ArrayList<>();
    String id;
    private ImageView imageBack;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__banner);

        imageBack = findViewById(R.id.imageBack);
        textTitle= findViewById(R.id.textTitleActivity);
        textTitle.setText("پیشنهادات");
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        id = getIntent().getStringExtra(put.id);
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recyitembaneer);
        adapterItemBanner = new AdapterItemBanner(getApplicationContext(), modelItemBanners);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterItemBanner);
        //recyclerView.hasFixedSize();

        getItemBanner(id);
    }

    private void getItemBanner(final String id) {
        String url = Link.linkGetBannerItem;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Banner.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ModelItemBanner[] banners = gson.fromJson(response.toString(), ModelItemBanner[].class);
                for (int i = 0; i < banners.length; i++) {
                    modelItemBanners.add(banners[i]);
                    adapterItemBanner.notifyDataSetChanged();

                }

                progressDialog.dismiss();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activity_Banner.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id, id);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
