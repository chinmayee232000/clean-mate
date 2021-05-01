package com.example.clean_mate2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class register extends AppCompatActivity {
    EditText email,password,fullname,roomno,hostel,phonenumber;
    CheckBox mode;
    String TAG="register";
    String Uid,date;
    Integer payment;
    ProgressBar progressBar2;
    FirebaseAuth fAuth;
    FirebaseFirestore ref;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.emailid);
        password=findViewById(R.id.password);
        fullname=findViewById(R.id.fullname);
        roomno=findViewById(R.id.roomno);
        hostel=findViewById(R.id.hostel);
        phonenumber=findViewById(R.id.textView5);
        //ref=FirebaseFirestore.getInstance();
        progressBar2=findViewById(R.id.progressBar2);
        mode=findViewById(R.id.mode);
        fAuth= FirebaseAuth.getInstance();
        //Uid=fAuth.getCurrentUser().getUid();
        date=null;
        payment=0;

        //Intent i1=new Intent(this,Dashboard1.class);

        reg=findViewById(R.id.Loginbutton);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = email.getText().toString().trim();
                String mpassword = password.getText().toString().trim();
                String mfullname=fullname.getText().toString().trim();
                String mhostel=hostel.getText().toString().trim();
                String mroomno=roomno.getText().toString().trim();
                String mphonenumber=phonenumber.getText().toString();
                if (TextUtils.isEmpty(memail)) {
                    email.setError("Email ID is required");
                    return;
                }
                if (TextUtils.isEmpty(mpassword)) {
                    password.setError("password is required");
                    return;
                }
                if (mpassword.length() < 6) {
                    password.setError("Password must contain at least 6 characters");
                    return;
                }
                progressBar2.setVisibility(View.VISIBLE);
                //register user w firebase
                fAuth.createUserWithEmailAndPassword(memail, mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //String fuser=fAuth.getCurrentUser().getUid();
                            HashMap<String, Object> map = new HashMap<>();
                            String a = "FullName";
                            map.put("phonenumber", mphonenumber);
                            map.put(a, mfullname);
                            map.put("Hostel", mhostel);
                            map.put("RoomNo", mroomno);
                            map.put("EmailID", memail);
                            map.put("Password", mpassword);
                            map.put("Date", date);
                            map.put("Payment", payment);

                            if (mode.isChecked()) {
                                map.put("isUser", false);
                                map.put("daily_wear",5);
                                map.put("fancy_wear",6);
                                map.put("bed_sheet",7);
                                map.put("pillow_cover",2);
                                map.put("towel",7);
                                //enter in db
                                FirebaseFirestore.getInstance().collection("LSP").document("Details").set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(register.this, "Added to database", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Intent admindashboard=new Intent(register.this,adminDashboard.class);
                                //admindashboard.putExtra("admin_Uid",mphonenumber);
                                //Log.d(TAG, "onComplete: sent value" + mphonenumber);
                                startActivity(admindashboard);

                            }
                            else {
                                map.put("isUser", true);
                                FirebaseFirestore.getInstance().collection(mphonenumber).document("Details").set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(register.this, "Added to database", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                HashMap<String, String> tempmap = new HashMap<>();
                                tempmap.put("phonenumber", mphonenumber);
                                FirebaseFirestore.getInstance().collection("Users").add(tempmap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "onSuccess: Users collection successfully made ");
                                    }
                                });
                                Toast.makeText(register.this, "User Created", Toast.LENGTH_SHORT).show();
                                progressBar2.setVisibility(View.INVISIBLE);
                                Intent toDashboard = new Intent(register.this, Dashboard1.class);
                                toDashboard.putExtra("Uid", mphonenumber);
                                Log.d(TAG, "onComplete: sent value" + mphonenumber);
                                startActivity(toDashboard);

                            }

                        }
                         else {
                            Toast.makeText(register.this, "error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
