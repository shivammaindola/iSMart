package com.example.lenovo.ismart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Detail_fund extends AppCompatActivity {
    //progress
    private ProgressDialog Dialog;

    TextView text_detail,text_heading;
    ImageView text_image;
    ImageView home_image;

    List currentlist;

    String listid = "";
    String fundid = "";
    String mainid="";

    DatabaseReference textrefbasic,textrefadvanced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fund);

        text_detail=(TextView)findViewById(R.id.text_detail);
        text_heading=(TextView)findViewById(R.id.text_heading);
        text_image=(ImageView)findViewById(R.id.text_image);
        home_image=(ImageView)findViewById(R.id.home_image);

        home_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchNextActivity;
                launchNextActivity = new Intent(Detail_fund.this, MainActivity.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(launchNextActivity);
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
            if(fundid.equals("04")||fundid.equals("05")||fundid.equals("06"))
                getdetail(fundid,listid,textrefadvanced);
            else
                getdetail(fundid,listid,textrefbasic);
        }


    }
    public void getdetail(String fid,String lid, DatabaseReference DR){
        //progress
        Dialog = new ProgressDialog(Detail_fund.this);
        Dialog.setMessage("Happy Earning...");
        Dialog.show();

        DatabaseReference CDR = DR.child(fid).child("List").child(lid);
        CDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentlist = dataSnapshot.getValue(List.class);
                //set image
                Picasso.with(getBaseContext()).load(currentlist.getList_image()).into(text_image);
                text_heading.setText(currentlist.getName());
                text_detail.setText(currentlist.getText());

                Dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
