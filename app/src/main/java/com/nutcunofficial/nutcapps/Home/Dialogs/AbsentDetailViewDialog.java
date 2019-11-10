package com.nutcunofficial.nutcapps.Home.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.nutcunofficial.nutcapps.R;

public class AbsentDetailViewDialog extends AppCompatDialogFragment {

    private String classInfo;
    private String classroom;
    private String teacher;
    private String absentDetailValue ;

    public static AbsentDetailViewDialog newInstance(String classInfo,String classroom,String teacher,String absentDetailValue){
        return new AbsentDetailViewDialog(classInfo,classroom,teacher,absentDetailValue);
    }


    private AbsentDetailViewDialog(String classInfo, String classroom, String teacher, String absentDetailValue) {
        this.classInfo = classInfo;
        this.classroom = classroom;
        this.teacher = teacher;
        this.absentDetailValue = absentDetailValue;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_detail_absent,null);

        dialog.setView(view);

        TextView ClassInfo = view.findViewById(R.id.classInfo_view);
        TextView Classroom = view.findViewById(R.id.classroom);
        TextView Teacher = view.findViewById(R.id.teacher_value);
        TextView Detail = view.findViewById(R.id.absent_detail_value);

        ClassInfo.setText(classInfo);
        Classroom.setText(classroom);
        Teacher.setText(teacher);
        Detail.setText(absentDetailValue);

        return dialog.create();
    }



}
