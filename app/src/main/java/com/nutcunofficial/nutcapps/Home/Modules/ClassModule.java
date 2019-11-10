package com.nutcunofficial.nutcapps.Home.Modules;

public class ClassModule {
    private String Class_Name;
    private String ClassRoom_Name;

    public ClassModule(String class_Name, String classRoom_Name) {
        Class_Name = class_Name;
        ClassRoom_Name = classRoom_Name;
    }

    public String getClass_Name() {
        return Class_Name;
    }

    public void setClass_Name(String class_Name) {
        Class_Name = class_Name;
    }

    public String getClassRoom_Name() {
        return ClassRoom_Name;
    }

    public void setClassRoom_Name(String classRoom_Name) {
        ClassRoom_Name = classRoom_Name;
    }
}
