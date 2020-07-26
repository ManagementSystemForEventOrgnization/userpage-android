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
import com.example.myapplication.adapter.EarnMoneyAdapter;
import com.example.myapplication.model.EarnedMoney.Example;
import com.example.myapplication.model.EarnedMoney.Result;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.RecyclerItemClickListener;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarnedMoney extends AppCompatActivity {
    @BindView(R.id.rvEarnMoney) RecyclerView rvEarnMoney;
    @BindView(R.id.txt_amountRevenue) TextView txt_amountRevenue;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;

    Context mContext;
    BaseApiService mApiService;
    int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earned_money);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);

        toolbar_title.setText("EarnedMoney");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rvEarnMoney.setLayoutManager(new LinearLayoutManager(this));
        rvEarnMoney.setItemAnimator(new DefaultItemAnimator());
        getListEarnMoney();
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
    private void getListEarnMoney(){
        mApiService.getListRevenus().enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful())
                {
                    List<Result> earnList = response.body().getResult();
                    for (int i=0; i<earnList.size();i++)
                    {
                        amount +=earnList.get(i).getTotalSession();
                    }
                    txt_amountRevenue.setText(Integer.toString(amount) + " VND");
                    rvEarnMoney.setAdapter(new EarnMoneyAdapter(mContext, earnList));
                    rvEarnMoney.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Result items = earnList.get(position);
                            String id = items.getId();
                            Intent detailEarnMoney = new Intent(mContext, DetailEarnMoney.class);
                            detailEarnMoney.putExtra(Constants.KEY_EARNMONEYID,id);
                            startActivity(detailEarnMoney);
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
