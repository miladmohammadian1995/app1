package com.google.asli.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.asli.Class.Link;

import com.google.asli.Class.put;

import com.google.asli.R;
import com.google.asli.Class.MySingleton;
import com.google.asli.Class.OnloadPrice;
import com.google.asli.Model.ModelBasket;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterBasket extends RecyclerView.Adapter<AdapterBasket.viewHodelr>  {

    Context context;
    List<ModelBasket> modelBaskets;
    private OnloadPrice onloadPrice;

    public AdapterBasket(Context context, List<ModelBasket> modelBaskets) {
        this.context = context;
        this.modelBaskets = modelBaskets;
    }

    public void setOnloadPrice(OnloadPrice onloadPrice) {
        this.onloadPrice = onloadPrice;
    }

    @NonNull
    @Override
    public viewHodelr onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutbasket,parent,false);
        return new viewHodelr(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHodelr viewHodelr, final int i) {


        final ModelBasket basket = modelBaskets.get(i);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(Integer.valueOf(basket.getPrice()));
        String allprice = decimalFormat.format(Integer.valueOf(basket.getAllprice()));

        viewHodelr.textallprice.setText(allprice+" "+"تومان");
        viewHodelr.textprice.setText(price+" "+"تومان");
        viewHodelr.textgaranty.setText(basket.getGaranty());
        viewHodelr.textnumber.setText(basket.getNumber()+" "+"عدد");
        viewHodelr.texttitle.setText(basket.getTitle());
        viewHodelr.textcolor.setText(basket.getColor());
        Picasso
                .with(context)
                .load(basket.getImage())
                .skipMemoryCache()
                .into(viewHodelr.imageBasket);
        viewHodelr.textdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onloadPrice !=null)
                {
                    onloadPrice.onloadprice();
                    deleteBasket(basket.getId()+"");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            modelBaskets.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeRemoved(i,modelBaskets.size());
                        }
                    },200);
                }




            }
        });

    }

    @Override
    public int getItemCount() {
        return modelBaskets.size();
    }

    public class viewHodelr extends RecyclerView.ViewHolder {

        ImageView imageBasket;
        TextView texttitle,textnumber,textcolor,textgaranty,textprice,textallprice,textdelete;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Vazir-Medium-FD-WOL.ttf");

        public viewHodelr(@NonNull View itemView) {
            super(itemView);

            texttitle = itemView.findViewById(R.id.textTitleBasket);
            texttitle.setTypeface(typeface);
            textcolor = itemView.findViewById(R.id.textColorBasket);
            textcolor.setTypeface(typeface);
            textnumber = itemView.findViewById(R.id.textNumberBasket);
            textnumber.setTypeface(typeface);
            textgaranty = itemView.findViewById(R.id.textGarantyBasket);
            textgaranty.setTypeface(typeface);
            textprice = itemView.findViewById(R.id.priceBasket);
            textprice.setTypeface(typeface);
            textallprice = itemView.findViewById(R.id.textAllpriceBasket);
            textallprice.setTypeface(typeface);
            textdelete = itemView.findViewById(R.id.textDeleteBasket);
            textdelete.setTypeface(typeface);
            imageBasket = itemView.findViewById(R.id.imageBasket);

        }
    }


    private void deleteBasket(final String id)
    {

        String url =Link.linkDelteBasket;
        //final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   progressDialog.dismiss();
             //   Toast.makeText(context, "محصول از سبد خرید حذف شد", Toast.LENGTH_SHORT).show();

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST,url,listener,errorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(put.id,id);
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);


    }


}
