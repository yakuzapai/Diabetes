package com.kandon.caramelwaffle.diabetes.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by CaramelWaffle on 15/4/2560.
 */

public class Contact extends RealmObject {
    @PrimaryKey
    int id;
    @Required
    String name;
    @Required
    String number;
    String about;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
