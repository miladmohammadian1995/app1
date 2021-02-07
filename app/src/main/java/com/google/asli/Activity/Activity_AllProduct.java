package com.google.asli.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.asli.Adapter.AdapterFree;
import com.google.asli.Adapter.AdapterOnly;
import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Model.ModelFree;
import com.google.asli.Model.ModelOnly;
import com.google.asli.R;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Activity_AllProduct extends AppCompatActivity {

    RecyclerView recyall;
    AdapterOnly adapterOnly;
    List<ModelOnly> modelOnlies =new ArrayList<>();
    AdapterFree adapterFree ;
    List<ModelFree> modelFrees = new ArrayList<>();
    String idText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__all_product);
        recyall = findViewById(R.id.recyAll);
        idText = getIntent().getStringExtra("idText");
        if (idText.equals("1"))
        {
            adapterOnly = new AdapterOnly(getApplicationContext(),modelOnlies);
            recyall.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
            recyall.setAdapter(adapterOnly);
            getOnlyData();
        }
      else if (idText.equals("2"))
        {
            adapterFree = new AdapterFree(getApplicationContext(),modelFrees);
            recyall.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
            recyall.setAdapter(adapterFree);
            getFreeData();
        }


    }
    private void getFreeData() {

        String url = Link.linkFree;
//        final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
//        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelFree[] frees = gson.fromJson(response.toString(), ModelFree[].class);
                for (int i = 0; i < frees.length; i++) {
                    modelFrees.add(frees[i]);
                    adapterFree.notifyDataSetChanged();

                }
                //  progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activity_AllProduct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
    private void getOnlyData() {

        String url = Link.linkOnly;
//        final ProgressDialog progressDialog = new ProgressDialog(Home_Activity.this);
//        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                ModelOnly[] onlies = gson.fromJson(response.toString(), ModelOnly[].class);
                for (int i = 0; i < onlies.length; i++) {
                    modelOnlies.add(onlies[i]);
                    adapterOnly.notifyDataSetChanged();

                }
                //progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activity_AllProduct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
