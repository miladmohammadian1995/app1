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
import com.google.asli.Adapter.AdapterItemCategory;
import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.put;
import com.google.asli.Model.ModelItemCategory;
import com.google.asli.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Item_Category extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterItemCategory adapterItemCategory;
    List<ModelItemCategory> itemCategories = new ArrayList<>();
    String id;
    ImageView imageback;
    TextView texttitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__item__category);

        imageback = findViewById(R.id.imageBack);
        texttitle = findViewById(R.id.textTitleActivity);

        texttitle.setText(getIntent().getStringExtra(put.title));
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyitemcategory);
        adapterItemCategory = new AdapterItemCategory(getApplicationContext(),itemCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterItemCategory);
        recyclerView.hasFixedSize();

        id = getIntent().getStringExtra(put.id);
        getItemCategory(id);
    }

    private void getItemCategory(final String id){


        String url = Link.linkGetItemCategory;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Item_Category.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ModelItemCategory[] categories = gson.fromJson(response.toString(), ModelItemCategory[].class);
                for (int i = 0; i < categories.length; i++) {
                    itemCategories.add(categories[i]);
                    adapterItemCategory.notifyDataSetChanged();

                }
                progressDialog.dismiss();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_Item_Category.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener)
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id,id);
                return map;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
    @Override
    public void finish() {
        super.finish();

    }

}
