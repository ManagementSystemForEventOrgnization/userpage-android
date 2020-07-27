package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.PaymentAdapter;
import com.example.myapplication.model.PaymentHistory.Example;
import com.example.myapplication.model.PaymentHistory.Result;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.RecyclerItemClickListener;
import com.example.myapplication.util.SharedPrefManager;
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

public class Payment extends AppCompatActivity {
    @BindView(R.id.rvPayments) RecyclerView rvPayments;
    @BindView(R.id.txt_amountExpenditure) TextView txt_amountExpenditure;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;

    String myUserId;
//    chi phi (red)
    int totalExpenditure = 0;
    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        sharedPrefManager = new SharedPrefManager(this);

        toolbar_title.setText("Payment");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rvPayments.setLayoutManager(new LinearLayoutManager(this));
        rvPayments.setItemAnimator(new DefaultItemAnimator());
        getTotalPayment();
        getPayments();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void getTotalPayment(){
        mApiService.get_payment_history_total().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        totalExpenditure = jsonRESULTS.getJSONObject("result").getInt("expTotal");
                        txt_amountExpenditure.setText(totalExpenditure + " VND");

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
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
    private void getPayments() {
        mApiService.getPaymentHistory().enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.isSuccessful())
                {
                    List<Result> paymentItems = response.body().getResult();
                    myUserId = sharedPrefManager.getSpIduser();
                    rvPayments.setAdapter(new PaymentAdapter(mContext, paymentItems,myUserId));

                    rvPayments.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Result items = paymentItems.get(position);
                            String id = items.getId();
                            Intent detailPayment = new Intent(mContext, DetailPayment.class);
                            detailPayment.putExtra(Constants.KEY_PAYMENTID,id);
//                            detailPayment.putExtra(Constants.KEY_USERID, myUserId);
                            startActivity(detailPayment);
                        }
                    }));
                }
                else{
                    Toast.makeText(mContext, "Failed to load payments", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
}
