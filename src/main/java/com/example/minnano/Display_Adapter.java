package com.example.minnano;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class Display_Adapter extends FirestoreRecyclerAdapter<Item_Cell,Display_Adapter.ViewHolder>
{

    Context context;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Display_Adapter(@NonNull FirestoreRecyclerOptions<Item_Cell> options, Context context)
    {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Item_Cell item_cell)
    {
        viewHolder.title.setText(item_cell.getProductTitle());
        viewHolder.price.setText(item_cell.getProductPrice());
        Picasso.with(context)
                .load(item_cell.getProductImage())
                .resize(300,300).into(viewHolder.image);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item__cell,parent,false);
        return new ViewHolder(v);
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
