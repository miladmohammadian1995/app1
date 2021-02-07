package com.google.asli.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.asli.Class.Link;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.OnloadPrice;
import com.google.asli.Class.put;

import com.google.asli.R;
import com.google.asli.Adapter.AdapterBasket;
import com.google.asli.Model.ModelBasket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Basket extends AppCompatActivity {

    LinearLayout lnrBasket;
    RecyclerView recyBasket;
    AdapterBasket adapterBasket;
    List<ModelBasket> modelBaskets = new ArrayList<>();
    String session;
    int totalallprice = 0;
    TextView textTotal;
    TextView textTitle;
    ImageView imageback;
    TextView textZarinpal;
    String title;
    String number;
    ArrayList<String> titleList=new ArrayList<>();
    int nm = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__basket);

        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        session = preferences.getString(put.email, "ورود/عضویت");

        findview();
    }

    private void findview() {
        textTotal = findViewById(R.id.textTotal);
        textZarinpal = findViewById(R.id.textZarinpal);
        textZarinpal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (session.equals("ورود/عضویت"))
//                {
//                    Toast.makeText(Activity_Basket.this, "لطفا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
//                }
//                else if (textTotal.getText().toString().equals("0 تومان")) {
//
//                    Toast.makeText(Activity_Basket.this, "شما محصولی برای خرید ندارید", Toast.LENGTH_SHORT).show();
//                }
//                    else
//                    {
//                        Intent intent = new Intent(Activity_Basket.this,Activity_WebGat.class);
//                        intent.putExtra("total",totalallprice);
//                        intent.putStringArrayListExtra("desc",titleList);
//                        intent.putExtra(put.number,nm);
//                        startActivity(intent);
//                        finish();
                  //}


               Toast.makeText(Activity_Basket.this, "درگاه برای اپلیکیشن فعال نیست!", Toast.LENGTH_LONG).show();

            }
        });
        textTitle = findViewById(R.id.textTitleActivity);
        textTitle.setText("سبد خرید");
        imageback = findViewById(R.id.imageBack);
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lnrBasket = findViewById(R.id.lnrBasket);
        recyBasket = findViewById(R.id.recyBasket);
        adapterBasket = new AdapterBasket(getApplicationContext(), modelBaskets);
        adapterBasket.setOnloadPrice(new OnloadPrice() {
            @Override
            public void onloadprice() {
                recreate();
                Toast.makeText(Activity_Basket.this, "سبد خرید شما بروز شد", Toast.LENGTH_SHORT).show();
            }
        });
        recyBasket.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyBasket.setAdapter(adapterBasket);
        getBsket(session);

    }

    private void getBsket(final String email) {
        String url = Link.linkGetBasket;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Basket.this);
        progressDialog.show();


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString(put.id);
                       title = object.getString(put.title);
                        String image = object.getString(put.image);
                        String color = object.getString(put.color);
                        String garnaty = object.getString(put.garanty);
                        String price = object.getString(put.price);
                        String allprice = object.getString(put.allprice);
                        number = object.getString(put.number);
                        modelBaskets.add(new ModelBasket(Integer.parseInt(id),title, image, number, color, garnaty, price, allprice));
                        adapterBasket.notifyDataSetChanged();
                        totalallprice += Integer.parseInt(allprice);
                    nm += Integer.parseInt(number);
                    titleList.add(title);
                      //  Toast.makeText(Activity_Basket.this, totalallprice+"", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                String price1 = decimalFormat.format(Integer.valueOf(totalallprice+""));

                Typeface typeface = Typeface.createFromAsset(getAssets(),"Vazir-Medium-FD-WOL.ttf");
                textTotal.setTypeface(typeface);
                textTotal.setText(price1+""+" "+"تومان");

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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}
