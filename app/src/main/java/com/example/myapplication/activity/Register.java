package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.example.myapplication.util.SharedPrefManager;
import com.example.myapplication.util.Validate;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    @BindView(R.id.input_email)EditText input_email;
    @BindView(R.id.input_fullname) EditText input_fullName;
    @BindView(R.id.input_password) EditText input_password;
    @BindView(R.id.input_confirmPassword) EditText input_confirmPassword;
    @BindView(R.id.btn_register) Button btn_register;

    Context mContext;
    BaseApiService mApiService;
    Validate mValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        mValidate = new Validate();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mValidate.validate(input_email) && mValidate.validate(input_password)
                && mValidate.validate(input_confirmPassword) && mValidate.validate(input_fullName)
                && mValidate.isConfirmed(input_password,input_confirmPassword))
                {
                    register();
                }
            }
        });
    }

    private void register(){
        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
        mApiService.registerRequest(input_email.getText().toString(), input_fullName.getText().toString(),
                input_password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: success");
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                Toast.makeText(mContext, "An OTP code sent to" + input_email.getText().toString() , Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(mContext, VerifyCode.class);
                                myIntent.putExtra(Constants.KEY_EMAIL, input_email.getText().toString());
                                startActivity(myIntent);

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            progressDialog.dismiss();
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
                        progressDialog.dismiss();
                    }
                });

    }

}
