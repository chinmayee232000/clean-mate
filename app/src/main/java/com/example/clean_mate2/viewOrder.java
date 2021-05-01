package com.example.clean_mate2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class viewOrder extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private CollectionReference mDatabaseReference;
    private List<Upload> mUploads;
    private String Uid;
    private String TAG="viewOrder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        mRecyclerView=findViewById(R.id.Recyclerview_view_order);
        mRecyclerView.setHasFixedSize(true);
        mUploads=new ArrayList<>();
        //mAdapter=new ImageAdapter(this,mUploads);
        Uid= getIntent().getStringExtra("Uid");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setAdapter(mAdapter);
        mDatabaseReference= FirebaseFirestore.getInstance().collection(Uid).document("Details").collection("Images");
        mDatabaseReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    //QueryDocumentSnapshot always exists so no check

                    Upload upload=new Upload(documentSnapshot.getString("ImageName"),documentSnapshot.getString("imageUrl"));
                    String name=upload.getName();
                    String url=upload.getImageUrl();
                    Log.d(TAG, "onSuccess: value"+name+" "+url);
                    mUploads.add(upload);

                }
                mAdapter= new ImageAdapter(viewOrder.this,mUploads);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }
}