package com.kandon.caramelwaffle.diabetes.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by CaramelWaffle on 15/4/2560.
 */

public class Medicine extends RealmObject {
    @PrimaryKey
    int id;
    String medName;
    String medType;
    String medKind;
    String medQty;
    String medTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }

    public String getMedKind() {
        return medKind;
    }

    public void setMedKind(String medKind) {
        this.medKind = medKind;
    }

    public String getMedQty() {
        return medQty;
    }

    public void setMedQty(String medQty) {
        this.medQty = medQty;
    }

    public String getMedTime() {
        return medTime;
    }

    public void setMedTime(String medTime) {
        this.medTime = medTime;
    }
}
