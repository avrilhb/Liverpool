package com.example.liverpooltest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.liverpooltest.R;
import com.example.liverpooltest.entity.Record;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private ArrayList<Record> recordArrayList;
    private Context context;

    public SearchAdapter(ArrayList<Record> recordArrayList, Context context) {
        this.recordArrayList = recordArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Record record = recordArrayList.get(position);

        holder.productNameTV.setText(record.getProductDisplayName());
        holder.noDiscountPriceTV.setText("$" + String.valueOf(record.getListPrice()));
        holder.discountPriceTV.setText("$" + String.valueOf(record.getPromoPrice()));
        Glide.with(context)
                .load(record.smImage)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.liverpool_icon) // Placeholder image
                )
                .into(holder.productIV);
    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating a variable for our text view and image view.
        private TextView productNameTV, noDiscountPriceTV, discountPriceTV;
        private ImageView productIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our variables.
            productNameTV = itemView.findViewById(R.id.product_name_tv);
            noDiscountPriceTV = itemView.findViewById(R.id.no_discount_price_tv);
            discountPriceTV = itemView.findViewById(R.id.discount_price_tv);
            productIV = itemView.findViewById(R.id.product_iv);
        }
    }
}
