package com.kandon.caramelwaffle.diabetes.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by CaramelWaffle on 15/3/2560.
 */

public class DessertAdapter extends RecyclerView.Adapter<DessertAdapter.MyViewHolder> {
    private Context mContext;
    private Query mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private MaterialDialog materialDialog;
    private List<String> dessertKey = new ArrayList<>();
    private List<String> dessertName = new ArrayList<>();
    private List<String> dessertcarbo = new ArrayList<>();
    private List<String> dessertenergy = new ArrayList<>();


    public DessertAdapter(Context context, Query query) {
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
                int DessertKeyIndex = dessertKey.indexOf(FoodKeyName);
                if (DessertKeyIndex < 0) {
                    // new data
                    GenericTypeIndicator<Map<String, Object>> m = new GenericTypeIndicator<Map<String, Object>>() {
                    };
                    Map<String, Object> map = dataSnapshot.getValue(m);

                    dessertKey.add(dataSnapshot.getKey());
                    dessertName.add(map.get("name").toString());
                    dessertcarbo.add(map.get("carbo").toString());
                    dessertenergy.add(map.get("energy").toString());

                    notifyItemInserted(dessertKey.size() - 1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String FoodKeyName = dataSnapshot.getKey();
                int FoodKeyIndex = dessertKey.indexOf(FoodKeyName);
                if (FoodKeyIndex > -1) {
                    GenericTypeIndicator<Map<String, Object>> m = new GenericTypeIndicator<Map<String, Object>>() {
                    };
                    Map<String, Object> map = dataSnapshot.getValue(m);
                    dessertName.set(FoodKeyIndex, map.get("name").toString());
                    dessertcarbo.set(FoodKeyIndex,map.get("carbo").toString());
                    dessertenergy.set(FoodKeyIndex,map.get("energy").toString());

                    notifyItemChanged(FoodKeyIndex);
                }


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String FoodKeyName = dataSnapshot.getKey();
                int FoodKeyIndex = dessertKey.indexOf(FoodKeyName);
                if (FoodKeyIndex > -1) {
                    dessertKey.remove(FoodKeyIndex);
                    dessertName.remove(FoodKeyIndex);
                    dessertcarbo.remove(FoodKeyIndex);
                    dessertenergy.remove(FoodKeyIndex);

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
    holder.textView.setText(dessertName.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title(dessertName.get(position))
                        .content("คาร์โบไฮเดรต : "+dessertcarbo.get(position)+"\n"+
                                "พลังงาน : "+dessertenergy.get(position)
                        )
                        .cancelable(true)
                        .positiveText("ตกลง")
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dessertKey.size();
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
