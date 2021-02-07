package com.google.asli.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.asli.Class.put;

import com.google.asli.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Profile extends AppCompatActivity {

    private Button btnprofile, btnfav, btnexite;
    private ImageView imageBack;
    private TextView textTilte;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profile);

        findview();
    }

    private void findview() {
        imageBack = findViewById(R.id.imageBack);
        textTilte = findViewById(R.id.textTitleActivity);
        textTilte.setText("پروفایل");
        btnexite = findViewById(R.id.btnExite);
        btnfav = findViewById(R.id.btnFav);
        btnprofile = findViewById(R.id.btnProfile);
        onclick();

    }

    private void onclick() {
        btnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Profile.this, Activity_Favorit.class));

            }
        });
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnexite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(put.email, "ورود/عضویت");
                editor.putString(put.image, "");
                editor.apply();
                Intent intent = new Intent();
                intent.putExtra(put.email, "ورود/عضویت");
                intent.putExtra(put.image, "");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Activity_Profile.this, Activity_EditeProfile.class));
            }
        });
    }


}
