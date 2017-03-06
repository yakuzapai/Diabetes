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
        SharedPreferences info = getContext().getSharedPreferences("information", getContext().MODE_PRIVATE);
        name.setText(info.getString("username","No information"));
        dob.setText(info.getString("dob","No information"));
        age.setText(info.getString("age","No information")+" ปี");
        gender.setText(info.getString("gender","No information"));
        blood.setText(info.getString("blood","No information"));
        weight.setText(info.getString("weight","No information"));
        height.setText(info.getString("height","No information"));
        bmi.setText(info.getString("bmi","No information"));
        UserMedicalCondition.setText(info.getString("UserMedicalCondition","No information"));
        DangerMedical.setText(info.getString("DangerMedical","No information"));
        smoke.setText(info.getString("smoke","No information"));
        drink.setText(info.getString("drink","No information"));
        blood_pressure.setText(info.getString("blood_pressure","No information"));
        bloodSugar.setText(info.getString("bloodSugar","No information"));

        if (name.getText().equals("")){
            name.setText("No information");
        }
        if (dob.getText().equals("")){
            dob.setText("No information");
        }
        if (age.getText().equals("")){
            age.setText("No information");
        }
        if (gender.getText().equals("")){
            gender.setText("No information");
        }
        if (blood.getText().equals("")){
            blood.setText("No information");
        }
        if (weight.getText().equals("")){
            weight.setText("No information");
        }
        if (height.getText().equals("")){
            height.setText("No information");
        }
        if (bmi.getText().equals("")){
            bmi.setText("No information");
        }
        if (UserMedicalCondition.getText().equals("")){
            UserMedicalCondition.setText("No information");
        }
        if (DangerMedical.getText().equals("")){
            DangerMedical.setText("No information");
        }
        if (smoke.getText().equals("")){
            smoke.setText("No information");
        }
        if (drink.getText().equals("")){
            drink.setText("No information");
        }
        if (blood_pressure.getText().equals("/")){
            blood_pressure.setText("No information");
        }
        if (bloodSugar.getText().equals("")){
            bloodSugar.setText("No information");
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
