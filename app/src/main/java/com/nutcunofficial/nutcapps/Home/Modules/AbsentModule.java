package com.nutcunofficial.nutcapps.Home.Modules;

import android.util.Log;

import androidx.annotation.NonNull;

import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.nutcApplication;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class AbsentModule {
    private static final String TAG = "AbsentModule";
    private AbsentTotalModule totalModule;
    private List<AbsentClassModule> classModules;

    public AbsentModule() {
        this.classModules = new ArrayList<>();
    }

    public void addClass(AbsentClassModule absentClassModule){
        this.classModules.add(absentClassModule);
    }

    public AbsentTotalModule getTotalModule() {
        return totalModule;
    }

    public void setTotalModule(@NonNull AbsentTotalModule totalModule) {
        this.totalModule = totalModule;
    }

    public List<AbsentClassModule> getClassModules() {
        return classModules;
    }

    public void setClassModules(List<AbsentClassModule> classModules) {
        this.classModules = classModules;
    }

    public static class AbsentTotalModule{
        private final int VARIABLE_COUNT = 12;
        private String absent;
        private String late;
        private String earlyLeave;
        private String officialLeave;
        private String PersonalLeave;
        private String SickLeave;
        private String FuneralLeave;
        private String MenstrualLeave;
        private String MarriageLeave;
        private String MaternityLeave;
        private String PrenatalLeave;
        private String Grade;

        public AbsentTotalModule(String absent, String late, String earlyLeave, String offcialLeave, String personalLeave, String sickLeave, String funeralLeave, String menstrualLeave, String marriageLeave, String maternityLeave, String prenatalLeave, String grade) {
            this.absent = absent;
            this.late = late;
            this.earlyLeave = earlyLeave;
            this.officialLeave = offcialLeave;
            PersonalLeave = personalLeave;
            SickLeave = sickLeave;
            FuneralLeave = funeralLeave;
            MenstrualLeave = menstrualLeave;
            MarriageLeave = marriageLeave;
            MaternityLeave = maternityLeave;
            PrenatalLeave = prenatalLeave;
            Grade = grade;
        }

        public AbsentTotalModule(List<String> data){
            if(data.size() < 12 ) return;
            for(int i = 0 ; i < data.size();i++){
                String value = data.get(i);
                set(value,i);
            }
        }

        public List<KeyValueModule<String>> toList(){
            List<KeyValueModule<String>> result = new ArrayList<>();
            // resource string array
            String[] titles = nutcApplication.getResourses().getStringArray(R.array.AbsentTitle);

            if(titles.length != VARIABLE_COUNT) { Log.e(TAG, "toList: title arrays length not equal to VARIABLE_COUNT" ); return null; }

            for(int i = 0; i < titles.length;i++)
                result.add(new KeyValueModule<>(titles[i],get(i)));

            return result;
        }

        public String get(int position){
            String result = null;
            switch (position){
                case 0:
                    result = this.absent;
                    break;
                case 1:
                    result = this.late;
                    break;
                case 2:
                    result = this.earlyLeave;
                    break;
                case 3:
                    result = this.officialLeave;
                    break;
                case 4:
                    result = this.PersonalLeave;
                    break;
                case 5:
                    result = this.SickLeave;
                    break;
                case 6:
                    result = this.FuneralLeave;
                    break;
                case 7:
                    result = this.MenstrualLeave;
                    break;
                case 8:
                    result = this.MarriageLeave;
                    break;
                case 9:
                    result = this.MaternityLeave;
                    break;
                case 10:
                    result = this.PrenatalLeave;
                    break;
                case 11:
                    result = this.Grade;
                    break;
            }
            return (result != null) ? result : "";
        }

        public void set(String value,int position){
            switch (position){
                case 0:
                    this.absent = value;
                    break;
                case 1:
                    this.late = value;
                    break;
                case 2:
                    this.earlyLeave = value;
                    break;
                case 3:
                    this.officialLeave=value;
                    break;
                case 4:
                    this.PersonalLeave=value;
                    break;
                case 5:
                    this.SickLeave=value;
                    break;
                case 6:
                    this.FuneralLeave = value;
                    break;
                case 7:
                    this.MenstrualLeave=value;
                    break;
                case 8:
                    this.MarriageLeave=value;
                    break;
                case 9:
                    this.MaternityLeave=value;
                    break;
                case 10:
                    this.PrenatalLeave=value;
                    break;
                case 11:
                    this.Grade=value;
                    break;
            }
        }

        public String getGrade() {
            return Grade;
        }

        public void setGrade(String grade) {
            Grade = grade;
        }

        public String getPrenatalLeave() {
            return PrenatalLeave;
        }

        public void setPrenatalLeave(String prenatalLeave) {
            PrenatalLeave = prenatalLeave;
        }

        public String getMaternityLeave() {
            return MaternityLeave;
        }

        public void setMaternityLeave(String maternityLeave) {
            MaternityLeave = maternityLeave;
        }

        public String getMarriageLeave() {
            return MarriageLeave;
        }

        public void setMarriageLeave(String marriageLeave) {
            MarriageLeave = marriageLeave;
        }

        public String getMenstrualLeave() {
            return MenstrualLeave;
        }

        public void setMenstrualLeave(String menstrualLeave) {
            MenstrualLeave = menstrualLeave;
        }

        public String getFuneralLeave() {
            return FuneralLeave;
        }

        public void setFuneralLeave(String funeralLeave) {
            FuneralLeave = funeralLeave;
        }

        public String getSickLeave() {
            return SickLeave;
        }

        public void setSickLeave(String sickLeave) {
            SickLeave = sickLeave;
        }

        public String getPersonalLeave() {
            return PersonalLeave;
        }

        public void setPersonalLeave(String personalLeave) {
            PersonalLeave = personalLeave;
        }

        public String getOfficialLeave() {
            return officialLeave;
        }

        public void setOfficialLeave(String officialLeave) {
            this.officialLeave = officialLeave;
        }

        public String getEarlyLeave() {
            return earlyLeave;
        }

        public void setEarlyLeave(String earlyLeave) {
            this.earlyLeave = earlyLeave;
        }

        public String getLate() {
            return late;
        }

        public void setLate(String late) {
            this.late = late;
        }

        public String getAbsent() {
            return absent;
        }

        public void setAbsent(String absent) {
            this.absent = absent;
        }
    }

    public static class AbsentClassModule extends ClassModule{
        private String group;
        private String type;
        private String credit;
        private String teacher;
        private String[] status;
        private String[] detailStat;

        public AbsentClassModule(String classRoom_Name,String class_Name,String group,String type,String credit,String teacher,String[] status,String[] detailStat) {
            super(class_Name, classRoom_Name);
            this.group=group;
            this.type=type;
            this.credit=credit;
            this.teacher=teacher;
            this.status=status;
            this.detailStat=detailStat;
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

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String[] getStatus() {
            return status;
        }

        public void setStatus(String[] status) {
            this.status = status;
        }

        public String[] getDetailStat() {
            return detailStat;
        }

        public void setDetailStat(String[] detailStat) {
            this.detailStat = detailStat;
        }
    }

}
