package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CommentAdapter;
import com.example.myapplication.model.Comment.Example;
import com.example.myapplication.model.Comment.Result;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.Validate;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comments extends AppCompatActivity {
    @BindView(R.id.rvListComment) RecyclerView rvListComment;
    @BindView(R.id.input_comment) EditText input_comment;
    @BindView(R.id.btn_sendComment) ImageButton btn_sendComment;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;

    String eventName, eventId;
    Context mContext;
    BaseApiService mApiService;
    Validate mValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);


        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        mValidate = new Validate();

        Intent chatIntent = getIntent();
        eventName = chatIntent.getStringExtra(Constants.KEY_EVENTNAME);
        eventId = chatIntent.getStringExtra(Constants.KEY_EVENTID);

        toolbar_title.setText(eventName);
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rvListComment.setLayoutManager(new LinearLayoutManager(this));
        rvListComment.setItemAnimator(new DefaultItemAnimator());
        getComments();
        btn_sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mValidate.validate(input_comment))
                {
                    postComment();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void getComments(){
        mApiService.get_list_comment(eventId).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.isSuccessful())
                {
                    List<Result> comments = response.body().getResult();
                    rvListComment.setAdapter(new CommentAdapter(mContext, comments));
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
    private void postComment(){
        mApiService.saveComment(eventId,input_comment.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        input_comment.setText("");

//                        Toast.makeText(mContext, "Commented Successfully", Toast.LENGTH_SHORT).show();
                        getComments();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

            }
        });
    }
}
