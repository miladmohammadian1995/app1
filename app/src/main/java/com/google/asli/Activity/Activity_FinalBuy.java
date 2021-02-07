package com.google.asli.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.asli.Class.put;
import com.google.asli.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_FinalBuy extends AppCompatActivity {

    TextView textallprice,textrefid,textfinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__final_buy);

        textallprice = findViewById(R.id.textALlpricefinal);
        textrefid = findViewById(R.id.textrefidFinal);
        textfinish = findViewById(R.id.textfinish);
        textfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textallprice.setText(getIntent().getStringExtra(put.allprice));
        textrefid.setText(getIntent().getStringExtra(put.refid));


    }

}
