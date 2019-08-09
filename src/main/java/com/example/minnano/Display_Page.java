package com.example.minnano;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Display_Page extends AppCompatActivity
{
    StorageReference proimg;
    DocumentReference prodes;
    ArrayList<Item_Cell> product_List;
    RecyclerView item_List;
    FirebaseAuth mAuth;
    long a;
    private String TAG = "TAG";
    public static final String Title="productTitle";
    public static final String Price="productPrice";
    public static final String Image="productImage";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__page);

        item_List=findViewById(R.id.itemList);
        item_List.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),1);
        item_List.setLayoutManager(layoutManager);

        product_List=prepareData();

        Display_Adapter adapter=new Display_Adapter(getApplicationContext(),product_List);
        item_List.setAdapter(adapter);

    }

   private ArrayList<Item_Cell> prepareData() /*throws IOException*/
   {

       ArrayList<Item_Cell> arrayList = new ArrayList<>();
       proimg = FirebaseStorage.getInstance().getReference("Product_Images");
       for (int i = 1; i <= 2; i++)
       {
           Item_Cell ic=new Item_Cell();

           ic=getData(i);

           /*proimg.child(i + ".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
                   ic.setProductImage(uri);
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(Display_Page.this, "Download Failed", Toast.LENGTH_SHORT).show();
               }
           });*/
           Log.e(TAG,"prepareData()1"+ic.getProductTitle());
           Log.e(TAG,"prepareData()2"+ic.getProductPrice());
           arrayList.add(ic);
       }
       return arrayList;
   }

    private Item_Cell getData(int i)
    {
        final Item_Cell ic=new Item_Cell();

            prodes= FirebaseFirestore.getInstance().document("Products/"+i);

            prodes.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot)
                {
                    if(documentSnapshot!=null)
                    {
                        ic.setProductTitle(documentSnapshot.getString(Title));
                        ic.setProductPrice(documentSnapshot.getString(Price));
                        Toast.makeText(Display_Page.this,"hello",Toast.LENGTH_LONG).show();
                        Log.e(TAG,"prepareData()3"+ic.getProductTitle());
                        Log.e(TAG,"prepareData()4"+ic.getProductPrice());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(Display_Page.this,"Failed",Toast.LENGTH_LONG).show();
                }
            });
        Log.e(TAG, "getData: "+ic.getProductPrice());
        Log.e(TAG, "getData: "+ic.getProductTitle());
        return ic;
    }

}

