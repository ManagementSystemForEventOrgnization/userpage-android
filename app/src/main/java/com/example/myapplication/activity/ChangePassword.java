package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.util.Validate;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import org.json.JSONObject;


import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    @BindView(R.id.input_currentPassword) EditText oldpassword;
    @BindView(R.id.input_newPassword) EditText newpassword;
    @BindView(R.id.input_confirmPassword) EditText confirmpassword;
    @BindView(R.id.btn_changePassword) Button changepassword;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;

    Context mContext;
    BaseApiService mApiService;
    Validate mValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        mValidate = new Validate();

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mValidate.validate(oldpassword)&& mValidate.validate(newpassword) && mValidate.validate(confirmpassword)
                && mValidate.isConfirmed(newpassword, confirmpassword)){
                    changePassword();
                }
            }
        });
        toolbar_title.setText("Change password");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void changePassword(){
        mApiService.updatePasswordRequest(oldpassword.getText().toString(), newpassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(mContext, "Changed successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, HomeActivity.class));
                        } else {
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
