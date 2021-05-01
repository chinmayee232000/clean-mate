package com.example.clean_mate2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentWindow extends AppCompatActivity {

    TextView payment,ddate,name,ppay,dpay;
    //TextView name=(TextView)findViewById(R.id.hi);
    CalendarView cv;
    String dname,Uid;
    Integer ppaymet,dpayment;
    String TAG="PaymentWindow";
    View line;
    private CollectionReference ref;
    private Button daily,monthly,showsummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_window);

        Uid=getIntent().getStringExtra("Uid");
        dpayment=getIntent().getIntExtra("dpayment",0);
        name=findViewById(R.id.hi);
        payment=findViewById(R.id.payment);
        ddate=findViewById(R.id.date);
        daily=findViewById(R.id.button6);
        monthly=findViewById(R.id.button7);
        cv=findViewById(R.id.calendarView);
        ppay=findViewById(R.id.ppay);
        dpay=findViewById(R.id.dpay);
        line=findViewById(R.id.line1);
        showsummary=findViewById(R.id.showsummary);
        ref= FirebaseFirestore.getInstance().collection(Uid);
        FirebaseFirestore.getInstance().collection(Uid).document("Details").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot doc=task.getResult();
                    if(doc!=null) {
                        dname = doc.getString("FullName");
                        ppaymet = doc.getLong("Payment").intValue();
                        Log.d(TAG, "onComplete:name is"+dname+" payment is "+ppaymet);
                    }
                    else
                    {
                        Log.d(TAG, "onComplete: No Record");
                    }
                }
                else
                {
                    Log.d(TAG, "onComplete: Not successful");
                }
            }
        });
        name.setText("Hi "+ dname+" !");

        showsummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cv.setVisibility(View.INVISIBLE);
                showsummary.setVisibility(View.INVISIBLE);
                line.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                payment.setVisibility(View.VISIBLE);
                ddate.setVisibility(View.VISIBLE);
                daily.setVisibility(View.VISIBLE);
                monthly.setVisibility(View.VISIBLE);
                ppay.setVisibility(View.VISIBLE);
                dpay.setVisibility(View.VISIBLE);
                Log.d(TAG, "onClick: payment "+ dpayment);


                String d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                ddate.setText("DATE OF ORDER : "+d);
                Integer temp=dpayment+ppaymet;
                ppay.setText("Previous payment : Rs "+ppaymet);
                dpay.setText("Payment for Current order : Rs "+dpayment);
                payment.setText("Your Payment for laundry = Rs "+temp);

                name.setText("Hi "+ dname+" !");

                daily.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.document("Details").update("Payment",dpayment+ppaymet);
                        ref.document("Details").update("Date",d);
                        Toast.makeText(PaymentWindow.this,"Payment stored",Toast.LENGTH_SHORT).show();
                        Intent todonePage=new Intent(PaymentWindow.this,donePage.class);
                        startActivity(todonePage);
                    }
                });
                monthly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ref.document("Details").update("Date",d);
                        int temp =ppaymet+dpayment;
                        ref.document("Details").update("Payment",dpayment).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(PaymentWindow.this,"Payment stored",Toast.LENGTH_SHORT).show();
                                Intent todonePage=new Intent(PaymentWindow.this,donePage.class);
                                startActivity(todonePage);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PaymentWindow.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        });




    }
}