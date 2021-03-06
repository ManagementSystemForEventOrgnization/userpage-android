package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.ProgressDialog;
import com.example.myapplication.util.Validate;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;
//import com.github.glomadrian.codeinputlib.CodeInput;


import org.json.JSONObject;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LastForgetPassword extends AppCompatActivity {
//    @BindView(R.id.input_code) CodeInput code;
    @BindView(R.id.input_code) EditText code;
    @BindView(R.id.input_password) EditText password;
    @BindView(R.id.input_confirmPassword) EditText confirmPassword;
    @BindView(R.id.btn_save) Button save;

    String mEmail;

    Context mContext;
    BaseApiService mApiService;
    Validate mValidate;
    ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_forget_password);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        mValidate = new Validate();
        mProgressDialog = new ProgressDialog();

        Intent intent = getIntent();
        mEmail = intent.getStringExtra(Constants.KEY_EMAIL);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mValidate.validate(password) && mValidate.validate(confirmPassword)
                    && mValidate.isConfirmed(password, confirmPassword))
                {
                    createNewPassword();
                }
            }
        });
    }
    private void createNewPassword(){
//        Character[] codeArr = code.getCode();
//        String strOfInts = Arrays.toString(codeArr).replaceAll("\\[|\\]|,|\\s", "");
//
//        Log.e("test","value of code is: " + strOfInts);
//        mProgressDialog.setProgressDialog(mContext);
        mApiService.forgotPasswordRequest(mEmail, code.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("test","value of status code isss: " + response.code() );
                        if(response.code()==200)
                        {
                            Toast.makeText(mContext, "Created new password successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, Login.class));
                        }
                        else {
                            try {
                                JSONObject jsonError = new JSONObject(response.errorBody().string());
                                Log.e("debug", "onFailure: ERROR 600 > " + jsonError.getJSONObject("error").getString("message") );
                                Toast.makeText(mContext, jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                            }                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }
}
