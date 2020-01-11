package com.example.minnano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CommentSection extends AppCompatActivity
{
    CollectionReference prodes;
    DocumentReference docref;
    RecyclerView commentList;
    FirebaseFirestore fs;
    String pid;
    Comment_Adapter adapter;
    private String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_section);


        Intent intent=getIntent();
        pid=intent.getStringExtra("DOCID");
        Log.e(TAG, "onDocid: "+pid);
        commentList=findViewById(R.id.commentList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        commentList.setLayoutManager(layoutManager);

        fs= FirebaseFirestore.getInstance();
        prodes=fs.collection("Products/"+pid+"/Comments");
        Query query=prodes.orderBy("Rating");
        FirestoreRecyclerOptions<CommentTile> options=new FirestoreRecyclerOptions.Builder<CommentTile>()
                .setQuery(query,CommentTile.class)
                .build();
        adapter=new Comment_Adapter(options,getApplicationContext());
        commentList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
