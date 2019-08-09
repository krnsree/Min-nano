package com.example.minnano;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Display_Adapter extends RecyclerView.Adapter<Display_Adapter.ViewHolder>
{
    Context context;
    ArrayList<Item_Cell> productList=new ArrayList<>();

    public Display_Adapter(Context context,ArrayList<Item_Cell> productList)
    {
        this.context=context;
        this.productList=productList;
    }

    @NonNull
    @Override
    public Display_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item__cell,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Display_Adapter.ViewHolder holder, int position)
    {
        Log.e(TAG, "onBindViewHolder1: "+productList.get(position).getProductImage());
        Log.e(TAG,"onBindViewHolder2: "+productList.get(position).getProductTitle());
        Log.e(TAG,"onBindViewHolder3: "+productList.get(position).getProductPrice());
        holder.title.setText(productList.get(position).getProductTitle());
        holder.price.setText(productList.get(position).getProductPrice());
        //holder.image.setImageURI(productList.get(position).getProductImage());
        holder.image.setScaleType(ImageView.ScaleType.MATRIX);
        holder.image.setAdjustViewBounds(true);
        Picasso.with(context)
                .load(productList.get(position).getProductImage())
                .resize(300,300).into(holder.image);
        holder.image.setAdjustViewBounds(true);
        holder.image.setScaleType(ImageView.ScaleType.CENTER);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView price;
        ImageView image;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title=itemView.findViewById(R.id.productTitle);
            price=itemView.findViewById(R.id.productPrice);
            image=itemView.findViewById(R.id.productImage);
        }
    }
}
