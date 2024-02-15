package com.example.productapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productapp.Model.Product;
import com.example.productapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    List<Product> productList;
    Context context;

    public ProductListAdapter(List<Product> productList, Context context, OnItemClickListener listener) {
        this.productList = productList;
        this.context = context;
        this.listener = listener;
    }
//aov

    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_productlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {

        Product product = productList.get(position);
        holder.Name.setText(product.getTitle());
        holder.discription.setText(product.getDescription());
        holder.price.setText("₹"+product.getPrice());

        String imageUrl = product.getThumbnail();
        Picasso.get().load(imageUrl).into(holder.Produtimage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.Onselected(product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name,discription,price;
        ImageView Produtimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tvProductname);
            discription = itemView.findViewById(R.id.tvProductdiscription);
            price = itemView.findViewById(R.id.tvProductPrice);
            Produtimage = itemView.findViewById(R.id.tvImage);

        }
    }
}
