package com.ismartapp.lenovo.ismart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Interface.ItemClickListener;

public class MF_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mview;
    private ItemClickListener itemClickListener;


    public MF_Holder(View itemView) {
        super(itemView);
        mview = itemView;
        itemView.setOnClickListener(this);
    }

    public void setSection(String section) {
        TextView post_sections = (TextView) mview.findViewById(R.id.post_sections);
        post_sections.setText(section);

    }

    public void setTitle(String title) {
        TextView post_title = (TextView) mview.findViewById(R.id.post_title);
        post_title.setText(title);

    }

    public void setImage(Context ctx, String image) {
        ImageView post_image = (ImageView) mview.findViewById(R.id.post_image);
        Picasso.with(ctx).load(image).into(post_image);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }
    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}


