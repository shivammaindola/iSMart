package com.example.lenovo.ismart;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

import java.util.ArrayList;

import Interface.ItemClickListener;

public class fund_list extends AppCompatActivity {

    private ProgressDialog Dialog;
    private Toolbar toolbar;

    private DatabaseReference listref_basic,listref_advanced;
    String fundid = "";
    String mainid = "";
    String Toolid="";

    private static final String TAG = "MyActivity";
    RecyclerView list_recyclerview;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_list);


        list_recyclerview = (RecyclerView) findViewById(R.id.list_RecyclerView);
        list_recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list_recyclerview.setLayoutManager(layoutManager);
        toolbar=(Toolbar)findViewById(R.id.main_toolbar3);


        if (getIntent() != null) {
            Log.i(TAG, "inside 1st if statement ");
            fundid = getIntent().getStringExtra("FundId");
            mainid = getIntent().getStringExtra("mainID");
            Toolid = getIntent().getStringExtra("ToolbarTitle");
            toolbar.setTitle(Toolid);
            setActionBar(toolbar);

            listref_basic = FirebaseDatabase.getInstance().getReference().child(mainid).child("Basic");
            listref_advanced = FirebaseDatabase.getInstance().getReference().child(mainid).child("Advanced");
        }

        if (!(fundid.isEmpty()&&mainid.isEmpty())) {
             if(fundid.equals("04")||fundid.equals("05")||fundid.equals("06"))
                getList(fundid, listref_advanced);
             else
                 getList(fundid, listref_basic);
        }

        }

    private void getList(final String fundId, DatabaseReference DR) {
        //progress
        Dialog = new ProgressDialog(fund_list.this);
        Dialog.setMessage("Happy Earning...");
        Dialog.show();

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

                        Intent listDetail = new Intent(fund_list.this,Detail_fund.class);
                        listDetail.putExtra("ListId",adapter.getRef(position).getKey());
                        listDetail.putExtra("FundId",fundId);
                        listDetail.putExtra("MainID",mainid);
                        startActivity(listDetail);

                    }
                });
                Dialog.hide();
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
    @Override
    protected void onStart() {
        Log.e(TAG,"Inside onStart");
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        Log.e(TAG,"Inside onStop");

        super.onStop();
        adapter.stopListening();
    }

}

