package com.example.minnano;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Display_Page extends AppCompatActivity {

    CollectionReference prodref;
    FirebaseFirestore fs;
    Display_Adapter adapter;
    RecyclerView item_List;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__page);

        item_List = findViewById(R.id.itemList);
        item_List.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        item_List.setLayoutManager(layoutManager);

        fs=FirebaseFirestore.getInstance();
        prodref=fs.collection("Products");
        Query query=prodref.orderBy("productTitle");
        FirestoreRecyclerOptions<Item_Cell> options=new FirestoreRecyclerOptions.Builder<Item_Cell>()
                .setQuery(query,Item_Cell.class)
                .build();
        adapter=new Display_Adapter(options,Display_Page.this);

        item_List.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
