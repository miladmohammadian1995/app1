package com.google.asli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.asli.Activity.Activity_Item;
import com.google.asli.Class.put;
import com.google.asli.Model.ModelItemCategory;
import com.google.asli.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterItemCategory extends RecyclerView.Adapter<AdapterItemCategory.viewHolder> {


    Context context;
    List<ModelItemCategory> itemCategories;

    public AdapterItemCategory(Context context, List<ModelItemCategory> itemCategories) {
        this.context = context;
        this.itemCategories = itemCategories;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutitemcategory, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        final ModelItemCategory category = itemCategories.get(i);
        viewHolder.textView.setText(category.getTitle());
        Picasso
                .with(context)
                .load(category.getImage())
                .into(viewHolder.circleImageView);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdapterItemCategory.this.context, Activity_Item.class);
                intent.putExtra(put.id, category.getId() + "");
                intent.putExtra(put.title, category.getTitle());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemCategories.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView circleImageView;
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardItemcategory);
            circleImageView = itemView.findViewById(R.id.imageitemcategory);
            textView = itemView.findViewById(R.id.textitemcategory);
        }
    }
}
