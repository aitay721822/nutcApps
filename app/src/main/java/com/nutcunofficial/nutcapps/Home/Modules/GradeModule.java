package com.nutcunofficial.nutcapps.Home.Modules;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GradeModule {
    private HashMap<String,GradeInnerModule> grade;

    public GradeModule() {
        grade = new HashMap<>();
    }

    public void addClassModule(String yysem,@NonNull GradeClassModule mModule){
        if(grade.get(yysem) == null) return;
        grade.get(yysem).addClass(mModule);
    }

    public void setGradeModule(String yysem,@NonNull GradeTotalModule mModule,String TAG){
        grade.put(yysem,new GradeInnerModule(mModule,TAG));
    }

    public HashMap<String, GradeInnerModule> getGrade() {
        return grade;
    }

    public List<GradeClassModule> getClassGrade(String yysem) {
        if(grade.get(yysem) == null) return null;
        return grade.get(yysem).classModule;
    }

    public GradeTotalModule getTotalGrade(String yysem){
        if(grade.get(yysem) == null) return null;
        return grade.get(yysem).totalModule;
    }

    public String getTag(String yysem){
        if(grade.get(yysem) == null) return null;
        return grade.get(yysem).getTAG();
    }

    public static class GradeInnerModule{
        private String TAG;
        private List<GradeClassModule> classModule;
        private GradeTotalModule totalModule;

        public GradeInnerModule(@NonNull GradeTotalModule totalModule, String TAG) {
            this.classModule = new ArrayList<>();
            this.totalModule = totalModule;
            this.TAG = TAG;
        }

        public String getTAG() {
            return TAG;
        }

        public void addClass(GradeClassModule mClass){
            classModule.add(mClass);
        }

        public List<GradeClassModule> getClassModule() {
            return classModule;
        }

        public void setClassModule(List<GradeClassModule> classModule) {
            this.classModule = classModule;
        }

        public GradeTotalModule getTotalModule() {
            return totalModule;
        }

        public void setTotalModule(GradeTotalModule totalModule) {
            this.totalModule = totalModule;
        }
    }

    public static class GradeTotalModule{
        private String all_credit;
        private String not_accept_credit;
        private String accept_credit;
        private String Avg_Grade;
        private String Conduct;

        public GradeTotalModule(String all_credit, String not_accept_credit, String accept_credit, String avg_Grade, String conduct) {
            this.all_credit = all_credit;
            this.not_accept_credit = not_accept_credit;
            this.accept_credit = accept_credit;
            Avg_Grade = avg_Grade;
            Conduct = conduct;
        }

        public String getAll_credit() {
            return all_credit;
        }

        public void setAll_credit(String all_credit) {
            this.all_credit = all_credit;
        }

        public String getNot_accept_credit() {
            return not_accept_credit;
        }

        public void setNot_accept_credit(String not_accept_credit) {
            this.not_accept_credit = not_accept_credit;
        }

        public String getAccept_credit() {
            return accept_credit;
        }

        public void setAccept_credit(String accept_credit) {
            this.accept_credit = accept_credit;
        }

        public String getAvg_Grade() {
            return Avg_Grade;
        }

        public void setAvg_Grade(String avg_Grade) {
            Avg_Grade = avg_Grade;
        }

        public String getConduct() {
            return Conduct;
        }

        public void setConduct(String conduct) {
            Conduct = conduct;
        }
    }

    public static class GradeClassModule extends ClassModule{
        private int Hash;
        private String credit;
        private String group;
        private String type;
        private String grade;

        public GradeClassModule(int hash, String classroom_name, String class_name, String group, String type,String credit,String grade) {
            super(class_name,classroom_name);
            Hash = hash;
            this.credit = credit;
            this.group = group;
            this.type = type;
            this.grade = grade;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public int getHash() {
            return Hash;
        }

        public void setHash(int hash) {
            Hash = hash;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
    }

}
