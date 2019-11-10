package com.nutcunofficial.nutcapps.util;

import android.widget.EditText;

import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.nutcApplication;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTextUtil {

    public static final int CHECK_IF_NON_EMPTY = 1;

    public static final int CHECK_IF_NOT_EQUAL = 2;

    public static final int CHECK_IF_EQUAL = 3;

    public static final int CHECK_PASSWORD_VALID = 4;

    private List<EditText> list;

    private boolean validation = true;

    public EditTextUtil(List<EditText> editText){
        this.list = editText;
    }

    public List<EditText> getList(){
        return this.list;
    }

    public boolean getResult() {
        return validation;
    }

    public EditTextUtil filter(int CheckStatus){
        switch(CheckStatus){
            case CHECK_IF_NON_EMPTY :
                checkIfNonEmpty();
                break;
            case CHECK_IF_EQUAL:
                checkEditTextIsEqual();
                break;
            case CHECK_IF_NOT_EQUAL:
                checkIfNonEmpty();
                break;
            case CHECK_PASSWORD_VALID:
                checkPasswordIsValid();
                break;
        }
        return this;
    }


    private void checkIfNonEmpty(){
        List<EditText> edit = getList();
        if(edit==null || edit.size() == 0 || !validation) { validation = false; return;}
        for(EditText text : edit){
            if(text.getText().toString().equals("")) {
                text.setError(nutcApplication.ResourcesString(R.string.empty));
                validation = false;
            }
            else {
                validation = true;
            }
        }
    }

    private void checkEditTextIsEqual(){
        List<EditText> edit = getList();
        if(edit==null || edit.size() == 0 || !validation) { validation = false; return;}

        String preference = edit.get(0).getText().toString();
        for(int i = 1 ; i < edit.size() ; i++){
            String compare = edit.get(i).getText().toString();
            if(!preference.equals(compare)){
                validation=false;
                break;
            }
        }

        if(!validation) {
            for (EditText text : edit)
                text.setError(nutcApplication.ResourcesString(R.string.IS_NOT_EQUAL));
        }
    }

    private void checkEditTextIsNotEqual(){
        List<EditText> edit = getList();
        if(edit==null || edit.size() == 0 || !validation) { validation = false; return;}

        String preference = edit.get(0).getText().toString();
        for(int i = 1 ; i < edit.size() ; i++){
            String compare = edit.get(i).getText().toString();
            if(preference.equals(compare)){
                validation=false;
                break;
            }
        }

        if(!validation) {
            for (EditText text : edit)
                text.setError(nutcApplication.ResourcesString(R.string.IS_CANNOT_EQUAL));
        }
    }

    private void checkPasswordIsValid(){
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z]).{6,30}");

        List<EditText> edit = getList();
        if(edit==null || edit.size() == 0 || !validation) { validation = false; return;}

        for(int i = 1 ; i < edit.size() ; i++) {
            String password = edit.get(i).getText().toString();
            Matcher matcher = pattern.matcher(password);
            if(matcher==null || !matcher.find()){
                validation=false;
            }
        }

        if(!validation) {
            for (EditText text : edit)
                text.setError(nutcApplication.ResourcesString(R.string.PASSWORD_NOT_VALID));
        }
    }

}
