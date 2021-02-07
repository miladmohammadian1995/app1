package com.google.asli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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


import com.google.asli.Class.put;

import com.google.asli.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.asli.Activity.Activity_Wait;
import com.google.asli.Model.ModelFree;
import com.google.asli.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class AdapterFree extends RecyclerView.Adapter<AdapterFree.viewHolder> {

    Context context;
List<ModelFree> modelFrees;

    public AdapterFree(Context context, List<ModelFree> modelFrees) {
        this.context = context;
        this.modelFrees = modelFrees;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutfree,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, int i) {

        final ModelFree free = modelFrees.get(i);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(free.getFreeprice()));
        String price2 = decimalFormat.format(Integer.valueOf(free.getPrice()));
        viewHolder.textfree.setPaintFlags(viewHolder.textfree.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.textfree.setText(price2+" "+"تومان");
        viewHolder.textprice.setText(price+" "+"تومان");
        viewHolder.textvisit.setText(free.getVisit());
        viewHolder.texttitle.setText(free.getTitle());
        viewHolder.labelView.setSecondaryText(free.getLabel()+"%");

        viewHolder.lnrShimmer.setVisibility(View.GONE);
            viewHolder.shimmerFrameLayout.startShimmer();
            if (free.getImage()!=null)
            {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.shimmerFrameLayout.stopShimmer();
                        viewHolder.shimmerFrameLayout.setVisibility(View.GONE);
                        viewHolder.lnrShimmer.setVisibility(View.VISIBLE);
                        Picasso.with(context)
                                .load(free.getImage())
                                .skipMemoryCache()
                                .into(viewHolder.imageViewfree);
                    }
                },1000);

        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(context,free.getId()+"" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdapterFree.this.context, Activity_Wait.class);
                intent.putExtra(put.id,free.getId()+"");
                intent.putExtra(put.label,free.getLabel());
                intent.putExtra(put.freeprice,free.getFreeprice());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // ((Activity)context).overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelFrees.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ShimmerFrameLayout shimmerFrameLayout;
        LinearLayout lnrShimmer;
        CardView cardView;
        TriangleLabelView labelView;
        ImageView imageViewfree;
        TextView texttitle,textvisit,textprice,textfree;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Vazir-Medium-FD-WOL.ttf");

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            lnrShimmer = itemView.findViewById(R.id.lnrshimmer);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer);
            cardView = itemView.findViewById(R.id.cardviewfree);
            labelView = itemView.findViewById(R.id.label);

            imageViewfree = itemView.findViewById(R.id.imagefree);
            texttitle = itemView.findViewById(R.id.texttitle);
            texttitle.setTypeface(typeface);
            textvisit = itemView.findViewById(R.id.textvisitfree);
            textvisit.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textpricefree);
            textprice.setTypeface(typeface);
            textfree = itemView.findViewById(R.id.textfreeprice);
            textfree.setTypeface(typeface);
        }
    }
}
