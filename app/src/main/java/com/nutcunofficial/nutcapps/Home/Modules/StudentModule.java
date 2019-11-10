package com.nutcunofficial.nutcapps.Home.Modules;

import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.nutcApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentModule {

    public class display{
        private String title;
        private String data;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public display(String title, String data) {
            this.title = title;
            this.data = data;
        }
    }

    private String Class_Type;

    private String Student_Type;

    private String Student_Sec_Type;

    public StudentModule(String student_Type, String student_Sec_Type, String class_Type, HashMap<String, String> data) {
        Class_Type = class_Type;
        Student_Type = student_Type;
        Student_Sec_Type = student_Sec_Type;
        this.data = data;
    }

    public String getClass_Type() {
        return Class_Type;
    }

    public void setClass_Type(String class_Type) {
        Class_Type = class_Type;
    }

    public String getStudent_Type() {
        return Student_Type;
    }

    public void setStudent_Type(String student_Type) {
        Student_Type = student_Type;
    }

    public String getStudent_Sec_Type() {
        return Student_Sec_Type;
    }

    public void setStudent_Sec_Type(String student_Sec_Type) {
        Student_Sec_Type = student_Sec_Type;
    }

    private HashMap<String,String> data;

    public List<display> toList(){
        if(data == null || data.size() == 0) return null;
        List<display> result = new ArrayList<>();
        for(Map.Entry<String,String> e : data.entrySet()){
            result.add(new display(e.getKey(),e.getValue()));
        }
        return result;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}
