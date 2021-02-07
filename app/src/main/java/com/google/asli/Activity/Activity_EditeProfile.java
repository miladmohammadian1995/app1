package com.google.asli.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.google.asli.R;
import com.google.asli.Class.put;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_EditeProfile extends AppCompatActivity {

    private TextView textEdite;
    private EditText edusername, edaddress, edphone;
    private ImageView imageback;
    private TextView textTile;
    SharedPreferences preferences;
    SharedPreferences preferences2;
    private String emailEdite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edite_profile);

        findview();
        preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        emailEdite = preferences.getString(put.email, "ایمیل");
    }

    private void findview() {
        edaddress = findViewById(R.id.edAddress);
        edphone = findViewById(R.id.edPhone);
        edusername = findViewById(R.id.edUsername);
        textEdite = findViewById(R.id.textEdite);
        imageback = findViewById(R.id.imageBack);
        textTile = findViewById(R.id.textTitleActivity);
        textTile.setText("ویرایش اطلاعات");

        preferences2 = getSharedPreferences(put.shared2, MODE_PRIVATE);
        edaddress.setText(preferences2.getString(put.address, ""));
        edphone.setText(preferences2.getString(put.phone, ""));
        edusername.setText(preferences2.getString(put.username, ""));

        onclick();
    }

    private void onclick() {

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textEdite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edusername.getText().toString().trim();
                String address = edaddress.getText().toString().trim();
                String phone = edphone.getText().toString().trim();
                if (user.equalsIgnoreCase("") || address.equalsIgnoreCase("") || phone.equalsIgnoreCase("")) {
                    Toast.makeText(Activity_EditeProfile.this, "فیلدهای مورد نظر را پر کنید", Toast.LENGTH_SHORT).show();
                } else
                    sendEditeProfile(emailEdite, edusername.getText().toString().trim(), edphone.getText().toString().trim(), edaddress.getText().toString().trim());
            }
        });
    }

    private void sendEditeProfile(final String email, final String username, final String phone, final String address) {
        String url = Link.linkEdite;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_EditeProfile.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.toString().equals("1")) {
                    preferences2 = getSharedPreferences(put.shared2, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences2.edit();
                    editor.putString(put.username, username);
                    editor.putString(put.address, address);
                    editor.putString(put.phone, phone);
                    editor.apply();
                    Toast.makeText(Activity_EditeProfile.this, "بروزرسانی انجام شد", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_EditeProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(put.email, email);
                map.put(put.username, username);
                map.put(put.phone, phone);
                map.put(put.address, address);

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
