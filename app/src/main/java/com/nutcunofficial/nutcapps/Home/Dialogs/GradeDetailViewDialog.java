package com.nutcunofficial.nutcapps.Home.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.nutcunofficial.nutcapps.R;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class GradeDetailViewDialog extends AppCompatDialogFragment {

    private String classInfo_view;
    private String classroom;
    private String group;
    private String course;
    private String credit_value;
    private String grade_value;

    public static GradeDetailViewDialog newInstance(String classInfo_view, String classroom, String group, String course, String credit_value, String grade_value){
        return new GradeDetailViewDialog(classInfo_view,classroom,group,course,credit_value,grade_value);
    }

    private GradeDetailViewDialog(String classInfo_view, String classroom, String group, String course, String credit_value, String grade_value) {
        this.classInfo_view = classInfo_view;
        this.classroom = classroom;
        this.group = group;
        this.course = course;
        this.credit_value = credit_value;
        this.grade_value = grade_value;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_detail_grade,null);
        builder.setView(view);

        TextView tv_classInfo = view.findViewById(R.id.classInfo_view);
        TextView tv_classroom = view.findViewById(R.id.classroom);
        TextView tv_group = view.findViewById(R.id.group);
        TextView tv_course = view.findViewById(R.id.course);
        TextView tv_credit_value = view.findViewById(R.id.credit_value);
        TextView tv_grade_value = view.findViewById(R.id.grade_value);

        tv_classInfo.setText(classInfo_view);
        tv_classroom.setText(classroom);
        tv_group.setText(group);
        tv_course.setText(course);
        tv_credit_value.setText(credit_value);
        tv_grade_value.setText(grade_value);

        return builder.create();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
