package com.nutcunofficial.nutcapps.Home.Modules;

import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.nutcApplication;

public class ClassmateModule {

    private int hash;
    private String schoolNumber;
    private String name;
    private String Gender;

    public ClassmateModule(int hash, String schoolNumber, String name, String gender) {
        this.hash = hash;
        this.schoolNumber = schoolNumber;
        this.name = name;
        Gender = gender;
    }

    public boolean isMale(){

        if(Gender.equals(nutcApplication.getResourses().getString(R.string.male)))
            return true;
        else if (Gender.equals(nutcApplication.getResourses().getString(R.string.female)))
            return false;

        return true;
    }


    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
