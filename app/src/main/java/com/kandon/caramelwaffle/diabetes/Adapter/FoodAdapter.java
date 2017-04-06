package com.kandon.caramelwaffle.diabetes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.kandon.caramelwaffle.diabetes.Model.HospitalData;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

/**
 * Created by CaramelWaffle on 15/3/2560.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {
    private Context mContext;
    private Query mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private MaterialDialog materialDialog;
    private List<String> foodKey = new ArrayList<>();
    private List<String> foodName = new ArrayList<>();
    private List<String> foodcarbo = new ArrayList<>();
    private List<String> foodenergy = new ArrayList<>();


    public FoodAdapter(Context context, Query query) {
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
                String FoodKeyName = dataSnapshot.getKey();  // one key name
                int FoodKeyIndex = foodKey.indexOf(FoodKeyName);
                if (FoodKeyIndex < 0) {
                    // new data
                    GenericTypeIndicator<Map<String, Object>> m = new GenericTypeIndicator<Map<String, Object>>() {
                    };
                    Map<String, Object> map = dataSnapshot.getValue(m);

                    foodKey.add(dataSnapshot.getKey());
                    foodName.add(map.get("name").toString());
                    foodcarbo.add(map.get("carbo").toString());
                    foodenergy.add(map.get("energy").toString());


                    Log.i("kodomo", "Key: " +dataSnapshot.getKey() );
                    Log.i("kodomo", "Food name : " + map.get("name").toString());
                    Log.i("kodomo", "Food Carbo: " + map.get("carbo").toString());
                    Log.i("kodomo", "Food Energy: " + map.get("energy").toString());
                    notifyItemInserted(foodKey.size() - 1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String FoodKeyName = dataSnapshot.getKey();
                int FoodKeyIndex = foodKey.indexOf(FoodKeyName);
                if (FoodKeyIndex > -1) {
                    GenericTypeIndicator<Map<String, Object>> m = new GenericTypeIndicator<Map<String, Object>>() {
                    };
                    Map<String, Object> map = dataSnapshot.getValue(m);
                    foodName.set(FoodKeyIndex, map.get("name").toString());
                    foodcarbo.set(FoodKeyIndex,map.get("carbo").toString());
                    foodenergy.set(FoodKeyIndex,map.get("energy").toString());

                    Log.i("kodomo", "Food name : " + map.get("name").toString());
                    Log.i("kodomo", "Food Carbo: " + map.get("carbo").toString());
                    Log.i("kodomo", "Food Energy: " + map.get("energy").toString());
                    notifyItemChanged(FoodKeyIndex);
                }


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String FoodKeyName = dataSnapshot.getKey();
                int FoodKeyIndex = foodKey.indexOf(FoodKeyName);
                if (FoodKeyIndex > -1) {
                    foodKey.remove(FoodKeyIndex);
                    foodName.remove(FoodKeyIndex);
                    foodcarbo.remove(FoodKeyIndex);
                    foodenergy.remove(FoodKeyIndex);

                    notifyItemRemoved(FoodKeyIndex );

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    holder.textView.setText(foodName.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title(foodName.get(position))
                        .content("คาร์โบไฮเดรต : "+foodcarbo.get(position)+"\n"+
                                "พลังงาน : "+foodenergy.get(position)
                        )
                        .cancelable(true)
                        .positiveText("ตกลง")
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodKey.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layout;
        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.layout = (RelativeLayout) itemView.findViewById(R.id.item_parent);
            this.textView = (TextView) itemView.findViewById(R.id.tv_list);
        }
    }
}
