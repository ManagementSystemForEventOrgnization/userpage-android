package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ApplyUserAdapter;
import com.example.myapplication.model.UserJoinEvent.Example;
import com.example.myapplication.model.UserJoinEvent.Result;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.SharedPrefManager;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListApplyUser extends AppCompatActivity {
    @BindView(R.id.rvListApplyUser) RecyclerView rvListApplyUser;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;
    String eventId, sessionId;
//            String typeTab;
    String organizerId, userId;
    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_apply_user);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        sharedPrefManager= new SharedPrefManager(this);
        userId = sharedPrefManager.getSpIduser();

        toolbar_title.setText("List apply user");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent myIntent = getIntent();
        eventId = myIntent.getStringExtra(Constants.KEY_EVENTID);
        sessionId = myIntent.getStringExtra(Constants.KEY_SESSIONID);
        organizerId = myIntent.getStringExtra(Constants.KEY_ORGANIZERID);

//        typeTab = myIntent.getStringExtra(Constants.KEY_STATUS);
        rvListApplyUser.setLayoutManager(new LinearLayoutManager(this));
        rvListApplyUser.setItemAnimator(new DefaultItemAnimator());

        getListApplyUser();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getListApplyUser(){
        mApiService.get_user_join_event(eventId, sessionId, 1).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()){
                    List<Result> listApplyUser = response.body().getResult();
                    rvListApplyUser.setAdapter(new ApplyUserAdapter(mContext, organizerId, userId, listApplyUser, new ApplyUserAdapter.ApplyAdapteListenner() {

                        @Override
                        public void rejectButtonOnClick(View v, int position) {
                            rejectApplyUser(listApplyUser.get(position).getId(),eventId, sessionId);
                        }

                        @Override
                        public void getProfileUser(View v, int position) {
                            Intent intent = new Intent(mContext, ProfileUser.class);
                            intent.putExtra(Constants.KEY_USERID, listApplyUser.get(position).getId());
                            startActivity(intent);
                        }

                    }));
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
    private void rejectApplyUser(String joinUserId, String eventId, String sessionId){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Reject joined user");
        builder.setMessage("Are you sure to reject this user?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        mApiService.rejectEventMenber(joinUserId,eventId, sessionId).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(mContext, "Rejected successfully", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    if (response.code()==600){
                                        try {
                                            JSONObject jsonError = new JSONObject(response.errorBody().string());
                                            Toast.makeText(mContext, jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                        dialog.dismiss();
                    }
                }
        );

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
