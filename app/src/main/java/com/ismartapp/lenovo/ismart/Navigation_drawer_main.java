package com.ismartapp.lenovo.ismart;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;


//import com.startapp.android.publish.adsCommon.StartAppAd;
//import com.startapp.android.publish.adsCommon.StartAppSDK;


public class Navigation_drawer_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Boolean exit = false;
    private Boolean  bs_netcheck=false;
    Context context = this;
    private ProgressDialog Dialog;

    private ImageView main_mutual_fund;
    private ImageView main_share_market;
    private ImageView main_cryptocurrency;
    ViewFlipper v_flipper;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  StartAppSDK.init(this, "207633994", false);
      //  StartAppAd.disableSplash();
      //  StartAppSDK.setUserConsent (this,
             //   "pas",
          //      System.currentTimeMillis(),
        //        true);
        setContentView(R.layout.activity_navigation_drawer_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("iSMart");
        setSupportActionBar(toolbar);

        //slidedot
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);
        dot_slide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //check c0nnection
        if (checkinternet()) {
            int images[] = {R.drawable.f3, R.drawable.f4, R.drawable.f1,R.drawable.f2};
            //v_flipper = findViewById(R.id.v_flipper);



            main_mutual_fund = (ImageView) findViewById(R.id.main_Mutual_Fund);
            main_share_market = (ImageView) findViewById(R.id.main_Share_market);
            main_cryptocurrency = (ImageView) findViewById(R.id.main_Cryptocurrency);

            main_mutual_fund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str = "Share Market";
                    Intent MIntent = new Intent(context, Mutual_Fund.class);
                    MIntent.putExtra("ID", str);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(MIntent);
                        startActivity(MIntent);
                    } else {
                        context.startService(MIntent);
                        startActivity(MIntent);
                    }
                }
            });
            main_share_market.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str = "Mutual Fund";
                    Intent MIntent = new Intent(context, Mutual_Fund.class);
                    MIntent.putExtra("ID", str);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(MIntent);
                        startActivity(MIntent);
                    } else {
                        context.startService(MIntent);
                        startActivity(MIntent);
                    }
                }
            });

            main_cryptocurrency.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str = "Cryptocurrency";
                    Intent MIntent = new Intent(context, Mutual_Fund.class);
                    MIntent.putExtra("ID", str);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(MIntent);
                        startActivity(MIntent);
                    } else {
                        context.startService(MIntent);
                        startActivity(MIntent);
                    }
                }
            });

//            //loop
//            for (int image : images) {
//                flipImage(image);
//            }

        }
    }

    private void dot_slide() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    /*public void flipImage(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(2600);
        v_flipper.setAutoStart(true);
        //animation
        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }
*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
              // StartAppAd.onBackPressed(this);
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
                }, 4000);
               // StartAppAd.onBackPressed(this);

            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_aboutus) {
            // Handle the about action
            Intent MIntent = new Intent(context, About.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(MIntent);
                startActivity(MIntent);
            } else {
                context.startService(MIntent);
                startActivity(MIntent);
            }

        } else if (id == R.id.nav_feedback) {
            Intent MIntent = new Intent(context, feedback.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(MIntent);
                startActivity(MIntent);
            } else {
                context.startService(MIntent);
                startActivity(MIntent);
            }

        } else if (id == R.id.nav_rateus) {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
              startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
            }

        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String sAux = "\nHey, Let me recommend you this app, download it and start Learning... \n\n";
            sAux = sAux + "http://play.google.com/store/apps/details?id=" + context.getPackageName();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Boolean checkinternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            return true;
        } else {
            //no connection
            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setTitle("No internet Connection");
            builder.setMessage("Please turn on internet connection to continue");
            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return false;
        }
    }
}
