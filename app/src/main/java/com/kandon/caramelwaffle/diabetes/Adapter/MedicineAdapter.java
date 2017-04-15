package com.kandon.caramelwaffle.diabetes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kandon.caramelwaffle.diabetes.Model.Contact;
import com.kandon.caramelwaffle.diabetes.Model.Medicine;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by CaramelWaffle on 15/3/2560.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> names = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private List<String> kinds = new ArrayList<>();
    private List<String> qtys = new ArrayList<>();
    private List<String> times = new ArrayList<>();
    private List<Integer> id = new ArrayList<>();
    private Realm realm;
    private RealmResults<Medicine> listMed;
    private RealmResults<Medicine> Med_del;

    public MedicineAdapter(Context context) {
        // call data

        mContext = context;
        realm = Realm.getDefaultInstance();
        listMed = getMedicine();
        for (Medicine medicine : listMed) {
            id.add(medicine.getId());
            names.add(medicine.getMedName());
            types.add(medicine.getMedType());
            kinds.add(medicine.getMedKind());
            qtys.add(medicine.getMedQty());
            times.add(medicine.getMedTime());

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
        holder.med_qty.setText("ประเภท"+" ("+kinds.get(position)+")"+"\nจำนวน  "+qtys.get(position));
        holder.med_before.setText("เวลา : "+times.get(position));


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title("ข้อมูลยา")
                        .content(Html.fromHtml( "<b>ชื่อยา</b> "+names.get(position)+"<br>"+
                                "<b>ชื่อการค้า</b> "+types.get(position)+"<br>"+
                                "<b>ปริมาณ</b> "+qtys.get(position)+"<br>"+
                                "<b>เวลา</b> "+times.get(position))
                        )
                        .cancelable(true)
                        .positiveText("ตกลง")
                        .show();
            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title("ลบข้อมูล")
                        .content("ต้องการลบข้อมูลหรือไม่")
                        .positiveText("ตกลง")
                        .negativeText("ยกเลิก")
                        .cancelable(true)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        Med_del = getMedicineByID(id.get(position));
                                        for (Medicine medicine : Med_del) {
                                            medicine.deleteFromRealm();
                                        }
                                    }
                                });
                                notifyItemRemoved(position);
                            }
                        }).show();


                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return getMedicine().size();
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

    public RealmResults<Medicine> getMedicine() {
        return realm.where(Medicine.class).findAll();
    }

    public RealmResults<Medicine> getMedicineByID(int ids) {
        return realm.where(Medicine.class).equalTo("id",ids).findAll();
    }

}
