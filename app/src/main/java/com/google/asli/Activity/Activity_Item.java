package com.google.asli.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.google.asli.Adapter.AdapterItem;
import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.put;
import com.google.asli.Model.ModelItem;
import com.google.asli.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Item extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterItem adapterItem;
    List<ModelItem> modelItems = new ArrayList<>();
    CardView cardFilter;
    String id;
    ImageView imageback;
    TextView texttitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__item);
        imageback = findViewById(R.id.imageBack);
        texttitle = findViewById(R.id.textTitleActivity);

        texttitle.setText(getIntent().getStringExtra(put.title));
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cardFilter = findViewById(R.id.cardfilter);
        id =getIntent().getStringExtra(put.id);
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recyitem);
        adapterItem = new AdapterItem(getApplicationContext(),modelItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterItem);
        recyclerView.hasFixedSize();
        getItem(id);

        cardFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(Activity_Item.this, "filter", Toast.LENGTH_SHORT).show();
                dialogFilter();
            }
        });
    }
    private void getItem(final String id) {

        String url = Link.linkGetItem;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Item.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ModelItem[] items = gson.fromJson(response.toString(), ModelItem[].class);
                for (int i = 0; i < items.length; i++) {
                    modelItems.add(items[i]);
                    adapterItem.notifyDataSetChanged();

                }
                progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Activity_Item.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id,id);
                return map;
            }
        }
                ;
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void dialogFilter()
    {
        final Dialog dialog = new Dialog(Activity_Item.this);
        dialog.setContentView(R.layout.dialog);
        RadioButton rd1,rd2,rd3,rd4;

        rd1 = dialog.findViewById(R.id.rd1);
        rd2 = dialog.findViewById(R.id.rd2);
        rd3 = dialog.findViewById(R.id.rd3);
        rd4 = dialog.findViewById(R.id.rd4);

        rd1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               // Toast.makeText(Activity_Item.this, "1", Toast.LENGTH_SHORT).show();

                  modelItems.clear();
                adapterItem.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFilter(id,"visit");
                        dialog.cancel();
                    }
                },100);

            }
        });

        rd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                modelItems.clear();
                adapterItem.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFilter(id,"sale");
                        dialog.cancel();
                    }
                },100);
            }
        });
        rd3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                modelItems.clear();
                adapterItem.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFilter(id,"priceUp");
                        dialog.cancel();
                    }
                },100);
            }
        });
        rd4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                modelItems.clear();
                adapterItem.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFilter(id,"priceDown");
                        dialog.cancel();
                    }
                },100);
            }
        });
        dialog.show();

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        width = (int) ((width) * ((double) 4 / 5));


        dialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    private void getFilter(final String id , final String param)
    {
        String url = Link.linkfilter;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ModelItem[] items = gson.fromJson(response.toString(), ModelItem[].class);
                for (int i = 0; i < items.length; i++) {
                    modelItems.add(items[i]);
                    adapterItem.notifyDataSetChanged();

                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_Item.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("idcat",id);
                map.put("param",param);
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
