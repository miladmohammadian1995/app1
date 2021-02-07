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
import com.google.asli.Model.ModelVisit;
import com.google.asli.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterVisit extends RecyclerView.Adapter<AdapterVisit.viewHolder> {

    Context context;
    List<ModelVisit> modelVisits;

    public AdapterVisit(Context context, List<ModelVisit> modelVisits) {
        this.context = context;
        this.modelVisits = modelVisits;
    }

    @NonNull
    @Override
    public AdapterVisit.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutvisit,parent,false);
        return new AdapterVisit.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterVisit.viewHolder viewHolder, int i) {

        final ModelVisit visit = modelVisits.get(i);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(visit.getPrice()));
        viewHolder.textprice.setText(price+" "+"تومان");
        viewHolder.textvisit.setText(visit.getVisit());
        viewHolder.texttitle.setText(visit.getTitle());
        viewHolder.lnrShimmer.setVisibility(View.GONE);
        viewHolder.shimmerFrameLayout.startShimmer();
        if (visit.getImage()!=null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewHolder.shimmerFrameLayout.stopShimmer();
                    viewHolder.shimmerFrameLayout.setVisibility(View.GONE);
                    viewHolder.lnrShimmer.setVisibility(View.VISIBLE);
                    Picasso.with(context)
                            .load(visit.getImage())
                            .skipMemoryCache()
                            .into(viewHolder.imageViewfree);
                }
            }, 1000);
        }
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdapterVisit.this.context, Activity_Wait.class);
                intent.putExtra(put.id,visit.getId()+"");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(put.freeprice,"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelVisits.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        LinearLayout lnrShimmer;
        ShimmerFrameLayout shimmerFrameLayout;
        CardView cardView;

        ImageView imageViewfree;
        TextView texttitle,textvisit,textprice;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Vazir-Medium-FD-WOL.ttf");

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            lnrShimmer = itemView.findViewById(R.id.lnrshimmer);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer);
            cardView = itemView.findViewById(R.id.cardviewvisit);
            imageViewfree = itemView.findViewById(R.id.imagevisit);
            texttitle = itemView.findViewById(R.id.texttitlevisit);
            texttitle.setTypeface(typeface);
            textvisit = itemView.findViewById(R.id.textvisitvisit);
            textvisit.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textpricevisit);
            textprice.setTypeface(typeface);
        }
    }
}
