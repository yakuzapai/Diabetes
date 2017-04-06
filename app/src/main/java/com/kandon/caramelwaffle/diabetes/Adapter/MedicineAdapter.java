package com.kandon.caramelwaffle.diabetes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by CaramelWaffle on 15/3/2560.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MyViewHolder> {
    private Context mContext;
    private MaterialDialog materialDialog;
    private int loop;
    private int i;
    private List<String> names = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private List<String> kinds = new ArrayList<>();
    private List<String> qtys = new ArrayList<>();
    private List<Boolean> bb = new ArrayList<>();
    private List<Boolean> bl = new ArrayList<>();
    private List<Boolean> bd = new ArrayList<>();
    private List<Boolean> ab = new ArrayList<>();
    private List<Boolean> al = new ArrayList<>();
    private List<Boolean> ad = new ArrayList<>();
    private List<String> times = new ArrayList<>();

    public MedicineAdapter(Context context) {
        // call data
        SharedPreferences info = context.getSharedPreferences("medicine", MODE_PRIVATE);
        loop = info.getInt("i",0);
        mContext = context;
        for (i=1;i<=loop;i++){
            names.add(info.getString("medNames"+i,"null"));
            types.add(info.getString("medTypes"+i,"null"));
            kinds.add(info.getString("medKind"+i,"null"));
            qtys.add(info.getString("medQty"+i,"null"));
            bb.add(info.getBoolean("bb"+i,false));
            bl.add(info.getBoolean("bl"+i,false));
            bd.add(info.getBoolean("bd"+i,false));
            ab.add(info.getBoolean("ab"+i,false));
            al.add(info.getBoolean("al"+i,false));
            ad.add(info.getBoolean("ad"+i,false));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.med_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.med_name.setText(names.get(position));
        holder.med_qty.setText("ประเภท"+" ("+kinds.get(position)+")"+"     จำนวน  "+qtys.get(position));
        if (bb.get(position)||bl.get(position)||bd.get(position)){
            if (bb.get(position )&& !bl.get(position) && !bd.get(position)){
                holder.med_before.setText("ก่อนอาหาร : เช้า");
                times.add("ก่อนอาหาร : เช้า");
            }else if (bb.get(position )&& bl.get(position) && !bd.get(position)){
                times.add("ก่อนอาหาร : เช้า กลางวัน");
                holder.med_before.setText("ก่อนอาหาร : เช้า กลางวัน");
            }else if (bb.get(position )&& bl.get(position) && bd.get(position)){
                times.add("ก่อนอาหาร : เช้า กลางวัน เย็น");
                holder.med_before.setText("ก่อนอาหาร : เช้า กลางวัน เย็น");
            }else if(bb.get(position )&& !bl.get(position) && bd.get(position)){
                times.add("ก่อนอาหาร : เช้า เย็น");
                holder.med_before.setText("ก่อนอาหาร : เช้า เย็น");
            }
            else if (!bb.get(position )&& bl.get(position) && !bd.get(position)){
                times.add("ก่อนอาหาร : กลางวัน");
                holder.med_before.setText("ก่อนอาหาร : กลางวัน");
            }else if (!bb.get(position )&& bl.get(position) && !bd.get(position)){
                times.add("ก่อนอาหาร : กลางวัน เย็น");
                holder.med_before.setText("ก่อนอาหาร : กลางวัน เย็น");
            }else if (!bb.get(position )&& !bl.get(position) && bd.get(position)){
                times.add("ก่อนอาหาร : เย็น");
            holder.med_before.setText("ก่อนอาหาร : เย็น");
            }


        }else
        if (ab.get(position)||al.get(position)||ad.get(position)){
            if (ab.get(position )&& !al.get(position) && !ad.get(position)){
                holder.med_before.setText("หลังอาหาร : เช้า");
                times.add("หลังอาหาร : เช้า");
            }else if (ab.get(position )&& al.get(position) && !ad.get(position)){
                times.add("หลังอาหาร : เช้า กลางวัน");
                holder.med_before.setText("หลังอาหาร : เช้า กลางวัน");
            }else if (ab.get(position )&& al.get(position) && ad.get(position)){
                times.add("หลังอาหาร : เช้า กลางวัน เย็น");
                holder.med_before.setText("หลังอาหาร : เช้า กลางวัน เย็น");
            }else if(ab.get(position )&& !al.get(position) && ad.get(position)){
                times.add("หลังอาหาร : เช้า เย็น");
                holder.med_before.setText("หลังอาหาร : เช้า เย็น");
            }
            else if (!ab.get(position )&& al.get(position) && !ad.get(position)){
                times.add("หลังอาหาร : กลางวัน");
                holder.med_before.setText("หลังอาหาร : กลางวัน");
            }else if (!ab.get(position )&& al.get(position) && !ad.get(position)){
                times.add("หลังอาหาร : กลางวัน เย็น");
                holder.med_before.setText("หลังอาหาร : กลางวัน เย็น");
            }else if (!ab.get(position )&& !al.get(position) && ad.get(position)){
                times.add("หลังอาหาร : เย็น");
                holder.med_before.setText("หลังอาหาร : เย็น");
            }

        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title("ข้อมูลยา")
                        .content("ชื่อยา "+names.get(position)+"\n"+
                                "ชื่อการค้า "+types.get(position)+"\n"+
                                "ปริมาณ "+qtys.get(position)+"\n"+
                                "รับประทาน "+times.get(position)
                        )
                        .cancelable(true)
                        .positiveText("ตกลง")
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return loop;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView med_name;
        TextView med_before;
        TextView med_qty;
        RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.med_name = (TextView)itemView.findViewById(R.id.med_name);
            this.med_before = (TextView)itemView.findViewById(R.id.med_before);
            this.med_qty = (TextView)itemView.findViewById(R.id.med_qty);
            this.layout = (RelativeLayout)itemView.findViewById(R.id.layout);

        }
    }

}
