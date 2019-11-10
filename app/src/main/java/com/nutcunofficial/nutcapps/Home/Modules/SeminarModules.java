package com.nutcunofficial.nutcapps.Home.Modules;

import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.nutcApplication;

public class SeminarModules {
    private String Gender;
    private String Name;
    private String Type;

    public SeminarModules(String type, String name, String gender) {
        Gender = gender;
        Name = name;
        Type = type;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public boolean isMale(){

        if(Gender.equals(nutcApplication.getResourses().getString(R.string.male)))
            return true;
        else if (Gender.equals(nutcApplication.getResourses().getString(R.string.female)))
            return false;

        return true;
    }

    public String getName() {
        return Name;
    }

    public String getStudentNumberWithoutName(){
        if(Name.contains("(") || Name.contains(")")) {
            String subName = Name.substring(Name.indexOf("(") + 1,Name.indexOf(")"));
            return subName;
        }
        return Name;
    }

    public String getNameWithoutStudentNumber() {
        if(Name.contains("(")) {
            return Name.substring(0,Name.indexOf("("));
        }
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

}
