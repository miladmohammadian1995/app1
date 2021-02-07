package com.google.asli.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.asli.R;

import mehdi.sakout.aboutpage.AboutPage;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity__about);

        View aboutPage = new AboutPage(this)
                .isRTL(true)
                .setImage(R.drawable.banner3)
                // .addItem(versionElement)
                // .addItem(adsElement)
                .setDescription("اپلیکیشن فروشگاهی ساده")
                .addGroup("ارتباط با ما")
                .addEmail("miladmohammadian1995@gmail.com")
                .addWebsite("http://varzesh3.com/")
                .addInstagram("milad_mohammadyann")
                .create();
        setContentView(aboutPage);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

}
