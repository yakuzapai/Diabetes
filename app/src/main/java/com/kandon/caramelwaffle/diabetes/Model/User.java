package com.kandon.caramelwaffle.diabetes.Model;

/**
 * Created by CaramelWaffle on 14/3/2560.
 */

public class User {
    public String username;
    public String dob;
    public String age;
    public String gender;
    public String blood;
    public String height;
    public String weight;
    public String bmi;
    public String userMedicalCondition;
    public String dangerMedical;
    public String smoke;
    public String drink;
    public String blood_pressure;
    public String bloodSugar;
    public String userBP1;
    public String userBP2;


    public User(){

    }

    public User(String username,String dob,String age,String gender, String blood ,String height,String weight,
                String bmi,String userMedicalCondition,String dangerMedical,String smoke,String drink,String blood_pressure,
                String bloodSugar,String userBP1,String userBP2
    ){
        this.username = username;
        this.dob = dob;
        this.age = age;
        this.gender = gender;
        this.blood = blood;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.userMedicalCondition = userMedicalCondition;
        this.dangerMedical = dangerMedical;
        this.smoke = smoke;
        this.drink = drink;
        this.blood_pressure =blood_pressure;
        this.bloodSugar = bloodSugar;
        this.userBP1 = userBP1;
        this.userBP2 = userBP2;
    }
}
