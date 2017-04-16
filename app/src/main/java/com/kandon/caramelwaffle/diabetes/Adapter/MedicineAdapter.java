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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private String breakfast = "";
    private String lanch = "";
    private String diner = "";
    private String sleep = "";

    private String time_report;

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

        SharedPreferences info = context.getSharedPreferences("activity_information", context.MODE_PRIVATE);
        breakfast = info.getString("breakfast", "");
        lanch = info.getString("lanch", "");
        diner = info.getString("dinner", "");
        sleep = info.getString("sleep", "");

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.med_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        if (times.get(position).equals("ก่อนอาหารเช้า")) {
            time_report = calTime(breakfast, true);
        } else if (times.get(position).equals("ก่อนอาหารกลางวัน")) {
            time_report = calTime(lanch, true);
        } else if (times.get(position).equals("ก่อนอาหารเย็น")) {
            time_report = calTime(diner, true);
        } else if (times.get(position).equals("ก่อนนอน")) {
            time_report = calTime(sleep, true);
        } else if (times.get(position).equals("หลังอาหารเช้า")) {
            time_report = calTime(breakfast, false);
        } else if (times.get(position).equals("หลังอาหารกลางวัน")) {
            time_report = calTime(lanch, false);
        } else if (times.get(position).equals("หลังอาหารเย็น")) {
            time_report = calTime(diner, false);
        }

        holder.med_name.setText(names.get(position));
        holder.med_qty.setText("ประเภท" + " (" + kinds.get(position) + ")" + "\nจำนวน  " + qtys.get(position));
        holder.med_before.setText("เวลา : " + time_report + " (" + times.get(position) + ")");


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title("ข้อมูลยา")
                        .content(Html.fromHtml("<b>ชื่อยา</b> " + names.get(position) + "<br>" +
                                "<b>ชื่อการค้า</b> " + types.get(position) + "<br>" +
                                "<b>ปริมาณ</b> " + qtys.get(position) + "<br>" +
                                "<b>เวลา</b> " + times.get(position))
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
            this.med_name = (TextView) itemView.findViewById(R.id.med_name);
            this.med_before = (TextView) itemView.findViewById(R.id.med_before);
            this.med_qty = (TextView) itemView.findViewById(R.id.med_qty);
            this.layout = (RelativeLayout) itemView.findViewById(R.id.layout);

        }
    }

    public RealmResults<Medicine> getMedicine() {
        return realm.where(Medicine.class).findAll();
    }

    public RealmResults<Medicine> getMedicineByID(int ids) {
        return realm.where(Medicine.class).equalTo("id", ids).findAll();
    }

    public String calTime(String time_string, Boolean isBefore) {
        String startDateString = time_string;
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date startDate = null;
        try {
            startDate = df.parse(startDateString);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar cal = Calendar.getInstance();
        // remove next line if you're always using the current time.
        cal.setTime(startDate);
        if (isBefore) {
            cal.add(Calendar.MINUTE, -30);
        } else {
            cal.add(Calendar.MINUTE, 30);
        }

        Date time_med = cal.getTime();

        DateFormat dfs = new SimpleDateFormat("HH:mm");
        String time_report = df.format(time_med);

        return time_report;
    }

}
