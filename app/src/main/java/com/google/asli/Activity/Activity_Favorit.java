package com.google.asli.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.asli.Adapter.AdapterFav;
import com.google.asli.Class.DbSqlite;
import com.google.asli.Model.ModelFav;
import com.google.asli.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Favorit extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterFav adapterFav;
    List<ModelFav> modelFavs = new ArrayList<>();
    ImageView imageback;
    TextView texttitel;
    DbSqlite dbSqlite = new DbSqlite(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__favorit);





        texttitel = findViewById(R.id.textTitleActivity);
        texttitel.setText("علاقه مندی ها");
        imageback = findViewById(R.id.imageBack);
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyfav);
//        adapterFav = new AdapterFav(getApplicationContext(),modelFavs);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(adapterFav);
//        recyclerView.hasFixedSize();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    @Override
    protected void onStart() {
        super.onStart();
        modelFavs = dbSqlite.showData();
        adapterFav = new AdapterFav(getApplicationContext(),modelFavs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapterFav);
        recyclerView.hasFixedSize();
    }

}
