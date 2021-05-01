package com.example.clean_mate2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class payment_status extends AppCompatActivity {
    TextView date,payment,username;
    Switch paid,delivered;
    ImageView tick;
    String Uid,name,payAmount,ddate;
     DocumentReference dbref;
    FirebaseFirestore col;
    private String TAG="payment_status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);
        Uid=getIntent().getStringExtra("Uid");
        payment=findViewById(R.id.payment_user);
        date=findViewById(R.id.order_date);
        paid=findViewById(R.id.paid);
        delivered=findViewById(R.id.switch1);
        tick=findViewById(R.id.tick);
        username=findViewById(R.id.username);
        dbref= FirebaseFirestore.getInstance().collection(Uid).document("Details");

        dbref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot!=null) {
                    Log.d(TAG, "onSuccess: start loop");
                    name = documentSnapshot.getString("FullName");
                    payAmount = documentSnapshot.get("Payment").toString();
                    ddate = documentSnapshot.get("Date").toString();
                    username.setText("Customer Name : "+name);
                    payment.setText("Payment due : "+payAmount+" Rupees");
                    date.setText("Order Date : "+ddate);
                    Log.d(TAG, "onSuccess: Retrieved values from database " + name + payAmount + ddate);
                }
                else
                {
                    Toast.makeText(payment_status.this,"No Order Placed",Toast.LENGTH_SHORT).show();
                    return;
                }

            }


        });

        paid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dbref= FirebaseFirestore.getInstance().collection(Uid).document("Details");
                dbref.update("Payment",0);
                Toast.makeText(payment_status.this,"Payment Updated",Toast.LENGTH_SHORT).show();
            }
        });

        delivered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FirebaseFirestore.getInstance().collection(Uid).document("Details").collection("Images").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot snapshot:task.getResult())
                        {
                            FirebaseFirestore.getInstance().collection(Uid).document("Details").collection("Images").document(snapshot.getId()).delete();
                        }
                    }
                });
                Toast.makeText(payment_status.this,"Order Received",Toast.LENGTH_SHORT).show();
                tick.setVisibility(View.VISIBLE);
            }
        });


    }
}