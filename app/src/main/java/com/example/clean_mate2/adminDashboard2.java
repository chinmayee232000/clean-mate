package com.example.clean_mate2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminDashboard2 extends AppCompatActivity {
    Button order_details,payment_status,update_prices;
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard2);
        order_details=findViewById(R.id.view_image);
        payment_status=findViewById(R.id.payment_status);
        update_prices=findViewById(R.id.update_prices);
        Uid=getIntent().getStringExtra("Uid");
        order_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toorder_details=new Intent(adminDashboard2.this,viewOrder.class);
                toorder_details.putExtra("Uid",Uid);
                startActivity(toorder_details);
            }
        });

        payment_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topayment_status=new Intent(adminDashboard2.this,payment_status.class);
                topayment_status.putExtra("Uid",Uid);
                startActivity(topayment_status);

            }
        });

        update_prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent toupdate_prices=new Intent(adminDashboard2.this,update_prices.class);
            startActivity(toupdate_prices);

            }
        });
    }
}