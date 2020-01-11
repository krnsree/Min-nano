package com.example.minnano;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class Display_Adapter extends FirestoreRecyclerAdapter<Item_Cell,Display_Adapter.ViewHolder>
{

    private static final String TAG = "TAG";
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
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull final Item_Cell item_cell)
    {

        viewHolder.title.setText(item_cell.getProductTitle());
        viewHolder.title.setTextSize(18);
        //viewHolder.title.setShadowLayer(2,5,5, Color.GRAY);
        viewHolder.price.setText("Rs "+(item_cell.getProductPrice()));
        viewHolder.price.setTextSize(17);
        //viewHolder.price.setShadowLayer(2,5,5, Color.GRAY);
        if(item_cell.getProductImage()!=null)
        {
            Picasso.with(context)
                    .load(Uri.parse(item_cell.getProductImage()))
                    .into(viewHolder.image);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, Product_Page.class);
                intent.putExtra("TITLE",item_cell.getProductTitle());
                intent.putExtra("PRICE",item_cell.getProductPrice());
                intent.putExtra("IMAGE",item_cell.getProductImage());
                intent.putExtra("COLOR",item_cell.getColor());
                intent.putExtra("TYPE",item_cell.getType());
                intent.putExtra("MODEL",item_cell.getModel());
                intent.putExtra("CONNECT",item_cell.getConnectivity());
                intent.putExtra("PID",item_cell.getPid());
                Log.e(TAG, "onClick:"+item_cell.getPid());

                context.startActivity(intent);
            }
        });
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
