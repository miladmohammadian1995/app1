package com.google.asli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.asli.Activity.Activity_Wait;
import com.google.asli.Class.put;
import com.google.asli.Model.ModelOnly;
import com.google.asli.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class AdapterOnly  extends RecyclerView.Adapter<AdapterOnly.viewHolder> {

    Context context;
    List<ModelOnly> modelOnlies;

    public AdapterOnly(Context context, List<ModelOnly> modelOnlies) {
        this.context = context;
        this.modelOnlies = modelOnlies;
    }

    @NonNull
    @Override
    public AdapterOnly.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutonly,parent,false);
        return new AdapterOnly.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterOnly.viewHolder viewHolder, int i) {

        final ModelOnly only = modelOnlies.get(i);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(only.getPrice()));
        viewHolder.textprice.setText(price+" "+"تومان");
        viewHolder.textvisit.setText(only.getVisit());
        viewHolder.texttitle.setText(only.getTitle());

        viewHolder.lnrShimmer.setVisibility(View.GONE);
        viewHolder.shimmerFrameLayout.startShimmer();

        if (only.getImage()!=null)
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewHolder.shimmerFrameLayout.stopShimmer();
                    viewHolder.shimmerFrameLayout.setVisibility(View.GONE);
                    viewHolder.lnrShimmer.setVisibility(View.VISIBLE);
                    Picasso.with(context)
                            .load(only.getImage())
                            .skipMemoryCache()
                            .into(viewHolder.imageViewfree);
                }
            },1000);

        }


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdapterOnly.this.context, Activity_Wait.class);
                intent.putExtra(put.id,only.getId()+"");
                intent.putExtra(put.freeprice,"");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelOnlies.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ShimmerFrameLayout shimmerFrameLayout;
        LinearLayout lnrShimmer;
        TriangleLabelView labelView;
        ImageView imageViewfree;
        TextView texttitle,textvisit,textprice;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Vazir-Medium-FD-WOL.ttf");

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            lnrShimmer = itemView.findViewById(R.id.lnrshimmer);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer);
            cardView = itemView.findViewById(R.id.cardviewonly);
            imageViewfree = itemView.findViewById(R.id.imageonly);
            texttitle = itemView.findViewById(R.id.texttitleonly);
            texttitle.setTypeface(typeface);
            textvisit = itemView.findViewById(R.id.textvisitonly);
            textvisit.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textpriceonly);
            textprice.setTypeface(typeface);
        }
    }
}
