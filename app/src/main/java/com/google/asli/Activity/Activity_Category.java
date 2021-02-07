package com.google.asli.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Model.ModelCategory;
import com.google.asli.R;
import com.google.asli.Adapter.AdapterCategory;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Category extends AppCompatActivity {

    private RecyclerView recyclerView;
    AdapterCategory adapterCategory;
    List<ModelCategory> modelCategories = new ArrayList<>();
    private ImageView imageBack;
    private TextView textTilte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__category);

        recyclerView = findViewById(R.id.recyCategory);
        adapterCategory = new AdapterCategory(getApplicationContext(),modelCategories);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapterCategory);
        imageBack = findViewById(R.id.imageBack);
        textTilte = findViewById(R.id.textTitleActivity);
        textTilte.setText("دسته بندی محصولات");

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setCategory();
    }
    private void setCategory()
    {

        String url= Link.linkCategory;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Category.this);
        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelCategory[] categories = gson.fromJson(response.toString(),ModelCategory[].class);
                for (int i = 0 ; i<categories.length;i++)
                {
                    modelCategories.add(categories[i]);
                    adapterCategory.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activity_Category.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,listener,errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
