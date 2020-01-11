package com.example.minnano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import javax.annotation.Nullable;

public class Product_Page extends AppCompatActivity {

    String title, price, image, color, model, type, connect, pid;
    TextView product_title, product_price, rating, specT, spec, comtitle, commenttitle, ratingCom, displayComment,ratingSel;
    EditText comment;
    ImageView product_image;
    RatingBar ratingBar;
    String specifications;
    FirebaseFirestore fs;
    DocumentReference documentReference;
    Button submitComment,showComments;


    @Override
    protected void onResume() throws NullPointerException {
        super.onResume();
        setComments();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        //getting the values
        final Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        price = intent.getStringExtra("PRICE");
        image = intent.getStringExtra("IMAGE");
        color = intent.getStringExtra("COLOR");
        type = intent.getStringExtra("TYPE");
        model = intent.getStringExtra("TYPE");
        connect = intent.getStringExtra("CONNECT");
        pid = intent.getStringExtra("PID");

        //getting the views
        ratingBar = findViewById(R.id.bar);
        product_title = findViewById(R.id.product_title);
        product_price = findViewById(R.id.product_price);
        product_image = findViewById(R.id.product_image);
        comtitle = findViewById(R.id.commentTitle);
        spec = findViewById(R.id.spec);
        specT = findViewById(R.id.specTitle);
        comment = findViewById(R.id.comments);
        submitComment = findViewById(R.id.submitcom);
        commenttitle = findViewById(R.id.commentsTitle);
        ratingCom = findViewById(R.id.Rating);
        displayComment = findViewById(R.id.CommentSec);
        ratingSel=findViewById(R.id.ratingSel);
        showComments=findViewById(R.id.showComments);

        //setting firebase instance
        fs = FirebaseFirestore.getInstance();
        documentReference = fs.collection("Products").document(pid);

        //Setting Comment Title
        commenttitle.setText("Top Rated Comments:");
        commenttitle.setTextSize(17);

        //Setting Rating & Title
        rating = findViewById(R.id.rating);
        rating.setText("Rating & Reviews:");
        rating.setTextSize(20);

        //Specification Title
        specT.setText("Specifications:");
        specT.setTextSize(20);

        //Setting price
        product_price.setText("Rs:" + price);
        product_price.setTextSize(18);

        //Setting the Product Title
        product_title.setText(title);
        product_title.setTextSize(19);

        //Loading the image
        Picasso.with(this)
                .load(Uri.parse(image))
                .into(product_image);

        //Setting Specifications
        specifications = "*Model :\t\t" + model + "\n*Color :\t\t" + color + "\n*Connectivity :\t\t" + connect + "\n*Type :\t\t" + type;
        spec.setText(specifications);
        spec.setTextSize(16);

        //setting the comment font & size
        comment.setTextSize(16);

        //Setting thecomment display
        displayComment.setTextSize(16);

        //seting the display rating
        ratingCom.setTextSize(20);

        //setting review Title
        comtitle.setTextSize(16);
        comtitle.setText("Add your Review:");
        ratingSel.setText("Rating:");
        ratingSel.setTextSize(20);

        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Comment = comment.getText().toString();
                float Rating = ratingBar.getRating();
                HashMap<String, String> CommentSection = new HashMap<>();
                CommentSection.put("Comment", Comment);
                CommentSection.put("Rating", Rating + "");
                final HashMap<String, String> commentSection = new HashMap<>();
                documentReference.collection("Comments").add(CommentSection)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                ratingBar.setRating(0);
                                comment.setText("");
                                setComments();

                            }
                        });
            }

        });

        showComments.setText("Show Comments");
        showComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),CommentSection.class);
                intent1.putExtra("DOCID",pid);
                Log.e("TAG", "onClick: "+pid);
                startActivity(intent1);
            }
        });

    }

    void setComments() {
        Query query = documentReference.collection("Comments").orderBy("Rating", Query.Direction.DESCENDING).limit(1);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        displayComment.setText(document.getString("Comment"));
                        ratingCom.setText(document.getString("Rating"));
                    }
                }
            }
        });
    }
}