package com.example.clean_mate2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AddImages extends AppCompatActivity {
private static final int PICK_IMAGE_REQUEST =1;
private Button mButtonChooseImage,mUpload,mShowAll;
private Button mButtonUpload;
private EditText mFileName;
private ProgressBar mprogressBar;
private ImageView mImage;
private Uri mImageUri;
private DatabaseReference mDatabaseRef;
private FirebaseFirestore firestore;
private StorageReference mStorageRef;
private FirebaseAuth auth;
String Uid,StringmFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_images);
        mButtonChooseImage=findViewById(R.id.button2);
        mFileName=findViewById(R.id.textView17);

        mImage=findViewById(R.id.imageView);
        mprogressBar=findViewById(R.id.progressBar);
        mUpload=findViewById(R.id.button4);
        mShowAll=findViewById(R.id.button5);
        mStorageRef= FirebaseStorage.getInstance().getReference();
        //mDatabaseRef= FirebaseDatabase.getInstance().getReference();
        firestore=FirebaseFirestore.getInstance();
        //auth=FirebaseAuth.getInstance();
        Uid=getIntent().getStringExtra("Uid");
        //UploadTask uploadTask = FirebaseStorage.getInstance().child("newFolder").getReference(userUid).putBytes(yourPhotoToByteArray);
       // uploadTask.setOnsuccessListener

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openFileChooser();
            }
        });
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringmFileName=mFileName.getText().toString();
                uploadFile();
            }
        });
        mShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent toShowUploads = new Intent(AddImages.this,ShowUploads.class);
             toShowUploads.putExtra("Uid",Uid);
             startActivity(toShowUploads);
            }
        });

    }
    void openFileChooser()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    private String getFileExtension(Uri uri)
    {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }
    void uploadFile()
    {
        if(mImageUri!=null)
        {
            final StorageReference fileReference= mStorageRef.child("images").child(System.currentTimeMillis()+'.'+getFileExtension(mImageUri));


           fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 mprogressBar.setVisibility(View.INVISIBLE);
                 Toast.makeText(AddImages.this,"Upload Successful",Toast.LENGTH_SHORT).show();
                 fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                         HashMap<String,Object> imagemap=new HashMap<>();
                         imagemap.put("imageUrl",uri.toString());
                         imagemap.put("ImageName",StringmFileName);

                         firestore.collection(Uid).document("Details").collection("Images").add(imagemap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                             @Override
                             public void onComplete(@NonNull Task<DocumentReference> task) {
                                 if(task.isSuccessful()) {
                                     Toast.makeText(AddImages.this, "Successful", Toast.LENGTH_SHORT).show();
                                 }
                                 else {
                                     Toast.makeText(AddImages.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });

                             /*
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()) {
                                     Toast.makeText(AddImages.this, "Successful", Toast.LENGTH_SHORT).show();
                                 }
                                 else {
                                     Toast.makeText(AddImages.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });
                         */


                     }
                 });

               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                 Toast.makeText(AddImages.this,e.getMessage(),Toast.LENGTH_SHORT).show();
               }
           }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                 mprogressBar.setVisibility(View.VISIBLE);
               }
           });
        }
        else
        {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK
        &&data !=null && data.getData()!=null)
        {
            mImageUri=data.getData();
            Picasso.get().load(mImageUri).into(mImage);
        }
    }
}