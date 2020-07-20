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
import com.example.myapplication.model.BaseResult;
import com.example.myapplication.model.ResultResponse;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.Validate;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;
//import com.github.glomadrian.codeinputlib.CodeInput;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCode extends AppCompatActivity {
//    @BindView(R.id.input_code) CodeInput code;
    @BindView(R.id.input_code) EditText code;
    @BindView(R.id.txt_email) TextView email;
    @BindView(R.id.btn_verify) Button btn_verify;

//    variable mEmail store email address from register
    String mEmail;

    Context mContext;
    BaseApiService mApiService;
    Validate mValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        mValidate = new Validate();

        Intent intent = getIntent();
        mEmail = intent.getStringExtra(Constants.KEY_EMAIL);

//        show email address registered
        email.setText(mEmail);

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(mValidate.validateVerifyCode(code, mContext))
//                {
//                    verify();
//                }
            }
        });
    }
    private void verify(){
//        Character[] codeArr = code.getCode();
//        String strOfInts = Arrays.toString(codeArr).replaceAll("\\[|\\]|,|\\s", "");
//        Log.e("test","value of code is: " + strOfInts);
        mApiService.getverifyToken(code.getText().toString()).enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(mContext, "Verified successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(mContext, MainActivity.class));
                }
                else
                {
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
            public void onFailure(Call<BaseResult> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });

    }
}
