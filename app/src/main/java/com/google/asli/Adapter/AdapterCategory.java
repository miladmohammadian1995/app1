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


import com.google.asli.Activity.Activity_Item_Category;
import com.google.asli.Class.put;
import com.google.asli.R;
import com.google.asli.Model.ModelCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.viewHolder> {

    Context context;
    List<ModelCategory> modelCategories;

    public AdapterCategory(Context context, List<ModelCategory> modelCategories) {
        this.context = context;
        this.modelCategories = modelCategories;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutcategory,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        final ModelCategory category = modelCategories.get(i);
        viewHolder.textView.setText(category.getTitlecategory());
        Picasso
                .with(context)
                .load(category.getImage())
                .skipMemoryCache()
                .into(viewHolder.imageView);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  =new Intent(AdapterCategory.this.context, Activity_Item_Category.class);
                intent.putExtra(put.id,category.getId()+"");
                intent.putExtra(put.title,category.getTitlecategory());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
               // Toast.makeText(context, category.getId()+"", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelCategories.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView textView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardviewCategory);
            imageView = itemView.findViewById(R.id.imageCategory);
            textView = itemView.findViewById(R.id.texttilteCtegory);
        }
    }
}
