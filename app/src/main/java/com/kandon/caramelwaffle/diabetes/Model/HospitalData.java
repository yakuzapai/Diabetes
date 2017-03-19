package com.kandon.caramelwaffle.diabetes.Model;

import android.content.Context;

import com.kandon.caramelwaffle.diabetes.Manager.Contextor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HospitalData {

    private static HospitalData instance;

    public static HospitalData getInstance() {
        if (instance == null)
            instance = new HospitalData();
        return instance;
    }

    private Context mContext;
    private List<String> mHospitalKey = new ArrayList<>();
    private List<String> mHospitalName = new ArrayList<>();
    private List<String> mHospitalTel = new ArrayList<>();
    private List<String> mHospitalImg = new ArrayList<>();
    private int size;

    private HospitalData() {
        mContext = Contextor.getInstance().getContext();
    }

    public List<String> getmHospitalKey() {
        return mHospitalKey;
    }

    public void AddmHospitalKey(String mHospitalKey) {
        this.mHospitalKey.add(mHospitalKey);
    }

    public List<String> getmHospitalName() {
        return mHospitalName;
    }

    public void AddmHospitalName(String mHospitalName) {
        this.mHospitalName.add(mHospitalName);
    }

    public List<String> getmHospitalTel() {
        return mHospitalTel;
    }

    public void AddmHospitalTel(String mHospitalTel) {
        this.mHospitalTel.add(mHospitalTel);
    }

    public List<String> getmHospitalImg() {
        return mHospitalImg;
    }

    public void AddmHospitalImg(String mHospitalImg) {
        this.mHospitalImg.add(mHospitalImg);
    }

}
