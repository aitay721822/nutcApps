package com.nutcunofficial.nutcapps.Home.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.nutcunofficial.nutcapps.util.EditTextUtil;
import com.nutcunofficial.nutcapps.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ForgotDialog extends AppCompatDialogFragment {

    private onForgotListener listener;
    private Button send;
    private EditText schoolNumber;
    private EditText idnumber;
    private Spinner QuestionSelector;
    private EditText QuestionAnswer;

    public interface onForgotListener{
        void doForgotTask(String schoolNumber,String idnumber,String QuestionHash,String QuestionAnswer);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.forgot_dialog, null);

        builder.setView(layout);

        /* QueryDropDownList */
        final HashMap<String,String> QueryValue = new HashMap<>();
        QueryValue.put("您最喜歡的食物","f875964c-091b-4e8a-8e8a-18520357c725");
        QueryValue.put("您最喜歡的顏色","6170037c-b4d7-4518-b132-2442796a3fa8");
        QueryValue.put("您最喜歡旅遊地點","946f1b18-7175-442a-bcdd-337de40a2554");
        QueryValue.put("What is your favorite food? (3-20 characters)","d04a9a76-9651-4d01-9123-4f9b6dd9cd78");
        QueryValue.put("您最好朋友的生日","c757b49d-ea44-4fad-b871-5856b9f2b365");
        QueryValue.put("What is the name of your first elementary/primary school? (3-20 characters)","2f92c550-4a0f-4412-98f4-69eec8b29e5c");
        QueryValue.put("What was the name of your first pet? (3-20 characters)","3a974230-be0a-4a48-a902-939871bd0751");
        QueryValue.put("您最愛的演藝人員","ea1f88b8-e2f2-4ad5-8bb1-dd623680ffc0");
        /* QueryDropDownList */

        send = (Button)layout.findViewById(R.id.sendBtn);
        schoolNumber = (EditText)layout.findViewById(R.id.dialogAccount);
        idnumber = (EditText)layout.findViewById(R.id.idnumber);
        QuestionSelector = ((Spinner)layout.findViewById(R.id.questionSpinner));
        QuestionAnswer = (EditText)layout.findViewById(R.id.answer);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<EditText> text = new ArrayList<>();
                text.add(schoolNumber);
                text.add(idnumber);
                text.add(QuestionAnswer);

                EditTextUtil editTextUtil = new EditTextUtil(text).filter(EditTextUtil.CHECK_IF_NON_EMPTY);

                // Validation
                if(editTextUtil.getResult()) listener.doForgotTask(
                            schoolNumber.getText().toString(),
                            idnumber.getText().toString().toUpperCase(),
                            QueryValue.get(QuestionSelector.getSelectedItem().toString()),
                            QuestionAnswer.getText().toString()
                    );

            }
        });

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (onForgotListener)context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "MUST IMPLEMENT LISTENER");
        }
    }

}
