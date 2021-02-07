package com.google.asli.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.asli.Adapter.AdapterOrder;
import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.put;
import com.google.asli.Model.ModelOrder;
import com.google.asli.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Order extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterOrder adapterOrder;
    List<ModelOrder> modelOrders = new ArrayList<>();
    String session;
    ImageView imageback;
    TextView texttitel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__order);
        texttitel = findViewById(R.id.textTitleActivity);
        texttitel.setText("سفارشات");
        imageback = findViewById(R.id.imageBack);
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        session = preferences.getString(put.email, "ورود/عضویت");
        recyclerView = findViewById(R.id.recyOrder);
        adapterOrder = new AdapterOrder(getApplicationContext(), modelOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterOrder);
        getOrder(session);
    }

    private void getOrder(final String email) {
        String url = Link.linkGetOrder;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Order.this);
        progressDialog.show();


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString(put.id);
                        String price = object.getString(put.allprice);
                        String refid = object.getString(put.refid);
                        String number = object.getString(put.number);
                        String date = object.getString(put.date);
                        String email = object.getString(put.email);
                        modelOrders.add(new ModelOrder(email, refid, price, number, date));
                        adapterOrder.notifyDataSetChanged();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();


            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener)

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.email, email);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }


}
