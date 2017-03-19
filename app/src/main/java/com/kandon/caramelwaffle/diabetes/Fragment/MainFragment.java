package com.kandon.caramelwaffle.diabetes.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kandon.caramelwaffle.diabetes.Activity.Information;
import com.kandon.caramelwaffle.diabetes.Activity.LoginActivity;
import com.kandon.caramelwaffle.diabetes.Activity.MainActivity;
import com.kandon.caramelwaffle.diabetes.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MainFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Toolbar toolbar;
    CircleImageView imageView;
    Context context;

    FloatingActionButton floatingActionButton;

    TextView userDisplayName;
    TextView name;
    TextView dob;
    TextView age;
    TextView gender;
    TextView blood;
    TextView weight;
    TextView height;
    TextView bmi;
    TextView UserMedicalCondition;
    TextView DangerMedical;
    TextView smoke;
    TextView drink;
    TextView blood_pressure;
    TextView bloodSugar;

    public MainFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);


        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        context = getActivity();
        initInstances(rootView, savedInstanceState);
        setInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.floatingActionButton);
        imageView = (CircleImageView)rootView.findViewById(R.id.profile_image);
        userDisplayName = (TextView)rootView.findViewById(R.id.user_display_name);
        name = (TextView)rootView.findViewById(R.id.name);
        dob = (TextView)rootView.findViewById(R.id.dob);
        age = (TextView)rootView.findViewById(R.id.age);
        gender = (TextView)rootView.findViewById(R.id.gender);
        blood = (TextView)rootView.findViewById(R.id.blood);
        weight = (TextView)rootView.findViewById(R.id.weight);
        height = (TextView)rootView.findViewById(R.id.height);
        bmi = (TextView)rootView.findViewById(R.id.bmi);
        UserMedicalCondition = (TextView)rootView.findViewById(R.id.UserMedicalCondition);
        DangerMedical = (TextView)rootView.findViewById(R.id.DangerMedical);
        smoke = (TextView)rootView.findViewById(R.id.smoke);
        drink = (TextView)rootView.findViewById(R.id.drink);
        blood_pressure = (TextView)rootView.findViewById(R.id.blood_pressure);
        bloodSugar = (TextView)rootView.findViewById(R.id.bloodSugar);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Information.class);
                startActivity(intent);
            }
        });
    }

    private void setInstances(View rootView, Bundle savedInstanceState) {
        user = firebaseAuth.getCurrentUser();
        if (user!=null){
            Glide.with(context)
                    .load(user.getPhotoUrl())
                    .into(imageView);
            userDisplayName.setText(user.getDisplayName());
            getUserInformation();
        }else {

            SharedPreferences.Editor editor = getActivity().getSharedPreferences("LOGINTOKEN", getActivity().MODE_PRIVATE).edit();
            editor.putBoolean("login", false);
            editor.apply();

            SharedPreferences.Editor editor2 = getActivity().getSharedPreferences("information_save", getActivity().MODE_PRIVATE).edit();
            editor2.putBoolean("isSave", false);
            editor2.apply();

            Intent intent = new Intent(context,LoginActivity.class);
            startActivity(intent);
            getActivity().finish();

        }
    }

    private void getUserInformation() {
        SharedPreferences info = getContext().getSharedPreferences("activity_information", getContext().MODE_PRIVATE);
        name.setText(info.getString("username","ไม่มีข้อมูล"));
        dob.setText(info.getString("dob","ไม่มีข้อมูล"));
        age.setText(info.getString("age","ไม่มีข้อมูล")+" ปี");
        gender.setText(info.getString("gender","ไม่มีข้อมูล"));
        blood.setText(info.getString("blood","ไม่มีข้อมูล"));
        weight.setText(info.getString("weight","ไม่มีข้อมูล"));
        height.setText(info.getString("height","ไม่มีข้อมูล"));
        bmi.setText(info.getString("bmi","ไม่มีข้อมูล"));
        UserMedicalCondition.setText(info.getString("UserMedicalCondition","ไม่มีข้อมูล"));
        DangerMedical.setText(info.getString("DangerMedical","ไม่มีข้อมูล"));
        smoke.setText(info.getString("smoke","ไม่มีข้อมูล"));
        drink.setText(info.getString("drink","ไม่มีข้อมูล"));
        blood_pressure.setText(info.getString("blood_pressure","ไม่มีข้อมูล"));
        bloodSugar.setText(info.getString("bloodSugar","ไม่มีข้อมูล"));

        if (name.getText().equals("")){
            name.setText("ไม่มีข้อมูล");
        }
        if (dob.getText().equals("")){
            dob.setText("ไม่มีข้อมูล");
        }
        if (age.getText().equals("")){
            age.setText("ไม่มีข้อมูล");
        }
        if (gender.getText().equals("")){
            gender.setText("ไม่มีข้อมูล");
        }
        if (blood.getText().equals("")){
            blood.setText("ไม่มีข้อมูล");
        }
        if (weight.getText().equals("")){
            weight.setText("ไม่มีข้อมูล");
        }
        if (height.getText().equals("")){
            height.setText("ไม่มีข้อมูล");
        }
        if (bmi.getText().equals("")){
            bmi.setText("ไม่มีข้อมูล");
        }
        if (UserMedicalCondition.getText().equals("")){
            UserMedicalCondition.setText("ไม่มีข้อมูล");
        }
        if (DangerMedical.getText().equals("")){
            DangerMedical.setText("ไม่มีข้อมูล");
        }
        if (smoke.getText().equals("")){
            smoke.setText("ไม่มีข้อมูล");
        }
        if (drink.getText().equals("")){
            drink.setText("ไม่มีข้อมูล");
        }
        if (blood_pressure.getText().equals("/")){
            blood_pressure.setText("ไม่มีข้อมูล");
        }
        if (bloodSugar.getText().equals("")){
            bloodSugar.setText("ไม่มีข้อมูล");
        }




    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

}
