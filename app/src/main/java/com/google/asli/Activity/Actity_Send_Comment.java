package com.google.asli.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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
import com.google.asli.Class.put;
import com.google.asli.R;

import com.willy.ratingbar.ScaleRatingBar;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Actity_Send_Comment extends AppCompatActivity {

    ScaleRatingBar ratingBar;
    EditText edcomment,edposotive,ednegarive;
    Button btncomment;
    String session,imageuser;
    String id ;
    private ImageView imageBack;
    private TextView textTilte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actity__send__comment);

        id = getIntent().getStringExtra(put.id);
        findview();

        SharedPreferences preferences = getSharedPreferences(put.shared, MODE_PRIVATE);
        session = preferences.getString(put.email, "ورود/عضویت");
        imageuser = preferences.getString(put.image,"");

        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

    }

    private void findview()
    {
        imageBack = findViewById(R.id.imageBack);
        textTilte = findViewById(R.id.textTitleActivity);
        textTilte.setText("ثبت دیدگاه");
        ratingBar = findViewById(R.id.ratingbarFinal);
        edcomment = findViewById(R.id.edcooment);
        ednegarive = findViewById(R.id.ednegative);
        edposotive = findViewById(R.id.edposotive);
        btncomment = findViewById(R.id.btncomment);
        onclick();
    }
    private void onclick()
    {
imageBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});
        btncomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (session.equals("ورود/عضویت") || imageuser.equals(""))
                {
                    Toast.makeText(Actity_Send_Comment.this, "شما وارد حساب کاربری خود نشده اید", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (edcomment.getText().toString().equals("")|| ednegarive.getText().toString().equals("")||edposotive.getText().toString().equals(""))
                    {
                        Toast.makeText(Actity_Send_Comment.this, "لطفا مقادیر لازم را پر کنید", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        senComment(id,session,imageuser,edcomment.getText().toString(),edposotive.getText().toString(),
                                ednegarive.getText().toString(),ratingBar.getRating()
                        );
                    }


                }
            }
        });
    }

    private void senComment(final String idproduct, final String email, final String imageuser, final String comment, final String posotive, final String negative, final float rating)
    {

        String url =Link.linkSendComment;
        final ProgressDialog progressDialog = new ProgressDialog(Actity_Send_Comment.this);
        progressDialog.show();
        progressDialog.setMessage("در حال ارسال دیدگاه");

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.toString().equals("1")) {
                    Toast.makeText(Actity_Send_Comment.this, "اطلاعات ثبت شد", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                         finish();

                }
                progressDialog.dismiss();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Actity_Send_Comment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        };


        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener)
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id,idproduct);
                map.put(put.email,email);
                map.put(put.image,imageuser);
                map.put(put.negative,negative);
                map.put(put.comment,comment);
                map.put(put.posotive,posotive);
                map.put(put.rating, String.valueOf(rating));


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
