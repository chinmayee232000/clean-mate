package com.example.clean_mate2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class update_prices extends AppCompatActivity {
    EditText p1,p2,p3,p4,p5;
    String price1,price2,price3,price4,price5;
    TextView t1,t2,t3,t4,t5;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_prices);
        p1=findViewById(R.id.p1);
        p2=findViewById(R.id.p2);
        p3=findViewById(R.id.p3);
        p4=findViewById(R.id.p4);
        p5=findViewById(R.id.p5);
        t1=findViewById(R.id.daily_wear);
        t2=findViewById(R.id.fancy_wear);
        t3=findViewById(R.id.bed_sheet);
        t4=findViewById(R.id.pillow_cover);
        t5=findViewById(R.id.towel);
        update=findViewById(R.id.update);
        FirebaseFirestore.getInstance().collection("LSP").document("Details").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
             price1=documentSnapshot.get("daily_wear").toString();
             price2=documentSnapshot.get("fancy_wear").toString();
             price3=documentSnapshot.get("bed_sheet").toString();
             price4=documentSnapshot.get("pillow_cover").toString();
             price5=documentSnapshot.get("towel").toString();
             t1.setText("Daily wear(Currently : Rs "+price1+" )");
             t2.setText("Fancy wear(Currently : Rs "+price2+" )");
             t3.setText("Bed Sheet(Currently : Rs "+price3+" )");
             t4.setText("Pillow Cover(Currently : Rs "+price4+" )");
             t5.setText("Towel(Currently : Rs "+price5+" )");

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String up1,up2,up3,up4,up5;
                up1=p1.getText().toString();
                up2=p2.getText().toString();
                up3=p3.getText().toString();
                up4=p4.getText().toString();
                up5=p5.getText().toString();
                FirebaseFirestore.getInstance().collection("LSP").document("Details").update("daily_wear",up1);
                FirebaseFirestore.getInstance().collection("LSP").document("Details").update("fancy_wear",up2);
                FirebaseFirestore.getInstance().collection("LSP").document("Details").update("bed_sheet",up3);
                FirebaseFirestore.getInstance().collection("LSP").document("Details").update("pillow_cover",up4);
                FirebaseFirestore.getInstance().collection("LSP").document("Details").update("towel",up5);
                Toast.makeText(update_prices.this,"Prices Updated for all Users",Toast.LENGTH_SHORT).show();
            }
        });

    }
}