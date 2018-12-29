package com.ismartapp.lenovo.ismart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import com.facebook.ads.*;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
//import com.startapp.android.publish.adsCommon.StartAppAd;
//import com.startapp.android.publish.adsCommon.StartAppSDK;
import java.util.ArrayList;


import Interface.ItemClickListener;

public class fund_list extends AppCompatActivity {

    private ProgressDialog Dialog;
    private Toolbar toolbar;
    private TextView quote;
    Context context=this;
    //ad
    private AdView adView;
    private final String TAG1 = InterstitialAdActivity.class.getSimpleName();
    private InterstitialAd interstitialAd;


    private DatabaseReference listref_basic,listref_advanced,refToolbar;
    String fundid = "";
    String mainid = "";

    private static final String TAG = "MyActivity";
    RecyclerView list_recyclerview;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // StartAppSDK.init(this, "207633994", false);
       // StartAppAd.disableSplash();
       // StartAppSDK.setUserConsent (this, "pas",System.currentTimeMillis(), true);
        setContentView(R.layout.activity_fund_list);

        //show fb ads
        interstitialAd = new InterstitialAd(this, "2358508427504518_2358511147504246");
        showad();
        //banner1
        adView = new AdView(this, "2358508427504518_2367662606589100", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container1);

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

        list_recyclerview = (RecyclerView) findViewById(R.id.list_RecyclerView);
        list_recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list_recyclerview.setLayoutManager(layoutManager);
        toolbar=(Toolbar)findViewById(R.id.main_toolbar3);
        quote=(TextView)findViewById(R.id.list_quote);


        if (getIntent() != null) {
            Log.i(TAG, "inside 1st if statement ");
            fundid = getIntent().getStringExtra("FundId");
            mainid = getIntent().getStringExtra("mainID");

            listref_basic = FirebaseDatabase.getInstance().getReference().child(mainid).child("Basic");
            listref_advanced = FirebaseDatabase.getInstance().getReference().child(mainid).child("Advanced");
        }

        if (!(fundid.isEmpty()&&mainid.isEmpty())) {
             if(fundid.equals("04")||fundid.equals("05")||fundid.equals("06")||fundid.equals("07")||fundid.equals("08"))
                getList(fundid, listref_advanced);
             else
                 getList(fundid, listref_basic);
        }

        }





    private void showad() {
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();

    }
    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void getList(final String fundId, DatabaseReference DR) {


        if (checkinternet()) {
            //toolbar
            //StartAppAd.showAd(this);
            refToolbar = DR.child(fundid);
            refToolbar.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String toolvalue = dataSnapshot.child("Title").getValue(String.class);
                    String quotevalue = dataSnapshot.child("Quote").getValue(String.class);
                    Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar3);
                    toolbar.setTitle(toolvalue);
                    setActionBar(toolbar);
                    quote.setText(quotevalue);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            DatabaseReference CDR = DR.child(fundId).child("List");

            FirebaseRecyclerOptions<List> options =
                    new FirebaseRecyclerOptions.Builder<List>()
                            .setQuery(CDR, List.class)
                            .build();
            Log.i(TAG, "after options ");
            adapter = new FirebaseRecyclerAdapter<List, List_holder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull List_holder holder, int position, @NonNull List model) {
                    holder.setName(model.getName());
                    holder.setList_image(getApplicationContext(), model.getList_image());
                    setActionBar(toolbar);
                    Log.i(TAG, "inside onbind");
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {

                            Intent listDetail = new Intent(context, Detail_fund.class);
                            listDetail.putExtra("ListId", adapter.getRef(position).getKey());
                            listDetail.putExtra("FundId", fundId);
                            listDetail.putExtra("MainID", mainid);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                context.startForegroundService(listDetail);
                                startActivity(listDetail);
                            } else {
                                context.startService(listDetail);
                                startActivity(listDetail);
                            }

                        }
                    });
                }

                @NonNull
                @Override
                public List_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_card, parent, false);

                    return new List_holder(view);

                }
            };
            list_recyclerview.setAdapter(adapter);
            adapter.startListening();
            adapter.notifyDataSetChanged();
            Log.i(TAG, "after set adapter ");

        }
    }
    @Override
    protected void onStart() {
        Log.e(TAG,"Inside onStart");
        super.onStart();
        //adapter.startListening();

    }

    @Override
    protected void onStop() {
        Log.e(TAG,"Inside onStop");

        super.onStop();
        //adapter.stopListening();
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

