package com.example.myapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.util.SharedPrefManager;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.PointerIcon.TYPE_NULL;

public class EditProfile extends AppCompatActivity {
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;
    @BindView(R.id.toolbar_right) TextView toolbar_right;

    @BindView(R.id.input_fullname) EditText input_fullname;
    @BindView(R.id.input_phone) EditText input_phone;
    @BindView(R.id.input_email) EditText input_email;
    @BindView(R.id.input_gender) EditText input_gender;
    @BindView(R.id.input_birthday) EditText input_birthday;
    @BindView(R.id.input_description) EditText input_description;


    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        sharedPrefManager = new SharedPrefManager(this);


        toolbar_title.setText("Edit info");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                startActivity(new Intent(mContext, profile.class));
            }
        });
        toolbar_right.setText("Save");
        toolbar_right.setVisibility(View.VISIBLE);
        toolbar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
        getProfileFromSharedPreferences();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @SuppressLint("ResourceAsColor")
    private void getProfileFromSharedPreferences(){
        JSONObject objUser = sharedPrefManager.getSPObjectUser();
        try{
            input_fullname.setText(objUser.getString("fullName"));
            input_email.setText(objUser.getString("email"));
            input_email.setFocusable(false);
            input_email.setEnabled(false);
            input_email.setTextColor(R.color.shadowColor);
            input_phone.setText(objUser.getString("phone"));
            input_birthday.setText(objUser.getString("birthday"));
            if(!objUser.getString("birthday").equals("")){
                String dateInString = objUser.getString("birthday").substring(0,10);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(dateInString);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                input_birthday.setText(dateFormat.format(date));
            }
            input_description.setText(objUser.getString("discription"));
            input_gender.setText(objUser.getString("gender"));
        }
        catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }
    private void editProfile(){
        mApiService.updateInfoRequest(input_fullname.getText().toString(), input_gender.getText().toString(),
                input_phone.getText().toString(),input_description.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        sharedPrefManager.saveSPObjectUser(SharedPrefManager.SP_OBJUSER, jsonRESULTS.getJSONObject("result"));
                        Toast.makeText(mContext, "Edit successfully", Toast.LENGTH_SHORT).show();

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(mContext, "Edit successfully", Toast.LENGTH_LONG).show();
                }
                try {
                    assert response.errorBody() != null;
                    JSONObject jsonError = new JSONObject(response.errorBody().string());
                    Log.e("debug", "onFailure: ERROR 600 > " + jsonError.getJSONObject("error").getString("message") );
                    Toast.makeText(mContext, jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
