package com.example.lenovo.ismart;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Handler;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Boolean exit = false;

    private ImageView main_mutual_fund;
    private ImageView main_share_market;
    private ImageView main_cryptocurrency;
    private ImageView main_real_estate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_mutual_fund = (ImageView) findViewById(R.id.main_Mutual_Fund);
        main_share_market = (ImageView) findViewById(R.id.main_Share_market);
        main_cryptocurrency = (ImageView) findViewById(R.id.main_Cryptocurrency);
        main_real_estate = (ImageView) findViewById(R.id.main_Real_Estate);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("iSmart");
        setActionBar(toolbar);

        main_mutual_fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "Mutual Fund";
                Intent MIntent = new Intent(MainActivity.this, Mutual_Fund.class);
                MIntent.putExtra("ID", str);
                startActivity(MIntent);
            }
        });
        main_share_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "Share Market";
                Intent MIntent = new Intent(MainActivity.this, Mutual_Fund.class);
                MIntent.putExtra("ID", str);
                startActivity(MIntent);
            }
        });

        main_cryptocurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "Cryptocurrency";
                Intent MIntent = new Intent(MainActivity.this, Mutual_Fund.class);
                MIntent.putExtra("ID", str);
                startActivity(MIntent);
            }
        });

        main_real_estate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "Real Estate";
                Intent MIntent = new Intent(MainActivity.this, Mutual_Fund.class);
                MIntent.putExtra("ID", str);
                startActivity(MIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent listDetail = new Intent(MainActivity.this, Settings.class);
                startActivity(listDetail);
                break;
            case R.id.action_updates:
                //help
            break;
            default:
                //help
        }
        return super.onOptionsItemSelected(item);
    }
}
