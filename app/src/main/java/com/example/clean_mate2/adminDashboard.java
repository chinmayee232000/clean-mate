package com.example.clean_mate2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class adminDashboard extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private DocumentReference mDatabaseReference;
    private DocumentReference dbref;
    private CollectionReference cref;
    private List<User> mUser;
    private String admin_Uid;
    public ArrayList<String> user_phone_no;
    private String Uid;
    private String TAG="adminDashboard";
//FIGURE OUT A WAY TO GET ALL USERS
    // THE CODE CURRENTLY PRINTS DATA OF LAUNDRY SERVICE PROVIDER ONLY
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        mRecyclerView=(RecyclerView)findViewById(R.id.Recyclerviewadmin);
        mRecyclerView.setHasFixedSize(true);
        //admin_Uid=getIntent().getStringExtra("admin_Uid");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUser=new ArrayList<>();
        user_phone_no= new ArrayList<String>();
        //Uid= getIntent().getStringExtra("Uid");
        
         getData(user_phone_no);


    }
    public void getData(ArrayList<String> user_phone_no)
    {
        cref=FirebaseFirestore.getInstance().collection("Users");
        cref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                    user_phone_no.add(documentSnapshot.getString("phonenumber"));
                    Log.d(TAG, "onSuccess: List made "+documentSnapshot.getString("phonenumber"));
                    for(String s:user_phone_no)
                        Log.d(TAG, "onSuccess: number "+s);
                }
                callAdapter(user_phone_no);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(adminDashboard.this,"Cannot access database",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void callAdapter(ArrayList<String> user_phone_no)
    {
        Log.d(TAG, "onCreate: back from function ");
        int n=user_phone_no.size();
        for(int i=0;i<n;i++) {
            Log.d(TAG, "onCreate: in loop ");
            dbref=FirebaseFirestore.getInstance().collection(user_phone_no.get(i)).document("Details");
            Log.d(TAG, "onCreate: "+user_phone_no.size());
            dbref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = new User(documentSnapshot.get("FullName").toString(), documentSnapshot.get("phonenumber").toString(), documentSnapshot.get("Hostel").toString(), documentSnapshot.get("RoomNo").toString());
                    String name = user.getFullName();
                    String phoneno = user.getPhoneNo();
                    String roomno = user.getRoomNo();
                    String hostel = user.getHostel();
                    Log.d(TAG, "onSuccess: Retrieved values from database " + name + phoneno + roomno + hostel);
                    mUser.add(user);

                    mAdapter = new CustomAdapter(adminDashboard.this, mUser);
                    mRecyclerView.setAdapter(mAdapter);
                }


            });

        }

    }
}