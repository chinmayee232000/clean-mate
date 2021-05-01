package com.example.clean_mate2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
EditText email,password,phoneno;
Button login;
CheckBox login_mode;
FirebaseAuth fAuth=FirebaseAuth.getInstance();
TextView signup;
String Uid; //Uid is phone number
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.textView2);
        password=findViewById(R.id.textView3);
        signup=findViewById(R.id.textView4);
        login = findViewById(R.id.Loginbutton);
        login_mode=findViewById(R.id.login_mode);
        phoneno=findViewById(R.id.textView18);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent temp=new Intent(MainActivity.this,register.class);
                //startActivity(temp);

                String m1email = email.getText().toString().trim();
                String m1password = password.getText().toString().trim();
                Uid=phoneno.getText().toString().trim();
                if (TextUtils.isEmpty(m1email)) {
                    email.setError("Enter valid Email ID");
                    return;
                }
                if (TextUtils.isEmpty(m1password)) {
                    password.setError("Enter valid Email ID");
                    return;
                }
                if(TextUtils.isEmpty(Uid)|| Uid.length()!=10)
                {
                    phoneno.setError("Valid Phone number required");
                    return;
                }
                //start here

                fAuth.signInWithEmailAndPassword(m1email,m1password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                            if(login_mode.isChecked())
                            {
                                String admin_Uid=phoneno.getText().toString().trim();
                                Intent toadminDashboard=new Intent(MainActivity.this,adminDashboard.class);
                                toadminDashboard.putExtra("admin_Uid",admin_Uid);
                                startActivity(toadminDashboard);
                            }
                            else {

                                Intent todashboard1 = new Intent(MainActivity.this, Dashboard1.class);
                                todashboard1.putExtra("Uid", Uid);
                                startActivity(todashboard1);
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }

            //here

        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister =new Intent(MainActivity.this,register.class);
                startActivity(toRegister);
            }
        });
    }
}

