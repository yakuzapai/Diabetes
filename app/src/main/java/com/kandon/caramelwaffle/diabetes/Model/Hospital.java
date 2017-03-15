package com.kandon.caramelwaffle.diabetes.Model;

/**
 * Created by CaramelWaffle on 14/3/2560.
 */

public class Hospital {
    public String name;
    public String tel;

    public Hospital(){

    }

    public Hospital(String name, String tel){
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
