package com.google.asli.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.put;
import com.google.asli.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Wait extends AppCompatActivity {

    ProgressBar progressBar;
    String id;
    String image,title,visit,price,label,date,only,sale,color,garanty,description,cat;
    String ratingbar;
    String freeprice;
    float f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__wait);
        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        String session = preferences.getString(put.email, "ورود/عضویت");
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        id = getIntent().getStringExtra(put.id);
        freeprice = getIntent().getStringExtra(put.freeprice);
        //Toast.makeText(this, freeprice, Toast.LENGTH_LONG).show();

        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
       sendId(id,session);

      //  getSilder();
    }
    private void sendId(final String idd, final String email)
    {
        String url = Link.linkData;
        progressBar.setVisibility(View.VISIBLE);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i<jsonArray.length();i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        id = object.getString("id");
                        image = object.getString("image");
                        title = object.getString("title");
                        visit = object.getString("visit");
                        price = object.getString("price");
                        cat = object.getString("cat");
                        label = object.getString("label");
                        date = object.getString("date");
                        only = object.getString("only");
                        sale = object.getString("sale");
                        color = object.getString("color");
                        garanty = object.getString("garanty");
                        description = object.getString("description");
                        ratingbar = object.getString("finalrating");
                        float f = Float.parseFloat(ratingbar);
                     //  Toast.makeText(Activity_Wait.this, String.valueOf(f), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Activity_Wait.this, id, Toast.LENGTH_LONG).show();

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Activity_Wait.this,Activity_Show.class);
                intent.putExtra(put.id,id);
                intent.putExtra(put.image,image);
                intent.putExtra(put.title,title);
                intent.putExtra(put.visit,visit);
                intent.putExtra(put.price,price);
                intent.putExtra(put.label,label);
                intent.putExtra(put.date,date);
                intent.putExtra(put.only,only);
                intent.putExtra(put.sale,sale);
                intent.putExtra(put.color,color);
                intent.putExtra(put.cat,cat);
                intent.putExtra(put.garanty,garanty);
                intent.putExtra(put.description,description);
                intent.putExtra(put.rating,ratingbar);
                if (!freeprice.equals(""))
                intent.putExtra(put.freeprice,freeprice);
                else
                    intent.putExtra(put.freeprice,"");

                startActivity(intent);

                    finish();


                progressBar.setVisibility(View.GONE);

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_Wait.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id,idd);
                map.put(put.email,email);
                return map;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
