package com.ismartapp.lenovo.ismart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class VerticleViewPagerActivity extends AppCompatActivity {

    VerticleViewPager verticalViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verticle_view_pager);

        verticalViewPager = (VerticleViewPager) findViewById(R.id.verticleViewPager);
        verticalViewPager.setAdapter(new verticlepageadapter(this));
    }
}
