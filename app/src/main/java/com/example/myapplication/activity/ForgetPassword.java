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


import org.json.JSONObject;


import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {
    @BindView(R.id.input_email) EditText input_email;
    @BindView(R.id.btn_send) Button btn_send;

    Context mContext;
    BaseApiService mApiService;
    Validate mValidate;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        mValidate = new Validate();
        mProgressDialog = new ProgressDialog();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mValidate.validate(input_email))
                {
                    requestForgetPassword();
                }
            }
        });
    }


    private void requestForgetPassword(){
//        mProgressDialog.setProgressDialog(mContext);
        mApiService.requestForgotPasswordRequest(input_email.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200)
                        {
                            Toast.makeText(mContext, "Sent an OTP code to" + input_email.getText().toString(), Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(mContext, LastForgetPassword.class);
                            myIntent.putExtra(Constants.KEY_EMAIL, input_email.getText().toString());
                            startActivity(myIntent);
                        }
                        else {
                            try {
                                JSONObject jsonError = new JSONObject(response.errorBody().string());
                                Log.e("debug", "onFailure: ERROR 600 > " + jsonError.getJSONObject("error").getString("message") );
                                Toast.makeText(mContext, jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }

}
