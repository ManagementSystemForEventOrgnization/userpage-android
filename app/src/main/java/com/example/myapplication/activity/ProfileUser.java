package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.BaseUser;
import com.example.myapplication.model.ListEvent.User;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUser extends AppCompatActivity {

    @BindView(R.id.txt_fullName)
    TextView fullname;
    @BindView(R.id.txt_email) TextView email;
    @BindView(R.id.txt_numberPhone) TextView numberphone;
    @BindView(R.id.profile_image) CircleImageView image;
    @BindView(R.id.itemBirthday) LinearLayout itemBirthday;
    @BindView(R.id.itemDescription) LinearLayout itemDescription;
    @BindView(R.id.itemJob) LinearLayout itemJob;
    @BindView(R.id.itemGender) LinearLayout itemGender;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;

    String myUserId, myFullname;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);

        Intent myIntent = getIntent();
        myUserId = myIntent.getStringExtra(Constants.KEY_USERID);

        getProfile();

        toolbar_title.setText(myFullname);
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
    private void getProfile(){
        mApiService.get_profile_user(myUserId).enqueue(new Callback<BaseUser>() {
            @Override
            public void onResponse(Call<BaseUser> call, Response<BaseUser> response) {
                if (response.code()==200)
                {
                    User userInfo = response.body().getResult();
                    myFullname = userInfo.getFullName();
                    fullname.setText(userInfo.getFullName());
                    email.setText(userInfo.getEmail());
                    itemBirthday.setVisibility(View.GONE);
                    itemDescription.setVisibility(View.GONE);
                    itemGender.setVisibility(View.GONE);
                    itemJob.setVisibility(View.GONE);
                    if(userInfo.getPhone()!=null){
                        numberphone.setText(userInfo.getPhone());
                    }
                    if (userInfo.getAvatar()!=null){
                        Picasso.get().load(userInfo.getAvatar()).into(image);
                    }
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
            public void onFailure(Call<BaseUser> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());

            }
        });
    }
}
