package com.google.asli.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
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

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Login extends AppCompatActivity {

    private TextView textTitle, textRegister;
    private ImageView imageBack;
    private EditText edemail, edpass;
    private TextView textLogin,textForgetPass;
    private CheckBox chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        findview();
    }

    private void findview() {
        chk =findViewById(R.id.chkPass);
        textTitle = findViewById(R.id.textTitleActivity);
        textForgetPass = findViewById(R.id.textForgetPassword);
        textRegister = findViewById(R.id.textRegister);
        textTitle.setText("ورود");
        edemail = findViewById(R.id.edEmailLogin);
        edpass = findViewById(R.id.edPasswordLogin);
        textLogin = findViewById(R.id.textLogin);
        imageBack = findViewById(R.id.imageBack);
        onclick();
    }

    private void onclick() {
        textForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this,Activity_ForgetPassword.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if (chk.isChecked()) {
                            edpass.setInputType(InputType.TYPE_CLASS_TEXT);
                        } else {
                            edpass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                        }
                    }


        });
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Activity_Login.this, "ثبت نام", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity_Login.this, Activity_Register.class);
                startActivity(intent);

            }
        });
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(edemail.getText().toString(), edpass.getText().toString());
            }
        });
    }

    private void login(final String email, final String pass) {

        String url = Link.linkLogin;
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Login.this);
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("1")) {
                    Toast.makeText(Activity_Login.this, "رمز عبور یا ایمیل اشتباه است", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    String image = response.toString();
                    int emailsplit =email.length();
                    int responsesplite=image.length();
                    String split = image.substring(emailsplit,responsesplite);
                   // Toast.makeText(Activity_Login.this,split, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra(put.email, email);
                    intent.putExtra(put.image,split);
                    setResult(RESULT_OK, intent);
                    finish();
                    progressDialog.dismiss();
                }


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(put.email, email);
                map.put(put.password, pass);
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
