package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ListPaymentSessionAdapter;
import com.example.myapplication.model.ListPaymentSession.Example;
import com.example.myapplication.model.ListPaymentSession.Result;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPaymentSession extends AppCompatActivity {
    Context mContext;
    BaseApiService mApiService;
    String eventId, sessionId, sessionName;
    int pagenumber =1;
    @BindView(R.id.rvListPaymentSession) RecyclerView rvListPaymentSession;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_payment_session);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);

        Intent myIntent = getIntent();
        eventId = myIntent.getStringExtra(Constants.KEY_EVENTID);
        sessionId = myIntent.getStringExtra(Constants.KEY_SESSIONID);
        sessionName = myIntent.getStringExtra(Constants.KEY_SESSIONNAME);

        toolbar_title.setText(sessionName);
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rvListPaymentSession.setLayoutManager(new LinearLayoutManager(this));
        rvListPaymentSession.setItemAnimator(new DefaultItemAnimator());
        getListPayment(pagenumber);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void getListPayment(int pageNum){
        mApiService.getListPaymentSession(eventId, sessionId, pageNum).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful())
                {
                    List<Result> listPayment = response.body().getResult();
                    rvListPaymentSession.setAdapter(new ListPaymentSessionAdapter(mContext, listPayment, new ListPaymentSessionAdapter.PaymentAdapteListenner() {
                        @Override
                        public void getProfileUser(View v, int position) {
                            Intent intent = new Intent(mContext, ProfileUser.class);
                            intent.putExtra(Constants.KEY_USERID, listPayment.get(position).getSender().getId());
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
}
