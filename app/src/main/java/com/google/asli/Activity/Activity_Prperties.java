package com.google.asli.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.TypeWriter;
import com.google.asli.Class.put;
import com.google.asli.R;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Prperties extends AppCompatActivity {

    private TextView textTile,texttitleActivity,textPr;
    //private TypeWriter textPr;
    String tittle,id;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__prperties);

        id = getIntent().getStringExtra(put.id);
        tittle = getIntent().getStringExtra(put.title);
        texttitleActivity = findViewById(R.id.textTitleActivity);
        texttitleActivity.setText("مشخصات");
        textPr = findViewById(R.id.textPr);
        textTile = findViewById(R.id.textTilePr);
        textTile.setText(tittle);
        imageView =findViewById(R.id.imageBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getProperties(id);
    }
    private void getProperties(final String id)
    {
        String url = Link.linkGetProperties;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Prperties.this);
        progressDialog.show();


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                textPr.setText(response.toString().replace("<br/>",""));
                //textPr.setText(response.toString().replace("<br/>",""));
                progressDialog.dismiss();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_Prperties.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

}
