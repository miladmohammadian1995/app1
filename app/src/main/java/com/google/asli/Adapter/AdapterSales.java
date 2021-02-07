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
import com.google.asli.Model.ModelBestSales;
import com.google.asli.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class AdapterSales extends RecyclerView.Adapter<AdapterSales.viewHolder> {

    Context context;
    List<ModelBestSales> modelBestSales;

    public AdapterSales(Context context, List<ModelBestSales> modelBestSales) {
        this.context = context;
        this.modelBestSales = modelBestSales;
    }

    @NonNull
    @Override
    public AdapterSales.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutbestsales,parent,false);
        return new AdapterSales.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterSales.viewHolder viewHolder, int i) {

        final ModelBestSales sales = modelBestSales.get(i);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(sales.getPrice()));
        viewHolder.textprice.setText(price+" "+"تومان");
        viewHolder.textvisit.setText(sales.getVisit());
        viewHolder.texttitle.setText(sales.getTitle());
        viewHolder.lnrShimmer.setVisibility(View.GONE);
        viewHolder.shimmerFrameLayout.startShimmer();
        if (sales.getImage()!=null)
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewHolder.shimmerFrameLayout.stopShimmer();
                    viewHolder.shimmerFrameLayout.setVisibility(View.GONE);
                    viewHolder.lnrShimmer.setVisibility(View.VISIBLE);
                    Picasso.with(context)
                            .load(sales.getImage())
                            .skipMemoryCache()
                            .into(viewHolder.imageViewfree);
                }
            },1000);

        }
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdapterSales.this.context, Activity_Wait.class);
                intent.putExtra(put.id,sales.getId()+"");
                intent.putExtra(put.freeprice,"");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelBestSales.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ShimmerFrameLayout shimmerFrameLayout;
        LinearLayout lnrShimmer;
        CardView cardView;
        TriangleLabelView labelView;
        ImageView imageViewfree;
        TextView texttitle,textvisit,textprice;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Vazir-Medium-FD-WOL.ttf");

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            lnrShimmer = itemView.findViewById(R.id.lnrshimmer);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer);
            cardView = itemView.findViewById(R.id.cardviewsales);
            imageViewfree = itemView.findViewById(R.id.imagesales);
            texttitle = itemView.findViewById(R.id.texttitlesales);
            texttitle.setTypeface(typeface);
            textvisit = itemView.findViewById(R.id.textvisitsales);
            textvisit.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textpricesales);
            textprice.setTypeface(typeface);
        }
    }
}
