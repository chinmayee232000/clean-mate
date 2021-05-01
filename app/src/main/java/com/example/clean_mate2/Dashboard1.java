package com.example.clean_mate2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Array;

public class Dashboard1 extends AppCompatActivity {
  private static final String TAG="Dashboard1";
  int[] quant=new int[5];
  int totalPayment=0,totalItems=0;
    TextView addImage1,addImage2,addImage3,addImage4,addImage5;
    CheckBox c1,c2,c3,c4,c5;
   String Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard1);
        Button select=(Button) findViewById(R.id.button3);
        Button proceed=(Button)findViewById(R.id.button);
        c1=findViewById(R.id.checkBox);
        c2=findViewById(R.id.checkBox2);
        c3=findViewById(R.id.checkBox3);
        c4=findViewById(R.id.checkBox5);
        c5=findViewById(R.id.checkBox6);
        Spinner spinner= (Spinner)findViewById(R.id.spinner2);
        Spinner spinner1=(Spinner)findViewById(R.id.spinner3);
        Spinner spinner2=(Spinner)findViewById(R.id.spinner4);
        Spinner spinner3=(Spinner)findViewById(R.id.spinner);
        Spinner spinner4=(Spinner)findViewById(R.id.spinner5);
        addImage1=(TextView)findViewById(R.id.textView7);
        addImage2=(TextView)findViewById(R.id.textView9);
        addImage3=(TextView)findViewById(R.id.textView11);
        addImage4=(TextView)findViewById(R.id.textView14);
        addImage5=(TextView)findViewById(R.id.textView16);
        Uid=getIntent().getStringExtra("Uid");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.selection_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // An item was selected. You can retrieve the selected item using
                    quant[0] = Integer.parseInt(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });


            spinner1.setAdapter(adapter);
            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    quant[1] = Integer.parseInt(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(Dashboard1.this, "NOTHING SELECTED", Toast.LENGTH_SHORT).show();
                }
            });


            spinner2.setAdapter(adapter);
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    quant[2] = Integer.parseInt(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(Dashboard1.this, "NOTHING SELECTED", Toast.LENGTH_SHORT).show();
                }
            });


            spinner3.setAdapter(adapter);
            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    quant[3] = Integer.parseInt(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(Dashboard1.this, "NOTHING SELECTED", Toast.LENGTH_SHORT).show();
                }
            });

            spinner4.setAdapter(adapter);
            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    quant[4] = Integer.parseInt(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(Dashboard1.this, "NOTHING SELECTED", Toast.LENGTH_SHORT).show();
                }
            });

        //normal=5
        //fancy=6
        //bed sheet=7
        //pillowcover=2
        //towel=7

            addImage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(c1.isChecked()) {
                        Intent toAddImages = new Intent(Dashboard1.this, AddImages.class);
                        toAddImages.putExtra("Uid", Uid);
                        startActivity(toAddImages);
                    }
                }
            });


            addImage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(c2.isChecked()) {
                        Intent toAddImages = new Intent(Dashboard1.this, AddImages.class);
                        toAddImages.putExtra("Uid", Uid);
                        startActivity(toAddImages);
                    }
                }
            });


            addImage3.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    if(c3.isChecked()) {
                        Intent toAddImages = new Intent(Dashboard1.this, AddImages.class);
                        toAddImages.putExtra("Uid", Uid);
                        startActivity(toAddImages);
                    }
                }
            });


            addImage4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(c4.isChecked()) {
                        Intent toAddImages = new Intent(Dashboard1.this, AddImages.class);
                        toAddImages.putExtra("Uid", Uid);
                        startActivity(toAddImages);
                    }
                }
            });


            addImage5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(c5.isChecked()) {
                        Intent toAddImages = new Intent(Dashboard1.this, AddImages.class);
                        toAddImages.putExtra("Uid", Uid);
                        startActivity(toAddImages);
                    }
                }
            });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView quantityTextView = (TextView) findViewById(R.id.textView12);
                FirebaseFirestore.getInstance().collection("LSP").document("Details").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       int p1,p2,p3,p4,p5;
                       p1=Integer.parseInt(documentSnapshot.getString("daily_wear"));
                       p2=Integer.parseInt(documentSnapshot.getString("fancy_wear"));
                       p3=Integer.parseInt(documentSnapshot.getString("bed_sheet"));
                       p4=Integer.parseInt(documentSnapshot.getString("pillow_cover"));
                       p5=Integer.parseInt(documentSnapshot.getString("towel"));
                       totalPayment= p1*quant[0]+p2*quant[1]+p3*quant[2]+p4*quant[3]+p5*quant[4];
                    }
                });

                totalItems=quant[0]+quant[1]+quant[2]+quant[3]+quant[4];
                quantityTextView.setVisibility(View.VISIBLE);
                proceed.setVisibility(View.VISIBLE);
                quantityTextView.setText("Total items being sent = " + totalItems);
                Log.d(TAG, "onClick: select working");


            }
        });

         proceed.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent toPayment=new Intent(Dashboard1.this,PaymentWindow.class);
                 toPayment.putExtra("Uid",Uid);
                 toPayment.putExtra("dpayment",totalPayment);
                 startActivity(toPayment);
             }
         });



    }




}