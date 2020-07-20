package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Chat extends AppCompatActivity {
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;
    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);

        toolbar_title.setText("Chat with admin");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }
}
