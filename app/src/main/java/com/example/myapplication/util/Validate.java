package com.example.myapplication.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

//import com.github.glomadrian.codeinputlib.CodeInput;

import java.util.Arrays;

public class Validate {
    public boolean validate(EditText editText){
        if(editText.getText().toString().trim().length()>0){
            return true;
        }
        editText.setError("Please fill this field");
        editText.requestFocus();
        return false;
    }
    public boolean validateEmail( EditText input_email){
        String email = input_email.getText().toString().trim();
        if (email.isEmpty() || isValidEmail(email)){
            input_email.setError("Email is not valid");
            input_email.requestFocus();
            return false;
        }
        return true;
    }
    public static boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean isConfirmed (EditText input, EditText confirm){
        if (confirm.getText().toString().equals(input.getText().toString()))
            return true;
        confirm.setError("Please input the right confirm");
        confirm.requestFocus();
        return false;
    }
//    public boolean validateVerifyCode(CodeInput code, Context mContext){
//        Character[] codeArr = code.getCode();
//        String strOfInts = Arrays.toString(codeArr).replaceAll("\\[|\\]|,|\\s", "");
//        if(strOfInts.length()==6)
//            return true;
//        Toast.makeText(mContext, "Please enter the correct number", Toast.LENGTH_SHORT).show();
//        return false;
//    }

}
