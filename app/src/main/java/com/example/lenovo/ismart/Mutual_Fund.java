package com.example.lenovo.ismart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

import com.example.lenovo.ismart.R;
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
    private String THeading;

    private TextView mf_basic,mf_advanced;
    private RecyclerView basic_rv, advanced_rv;
    private DatabaseReference basic_D,advanced_D,mRef;
    private static final String TAG = "MyActivity";
    String mainID="";
    FirebaseRecyclerAdapter firebaseRecyclerAdapter1,firebaseRecyclerAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual__fund);

        mf_basic=(TextView)findViewById(R.id.MFbasic);
        mf_advanced=(TextView)findViewById(R.id.MFadvanced);
        toolbar=(Toolbar)findViewById(R.id.main_toolbar2);


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
        if (!mainID.isEmpty())
        {
               basic_D = FirebaseDatabase.getInstance().getReference().child(mainID).child("Basic");
               basic_D.keepSynced(true);
               advanced_D = FirebaseDatabase.getInstance().getReference().child(mainID).child("Advanced");
               advanced_D.keepSynced(true);
               }



                mRef = FirebaseDatabase.getInstance().getReference().child(mainID);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String basic=dataSnapshot.child("Heading").child("MF_Basic").getValue(String.class);
                String advanced=dataSnapshot.child("Heading").child("MF_Advanced").getValue(String.class);
                mf_basic.setText(basic);
                mf_advanced.setText(advanced);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseRecyclerOptions<Fund> options1 =
                new FirebaseRecyclerOptions.Builder<Fund>()
                        .setQuery(basic_D, Fund.class)
                        .build();
        FirebaseRecyclerOptions<Fund> options2 =
                new FirebaseRecyclerOptions.Builder<Fund>()
                        .setQuery(advanced_D, Fund.class)
                        .build();

       Load_recyclerview(options1,options2,basic_rv,advanced_rv);
        Log.i(TAG, "inside onCreate ");
    }


    public void Load_recyclerview(FirebaseRecyclerOptions<Fund> o1,FirebaseRecyclerOptions<Fund> o2,RecyclerView r1,RecyclerView r2)
    {

            Log.i(TAG, "after options ");
            Dialog = new ProgressDialog(Mutual_Fund.this);
            Dialog.setMessage("Happy Earning...");
            Dialog.show();

            firebaseRecyclerAdapter1 = new FirebaseRecyclerAdapter<Fund, MF_Holder>(o1) {
                @Override
                protected void onBindViewHolder(@NonNull MF_Holder holder, int position, @NonNull Fund model) {
                    THeading=model.getTitle();
                    holder.setTitle(model.getTitle());
                    holder.setImage(getApplicationContext(), model.getImage());
                    Log.i(TAG, "inside on bind ");



                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {

                            Intent clothDetail = new Intent(Mutual_Fund.this,fund_list.class);
                            clothDetail.putExtra("FundId",firebaseRecyclerAdapter1.getRef(position).getKey());
                            clothDetail.putExtra("mainID",mainID);
                            clothDetail.putExtra("ToolbarTitle",THeading);
                            startActivity(clothDetail);

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
                THeading=model.getTitle();
                holder.setTitle(model.getTitle());
                holder.setImage(getApplicationContext(), model.getImage());
                Log.i(TAG, "inside on bind ");
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent clothDetail = new Intent(Mutual_Fund.this,fund_list.class);
                        clothDetail.putExtra("FundId",firebaseRecyclerAdapter2.getRef(position).getKey());
                        clothDetail.putExtra("mainID",mainID);
                        clothDetail.putExtra("ToolbarTitle",THeading);
                        startActivity(clothDetail);

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
    @Override
    protected void onStart() {
        Log.e("home","Inside onStart");
        super.onStart();
        firebaseRecyclerAdapter1.startListening();
        firebaseRecyclerAdapter2.startListening();

    }

    @Override
    protected void onStop() {
        Log.e("home","Inside onStop");

        super.onStop();
        firebaseRecyclerAdapter1.stopListening();
        firebaseRecyclerAdapter1.stopListening();
    }

}