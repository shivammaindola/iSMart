package com.ismartapp.lenovo.ismart;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import com.facebook.ads.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ismartapp.lenovo.ismart.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Interface.ItemClickListener;


public class Mutual_Fund extends AppCompatActivity {
    private ProgressDialog Dialog;
    private Toolbar toolbar;
    Context context = this;
    private TextView mf_basic,mf_advanced,quote;
    private RecyclerView basic_rv, advanced_rv;
    private DatabaseReference basic_D,advanced_D,mRef;
    private static final String TAG = "MyActivity";
    String mainID="";
    FirebaseRecyclerAdapter firebaseRecyclerAdapter1,firebaseRecyclerAdapter2;

    //fb ads
  //  private InterstitialAd interstitialAd;
   // private final String TAG1 = InterstitialAdActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual__fund);

        mf_basic = (TextView) findViewById(R.id.MFbasic);
        mf_advanced = (TextView) findViewById(R.id.MFadvanced);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar2);
        quote = (TextView) findViewById(R.id.MFQuote);
        //ads

        //interstitialAd = new InterstitialAd(this, "2358508427504518_2358511147504246");


            //initialize basic
            basic_rv = (RecyclerView) findViewById(R.id.Basic_RecyclerView);
            basic_rv.setHasFixedSize(true);
            basic_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            //initialize advanced
            advanced_D = FirebaseDatabase.getInstance().getReference().child("Advanced");
            advanced_D.keepSynced(true);
            advanced_rv = (RecyclerView) findViewById(R.id.Advanced_RecyclerView);
            advanced_rv.setHasFixedSize(true);
            advanced_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            //initialize headings
            if (getIntent() != null) {
                mainID = getIntent().getStringExtra("ID");
                //toolbar
                toolbar.setTitle(mainID);
                setActionBar(toolbar);

            }
            if (!mainID.isEmpty()) {
                basic_D = FirebaseDatabase.getInstance().getReference().child(mainID).child("Basic");
                basic_D.keepSynced(true);
                advanced_D = FirebaseDatabase.getInstance().getReference().child(mainID).child("Advanced");
                advanced_D.keepSynced(true);
            }


            FirebaseRecyclerOptions<Fund> options1 =
                    new FirebaseRecyclerOptions.Builder<Fund>()
                            .setQuery(basic_D, Fund.class)
                            .build();
            FirebaseRecyclerOptions<Fund> options2 =
                    new FirebaseRecyclerOptions.Builder<Fund>()
                            .setQuery(advanced_D, Fund.class)
                            .build();

            Load_recyclerview(options1, options2, basic_rv, advanced_rv);
            Log.i(TAG, "inside onCreate ");
        }


    public void Load_recyclerview(FirebaseRecyclerOptions<Fund> o1,FirebaseRecyclerOptions<Fund> o2,RecyclerView r1,RecyclerView r2) {


            Log.i(TAG, "after options ");
            Dialog = new ProgressDialog(Mutual_Fund.this);
            Dialog.setMessage("Happy Investing...");
            Dialog.show();
        if (checkinternet()) {



            headings(mainID);
            firebaseRecyclerAdapter1 = new FirebaseRecyclerAdapter<Fund, MF_Holder>(o1) {
                @Override
                protected void onBindViewHolder(@NonNull MF_Holder holder, int position, @NonNull Fund model) {
                    holder.setSection(model.getSections());
                    holder.setTitle(model.getTitle());
                    holder.setImage(getApplicationContext(), model.getImage());
                    Log.i(TAG, "inside on bind ");


                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {

                            Intent clothDetail = new Intent(context, fund_list.class);
                            clothDetail.putExtra("FundId", firebaseRecyclerAdapter1.getRef(position).getKey());
                            clothDetail.putExtra("mainID", mainID);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                context.startForegroundService(clothDetail);
                                startActivity(clothDetail);
                            } else {
                                context.startService(clothDetail);
                                startActivity(clothDetail);
                            }

                        }
                    });
                    Dialog.hide();
                }

                @NonNull
                @Override
                public MF_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.common_card, parent, false);
                    Log.i(TAG, "inside oncreate ");
                    return new MF_Holder(view);

                }
            };
            r1.setAdapter(firebaseRecyclerAdapter1);
            firebaseRecyclerAdapter1.startListening();
            firebaseRecyclerAdapter1.notifyDataSetChanged();
            Log.i(TAG, "after setadapter ");

            //for advanced
            firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter<Fund, MF_Holder>(o2) {
                @Override
                protected void onBindViewHolder(@NonNull MF_Holder holder, int position, @NonNull Fund model) {
                    holder.setSection(model.getSections());
                    holder.setTitle(model.getTitle());
                    holder.setImage(getApplicationContext(), model.getImage());
                    Log.i(TAG, "inside on bind ");
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {

                            Intent clothDetail = new Intent(context, fund_list.class);
                            clothDetail.putExtra("FundId", firebaseRecyclerAdapter2.getRef(position).getKey());
                            clothDetail.putExtra("mainID", mainID);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                context.startForegroundService(clothDetail);
                                startActivity(clothDetail);
                            } else {
                                context.startService(clothDetail);
                                startActivity(clothDetail);
                            }
                        }
                    });
                }

                @NonNull
                @Override
                public MF_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.common_card, parent, false);
                    Log.i(TAG, "inside oncreate ");
                    return new MF_Holder(view);

                }
            };
            r2.setAdapter(firebaseRecyclerAdapter2);
            firebaseRecyclerAdapter2.startListening();
            firebaseRecyclerAdapter2.notifyDataSetChanged();
            Log.i(TAG, "after setadapter ");
        }
    }
    @Override
    protected void onStart() {
        Log.e("home","Inside onStart");
        super.onStart();
//        firebaseRecyclerAdapter1.startListening();
  //      firebaseRecyclerAdapter2.startListening();

    }

    @Override
    protected void onStop() {
        Log.e("home","Inside onStop");

        super.onStop();
       // firebaseRecyclerAdapter1.stopListening();
        //firebaseRecyclerAdapter1.stopListening();
    }

    //set headings
    public void headings(String mainid){

        mRef = FirebaseDatabase.getInstance().getReference().child(mainID);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String basic = dataSnapshot.child("Heading").child("MF_Basic").getValue(String.class);
                String advanced = dataSnapshot.child("Heading").child("MF_Advanced").getValue(String.class);
                String quotevalue = dataSnapshot.child("Quote").getValue(String.class);
                mf_basic.setText(basic);
                mf_advanced.setText(advanced);
                quote.setText(quotevalue);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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