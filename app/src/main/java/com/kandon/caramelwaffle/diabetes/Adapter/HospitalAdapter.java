package com.kandon.caramelwaffle.diabetes.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kandon.caramelwaffle.diabetes.MainApplication;
import com.kandon.caramelwaffle.diabetes.Manager.Contextor;
import com.kandon.caramelwaffle.diabetes.Model.Hospital;
import com.kandon.caramelwaffle.diabetes.Model.HospitalData;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.Map;

/**
 * Created by CaramelWaffle on 15/3/2560.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.MyViewHolder> {
    private Context mContext;
    private Query mDatabaseReference;
    private ValueEventListener mValueEventListener;
    private ChildEventListener mChildEventListener;
    private MaterialDialog materialDialog;


    public HospitalAdapter(Context context, Query query) {
        mContext = context;
        mDatabaseReference = query;
        materialDialog = new MaterialDialog.Builder(mContext)
                .content("Please wait")
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                materialDialog.cancel();
                String HospitalKey = dataSnapshot.getKey();
                int hospitalKeyIndex = HospitalData.getInstance().getmHospitalKey().indexOf(HospitalKey);
                if (hospitalKeyIndex < 0) {
                    GenericTypeIndicator<Map<String, Object>> m = new GenericTypeIndicator<Map<String, Object>>() {
                    };
                    Map<String, Object> map = dataSnapshot.getValue(m);

                    HospitalData.getInstance().AddmHospitalKey(dataSnapshot.getKey());
                    HospitalData.getInstance().AddmHospitalName(map.get("name").toString());
                    HospitalData.getInstance().AddmHospitalTel(map.get("tel").toString());
                    HospitalData.getInstance().AddmHospitalImg(map.get("img").toString());

//                mHospitalKey.add(dataSnapshot.getKey());
//                mHospitalName.add(map.get("name").toString());
//                mHospitalTel.add(map.get("tel").toString());
                    Log.i("chart", "Key: " + HospitalData.getInstance().getmHospitalKey());
                    Log.i("chart", "hospitalname: " + HospitalData.getInstance().getmHospitalName());
                    Log.i("chart", "hospitaltel: " + HospitalData.getInstance().getmHospitalTel());
                    Log.i("chart", "hospitalImg: " + HospitalData.getInstance().getmHospitalImg());
                    notifyItemInserted(HospitalData.getInstance().getmHospitalKey().size() - 1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String HospitalKey = dataSnapshot.getKey();
                int hospitalKeyIndex = HospitalData.getInstance().getmHospitalKey().indexOf(HospitalKey);
                if (hospitalKeyIndex > -1) {
                    GenericTypeIndicator<Map<String, Object>> m = new GenericTypeIndicator<Map<String, Object>>() {
                    };
                    Map<String, Object> map = dataSnapshot.getValue(m);
                    HospitalData.getInstance().getmHospitalName().set(hospitalKeyIndex, map.get("name").toString());
                    HospitalData.getInstance().getmHospitalTel().set(hospitalKeyIndex, map.get("tel").toString());
                    HospitalData.getInstance().getmHospitalImg().set(hospitalKeyIndex, map.get("img").toString());
                    Log.i("chart", "hospitalname: " + HospitalData.getInstance().getmHospitalName());
                    Log.i("chart", "User Name Value is: " + HospitalData.getInstance().getmHospitalTel());
                    Log.i("chart", "hospitalImg: " + HospitalData.getInstance().getmHospitalImg());
                    notifyItemChanged(hospitalKeyIndex);
                }


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String HospitalKey = dataSnapshot.getKey();
                int hospitalKeyIndex = HospitalData.getInstance().getmHospitalKey().indexOf(HospitalKey);
                Log.i("chart", hospitalKeyIndex + "");
                if (hospitalKeyIndex > -1) {
                    HospitalData.getInstance().getmHospitalKey().remove(hospitalKeyIndex);
                    HospitalData.getInstance().getmHospitalName().remove(hospitalKeyIndex);
                    HospitalData.getInstance().getmHospitalTel().remove(hospitalKeyIndex);
                    HospitalData.getInstance().getmHospitalImg().remove(hospitalKeyIndex);
                    Log.i("chart", "hospitalname: " + HospitalData.getInstance().getmHospitalName());
                    Log.i("chart", "User Name Value is: " + HospitalData.getInstance().getmHospitalTel());
                    Log.i("chart", "hospitalImg: " + HospitalData.getInstance().getmHospitalImg());
                    notifyItemRemoved(hospitalKeyIndex);

                }


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(HospitalData.getInstance().getmHospitalImg().get(position))
                .into(holder.img);
        holder.textView.setText(HospitalData.getInstance().getmHospitalName().get(position) + " (" +
                HospitalData.getInstance().getmHospitalTel().get(position) + ")");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("chart", "click");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + HospitalData.getInstance().getmHospitalTel().get(position)));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mContext.startActivity(callIntent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return HospitalData.getInstance().getmHospitalKey().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView img;
        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.hospitalCard);
            this.img = (ImageView) itemView.findViewById(R.id.hospitalImg);
            this.textView = (TextView) itemView.findViewById(R.id.hospitalText);
        }
    }
}
