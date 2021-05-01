package com.example.clean_mate2;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;
import static android.icu.text.Transliterator.*;
import static androidx.core.content.ContextCompat.startActivity;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUser;
    String Uid;
    public CustomAdapter(Context context,List<User> users)
    {
        mContext=context;
        mUser=users;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView fullname,hostel,phoneno,roomno;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            fullname=itemView.findViewById(R.id.user_name);
            hostel=itemView.findViewById(R.id.hostel);
            phoneno=itemView.findViewById(R.id.phone_no);
            roomno=itemView.findViewById(R.id.room_no);
        }

        @Override
        public void onClick(View v) {
            int position=this.getAdapterPosition();
            //Toast.makeText(mContext,"position is "+position,Toast.LENGTH_SHORT).show();
            User nu=mUser.get(position);
            Uid=nu.getPhoneNo();

            Intent toadminDashboard2=new Intent(mContext,adminDashboard2.class);
            toadminDashboard2.putExtra("Uid", Uid);
            Log.d(TAG, "onClick: phno"+Uid);
            mContext.startActivity(toadminDashboard2);
        }
    }
    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_details,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {

        User uploadCurrent=mUser.get(position);
        holder.fullname.setText(uploadCurrent.getFullName());
        holder.phoneno.setText(uploadCurrent.getPhoneNo());
        holder.roomno.setText(uploadCurrent.getRoomNo());
        holder.hostel.setText(uploadCurrent.getHostel());

        //Picasso.get().load(uploadCurrent.getImageUrl()).fit().centerCrop().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }
}





