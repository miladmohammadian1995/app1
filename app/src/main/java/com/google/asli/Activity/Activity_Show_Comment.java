package com.google.asli.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.asli.Adapter.Adapter_Comment;

import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.put;
import com.google.asli.Model.ModelComment;



import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.asli.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Show_Comment extends AppCompatActivity {
    String id;
    RecyclerView recyclerView;
    Adapter_Comment adapter_comment;
    List<ModelComment> modelComments = new ArrayList<>();
    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__show__comment);
        id = getIntent().getStringExtra(put.id);
        actionButton = findViewById(R.id.floataction);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Show_Comment.this, Actity_Send_Comment.class);
                intent.putExtra(put.id, id);
                startActivityForResult(intent, 2020);
            }
        });
        recyclerView = findViewById(R.id.recyComment);
        adapter_comment = new Adapter_Comment(getApplicationContext(), modelComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter_comment);
        recyclerView.hasFixedSize();

        TextView textTitle = findViewById(R.id.textTitleActivity);
        textTitle.setText("نظرات کاربران");
        ImageView imageView= findViewById(R.id.imageBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Toast.makeText(this, getIntent().getStringExtra(put.id), Toast.LENGTH_SHORT).show();

        getComment(id);
    }

    private void getComment(final String id) {
        final String url = Link.linkGetComment;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Show_Comment.this);
        progressDialog.show();

        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString(put.id);
                        String image = jsonObject.getString(put.image);
                        String user = jsonObject.getString("user");
                        String comment = jsonObject.getString("comment");
                        String posotive = jsonObject.getString("posotive");
                        String negative = jsonObject.getString("negative");
                        String rating = jsonObject.getString("rating");
                        String confirm = jsonObject.getString("confirm");
                        String idproduct = jsonObject.getString("idproduct");
                        modelComments.add(new ModelComment(image, user, comment, posotive, negative, Float.parseFloat(rating)));
                        adapter_comment.notifyDataSetChanged();

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

                Toast.makeText(Activity_Show_Comment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2020 && resultCode == RESULT_OK) {

            recreate();
        }


    }


}
