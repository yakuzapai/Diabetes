package com.kandon.caramelwaffle.diabetes.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by CaramelWaffle on 15/3/2560.
 */

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.MyViewHolder> {
    private Context mContext;
    private MaterialDialog materialDialog;
    private int loop;
    private int i;
    private List<String> name = new ArrayList<>();
    private List<String> number = new ArrayList<>();
    private List<String> about = new ArrayList<>();
    String names,numbers,abouts;

    public CallAdapter(Context context) {
        // call data
        SharedPreferences info = context.getSharedPreferences("contact", MODE_PRIVATE);
        loop = info.getInt("i",0);
        for (i=1;i<=loop;i++){
            names = "name"+i;
            numbers = "number"+i;
            abouts = "about"+i;

            name.add(info.getString(names,null));
            number.add(info.getString(numbers,null));
            about.add(info.getString(abouts,"null"));
            mContext = context;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //Do some things

        holder.call_name.setText(name.get(position));
        holder.number.setText(number.get(position));
        if (!about.get(position).equals("null")){
            holder.about_name.setText("("+about.get(position)+")");
        }


        if (position % 10 == 0) {
            holder.layout.setBackgroundColor(Color.parseColor("#EF5350"));
        }
        if (position % 10 == 1) {
            holder.layout.setBackgroundColor(Color.parseColor("#EC407A"));
        }
        if (position % 10 == 2) {
            holder.layout.setBackgroundColor(Color.parseColor("#AB47BC"));
        }
        if (position % 10 == 3) {
            holder.layout.setBackgroundColor(Color.parseColor("#7E57C2"));
        }
        if (position % 10 == 4) {
            holder.layout.setBackgroundColor(Color.parseColor("#5C6BC0"));
        }
        if (position % 10 == 5) {
            holder.layout.setBackgroundColor(Color.parseColor("#2196F3"));
        }
        if (position % 10 == 6) {
            holder.layout.setBackgroundColor(Color.parseColor("#039BE5"));
        }
        if (position % 10 == 7) {
            holder.layout.setBackgroundColor(Color.parseColor("#0097A7"));
        }
        if (position % 10 == 8) {
            holder.layout.setBackgroundColor(Color.parseColor("#009688"));
        }
        if (position % 10 == 9) {
            holder.layout.setBackgroundColor(Color.parseColor("#43A047"));
        }
        if (position % 10 == 10) {
            holder.layout.setBackgroundColor(Color.parseColor("#689F38"));
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" +number.get(position) ));
                mContext.startActivity(callIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return loop;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView call_name;
        TextView about_name;
        TextView number;
        RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            call_name = (TextView) itemView.findViewById(R.id.call_name);
            about_name = (TextView) itemView.findViewById(R.id.about_name);
            number = (TextView) itemView.findViewById(R.id.number);
            layout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
        }
    }
}
