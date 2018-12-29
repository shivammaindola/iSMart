package com.ismartapp.lenovo.ismart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.ads.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
//import com.startapp.android.publish.adsCommon.StartAppAd;
//import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.facebook.ads.*;

public class Detail_fund extends AppCompatActivity {
    //progress
    private ProgressDialog Dialog;

    TextView text_detail,text_heading;
    ImageView text_image;
    ImageView home_image;
    Context context=this;

    List currentlist;
    private AdView adView;
    String TAG="";

    String listid = "";
    String fundid = "";
    String mainid="";

    DatabaseReference textrefbasic,textrefadvanced;
    //font
    Typeface tf1,tf2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StartAppSDK.init(this, "207633994", false);
        //StartAppAd.disableSplash();
        //StartAppSDK.setUserConsent (this, "pas",System.currentTimeMillis(), true);
        setContentView(R.layout.activity_detail_fund);

        //bannerad
        adView = new AdView(this, "2358508427504518_2367604156594945", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container2);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });

        // Request an ad
        adView.loadAd();

        text_detail=(TextView)findViewById(R.id.text_detail);
        text_heading=(TextView)findViewById(R.id.text_heading);
        text_image=(ImageView)findViewById(R.id.text_image);
        home_image=(ImageView)findViewById(R.id.home_image);

        tf1=Typeface.createFromAsset(getAssets(),"TiemposText-Regular.ttf");
        tf2=Typeface.createFromAsset(getAssets(),"Tiempos Headline-Black.ttf");
        text_detail.setTypeface(tf1);
        text_heading.setTypeface(tf2);

        home_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchNextActivity;
                launchNextActivity = new Intent(Detail_fund.this, Navigation_drawer_main.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(launchNextActivity);
                    startActivity(launchNextActivity);
                } else {
                    context.startService(launchNextActivity);
                    startActivity(launchNextActivity);
                }
            }
        });


        if (getIntent() != null) {
            listid = getIntent().getStringExtra("ListId");
            fundid = getIntent().getStringExtra("FundId");
            mainid = getIntent().getStringExtra("MainID");

            textrefbasic=FirebaseDatabase.getInstance().getReference().child(mainid).child("Basic");
            textrefadvanced=FirebaseDatabase.getInstance().getReference().child(mainid).child("Advanced");

        }
        if (!(listid.isEmpty()&&fundid.isEmpty()&&mainid.isEmpty())){
            if(fundid.equals("04")||fundid.equals("05")||fundid.equals("06")||fundid.equals("07")||fundid.equals("08"))
                getdetail(fundid,listid,textrefadvanced);
            else
                getdetail(fundid,listid,textrefbasic);
        }


    }
    public void getdetail(String fid,String lid, DatabaseReference DR) {

        if (checkinternet()) {
            //Ad
           // StartAppAd.showAd(this);
            DatabaseReference CDR = DR.child(fid).child("List").child(lid);
            CDR.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    currentlist = dataSnapshot.getValue(List.class);
                    //set image
                    Picasso.with(getBaseContext()).load(currentlist.getList_image()).into(text_image);
                    text_heading.setText(currentlist.getName());
                    text_detail.setText(currentlist.getText());


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }


    //connection
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
            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
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
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}
