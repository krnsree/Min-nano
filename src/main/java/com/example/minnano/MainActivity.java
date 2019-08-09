package com.example.minnano;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    EditText username,password,ePass,eUser,eDob,eName;
    Button sin,sup,signup,cal;
    DatabaseReference addreff,retreff;
    DatabaseAdapter adapter;
    long maxid;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        sin=(Button)findViewById(R.id.button);
        sup=(Button)findViewById(R.id.button2);
        adapter=new DatabaseAdapter();

        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder  subuilder=new AlertDialog.Builder(MainActivity.this);
                View suview=getLayoutInflater().inflate(R.layout.activity_sign_up_page,null);
                subuilder.setView(suview);
                final AlertDialog dialog=subuilder.create();

                eName=(EditText) suview.findViewById(R.id.editText3);
                eDob=(EditText) suview.findViewById(R.id.editText4);
                eUser=(EditText) suview.findViewById(R.id.editText5);
                ePass=(EditText) suview.findViewById(R.id.editText6);
                signup=(Button) suview.findViewById(R.id.button3);
                cal=(Button) suview.findViewById(R.id.button4);
                eDob.setEnabled(false);

                final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        eDob.setText(dayOfMonth+"/"+(++month)+"/"+year);
                    }
                };

                //datePickerDialog=new DatePickerDialog(MainActivity.this,onDateSetListener,2010,0,06);

                cal.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        datePickerDialog=new DatePickerDialog(MainActivity.this,onDateSetListener,2000,0,00);
                        datePickerDialog.show();
                    }
                });


                signup.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(!eName.toString().isEmpty() || !eDob.toString().isEmpty() ||
                                !eUser.toString().isEmpty() || !ePass.toString().isEmpty())
                        {
                            addreff= FirebaseDatabase.getInstance().getReference().child("Member");

                            addreff.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    if(dataSnapshot.exists())
                                        maxid=(dataSnapshot.getChildrenCount());
                                    username.setText(maxid+"");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            adapter.setDOB(eDob.getText().toString());
                            adapter.setName(eName.getText().toString());
                            adapter.setUsername(eUser.getText().toString());
                            adapter.setPassword(ePass.getText().toString());
                            addreff.child((maxid+1)+"").setValue(adapter);
                            Toast.makeText(MainActivity.this,"SignUp Successful",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                        else
                            Toast.makeText(MainActivity.this,"Please enter all the details",Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();
            }
        });

        sin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                authentication();
            }
        });

    }

    void authentication()
    {
        final boolean[] f = {false};
        final String[] user = new String[1];
        final String[] pass = new String[1];
        if(maxid>0)
        {
            for(int i=1;i<=maxid;i++)
            {

                retreff=FirebaseDatabase.getInstance().getReference().child("Member").child("member"+i);
                if(retreff!=null)
                {
                    retreff.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if(dataSnapshot.child("username")!=null || dataSnapshot.child("password")!=null)
                            {
                                user[0] = dataSnapshot.child("username").getValue().toString();
                                pass[0] = dataSnapshot.child("password").getValue().toString();

                                if (username.getText().toString().equals(user[0]) && password.getText().toString().equals(pass[0])) {
                                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                    f[0] = true;
                                } else if (!username.getText().toString().equals(user[0]) && password.getText().toString().equals(pass[0])) {
                                    Toast.makeText(MainActivity.this, "Wrong username", Toast.LENGTH_LONG).show();
                                    f[0] = true;
                                } else if (username.getText().toString().equals(user[0]) && !password.getText().toString().equals(pass[0])) {
                                    Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                                    f[0] = true;
                                }
                            }

                            else
                                Toast.makeText(MainActivity.this, "No such account exists. Please sign up", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }

                    });
                    if(f[0]!=true && maxid==i)
                        Toast.makeText(MainActivity.this, "No such account exists. Please sign up", Toast.LENGTH_LONG).show();
                    else
                        break;
                }

                else
                {
                    Toast.makeText(MainActivity.this, "No such account exists,please sign up", Toast.LENGTH_LONG).show();
                }

            }
        }
        else
            Toast.makeText(MainActivity.this, "No such account exists,please sign up", Toast.LENGTH_LONG).show();
    }
}

