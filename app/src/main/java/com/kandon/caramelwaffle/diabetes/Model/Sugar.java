package com.kandon.caramelwaffle.diabetes.Model;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by CaramelWaffle on 9/4/2560.
 */

public class Sugar extends RealmObject {
    @PrimaryKey
    int id;
    float sugarValue;
    @Required
    String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSugarValue() {
        return sugarValue;
    }

    public void setSugarValue(float sugarValue) {
        this.sugarValue = sugarValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
