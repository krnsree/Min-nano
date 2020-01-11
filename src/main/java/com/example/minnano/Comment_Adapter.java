package com.example.minnano;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;



public class Comment_Adapter extends FirestoreRecyclerAdapter<CommentTile,Comment_Adapter.ViewHolder>
{
    Context context;
    public Comment_Adapter(@NonNull FirestoreRecyclerOptions<CommentTile> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull CommentTile commentTile)
    {
        viewHolder.ratingTitle.setText("Rating:");
        viewHolder.rating.setText(commentTile.getRating());
        viewHolder.comment.setText(commentTile.getComment());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_tile, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView ratingTitle;
        TextView rating;
        TextView comment;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ratingTitle=itemView.findViewById(R.id.Ratingheading);
            rating=itemView.findViewById(R.id.ratingCom);
            comment=itemView.findViewById(R.id.comment);
        }
    }
}
