package com.example.clean_mate2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowUploads extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private CollectionReference mDatabaseReference;
    private List<Upload> mUploads;
    private String Uid;
    private String TAG="ShowUploads";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_uploads);
        mRecyclerView=findViewById(R.id.Recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mUploads=new ArrayList<>();
        //mAdapter=new ImageAdapter(this,mUploads);
        Uid= getIntent().getStringExtra("Uid");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setAdapter(mAdapter);
        mDatabaseReference=FirebaseFirestore.getInstance().collection(Uid).document("Details").collection("Images");
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
                mAdapter= new ImageAdapter(ShowUploads.this,mUploads);
                mRecyclerView.setAdapter(mAdapter);
            }
        });



            /*
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
              for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
              {
                 //QueryDocumentSnapshot always exists so no check
                  Upload upload=documentSnapshot.toObject(Upload.class);
                  mUploads.add(upload);
              }
              mAdapter=new ImageAdapter(ShowUploads.this,mUploads);
              mRecyclerView.setAdapter(mAdapter);
            }
        });
        */


    }


}