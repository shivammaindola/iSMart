package com.ismartapp.lenovo.ismart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Interface.ItemClickListener;

public class List_holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mview;
    private ItemClickListener itemClickListener;


    public List_holder(View itemView) {
        super(itemView);
        mview = itemView;
        itemView.setOnClickListener(this);
    }

    public void setName(String name) {
        TextView list_title = (TextView) mview.findViewById(R.id.list_title);
        list_title.setText(name);
        }

    public void setList_image(Context ctx, String image) {
        ImageView list_image = (ImageView) mview.findViewById(R.id.list_image);
        Picasso.with(ctx).load(image).resize(20,20).into(list_image);
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }
    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}


