package com.google.asli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.asli.Activity.Activity_Wait;
import com.google.asli.Class.put;
import com.google.asli.Model.ModelFav;
import com.google.asli.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFav extends RecyclerView.Adapter<AdapterFav.viewHolder>{


    Context context;
    List<ModelFav> modelFavs;

    public AdapterFav(Context context, List<ModelFav> modelFavs) {
        this.context = context;
        this.modelFavs = modelFavs;
    }

    @NonNull
    @Override
    public AdapterFav.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutitem,parent,false);

        return new AdapterFav.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFav.viewHolder viewHolder, int i) {

        final ModelFav item = modelFavs.get(i);
        viewHolder.textfreeprice.setVisibility(View.INVISIBLE);
        viewHolder.textvisite.setText(item.getVisit());
        viewHolder.texttitle.setText(item.getTitle());;
        viewHolder.textprice.setText(item.getPrice()+" "+"تومان");


        if (item.getLabel().equals("0"))
        {
            viewHolder.textfreeprice.setVisibility(View.GONE);
            viewHolder.textprice.setTextColor(Color.GREEN);
        }
        else
        {
            viewHolder.textprice.setTextColor(Color.RED);
            viewHolder.textprice.setPaintFlags(viewHolder.textprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.textfreeprice.setVisibility(View.VISIBLE);
            viewHolder.textfreeprice.setText(item.getFreeprice()+" "+"تومان");
            viewHolder.textfreeprice.setTextColor(Color.GREEN);
        }
        Picasso
                .with(context)
                .load(item.getImage())
                .into(viewHolder.imageView);
//        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, item.getId()+"", Toast.LENGTH_SHORT).show();
//            }
//        });

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdapterFav.this.context,Activity_Wait.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(put.id,item.getId()+"");
                int freeprice= Integer.parseInt(item.getFreeprice());
                int price = Integer.parseInt(item.getPrice());
                if (freeprice==price)
                {
                    intent.putExtra(put.freeprice,"");
                }
                else {
                    intent.putExtra(put.freeprice, item.getFreeprice());
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelFavs.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView texttitle,textvisite,textprice,textfreeprice;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Vazir-Medium-FD-WOL.ttf");

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewitem);
            imageView = itemView.findViewById(R.id.imageitem);
            textfreeprice = itemView.findViewById(R.id.textfreeitemfree);
            textfreeprice.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.textitemprice);
            textprice.setTypeface(typeface);
            texttitle = itemView.findViewById(R.id.texttitleitem);
            texttitle.setTypeface(typeface);
            textvisite = itemView.findViewById(R.id.textvisiteitem);
            textvisite.setTypeface(typeface);
        }
    }
}
