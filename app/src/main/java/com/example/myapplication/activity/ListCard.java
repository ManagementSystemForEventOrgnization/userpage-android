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
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CardAdapter;
import com.example.myapplication.model.ApplyEvent;
import com.example.myapplication.model.ListCard.Example;
import com.example.myapplication.model.ListCard.Result;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;
import com.stripe.android.model.Card;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCard extends AppCompatActivity {
    String eventId, sessionId, payType;
    @BindView(R.id.btn_addCard) TextView btn_addCard;
    @BindView(R.id.rvCards) RecyclerView rvCards;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);


        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);

        toolbar_title.setText("List card");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent myIntent = getIntent();
       eventId = myIntent.getStringExtra(Constants.KEY_EVENTID);
       sessionId = myIntent.getStringExtra(Constants.KEY_SESSIONID);
       payType = myIntent.getStringExtra(Constants.KEY_TYPEPAY);

        rvCards.setLayoutManager(new LinearLayoutManager(this));
        rvCards.setItemAnimator(new DefaultItemAnimator());
        getListCard();
        btn_addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AddCard.class));
            }
        });
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
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        getListCard();
        //Refresh your stuff here
    }
    private void getListCard(){
        mApiService.getListCard().enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.isSuccessful())
                {
                    List<Result> cardList = response.body().getResult();
                    rvCards.setAdapter(new CardAdapter(mContext, cardList, new CardAdapter.MyAdapterListener() {
                        @Override
                        public void iconImageButtonOnClick(View v, int position) {
                            deleteCard(cardList.get(position).getId());
                        }
                        @Override
                        public void clickYes(View v, int position){
                            setCardDefault(cardList.get(position).getId());
                        }
                        @Override
                        public void clickNo(View v, int position){

                        }
                    }));
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
    private void deleteCard(String CardId){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Delete card");
        builder.setMessage("Are you sure to delete this card?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                mApiService.delCard(CardId).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                        {
                            getListCard();
                            Toast.makeText(mContext, "Deleted successfully", Toast.LENGTH_LONG).show();
                        }
                        else {
                            try {
                                JSONObject jsonError = new JSONObject(response.errorBody().string());
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
    private void setCardDefault(String CardId){
        mApiService.setCardDefault(CardId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
//                    joinEvent(eventId, arrSessionIds, payType);
                    List<String> arrSessionIds = new ArrayList<>();
                    arrSessionIds.add(sessionId);
                    ApplyEvent applyEvent = new ApplyEvent(payType, eventId, arrSessionIds);

//                    String[] arrIdSessions = new String[1];
//                    arrIdSessions[0] = sessionId;

                    mApiService.joinEvent(applyEvent).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(mContext, "Joined event successfully", Toast.LENGTH_LONG).show();
                            }
                            else {
                                try {
                                    JSONObject jsonError = new JSONObject(response.errorBody().string());
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
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
//    private void joinEvent(String eventId, List<String> sessionId, String payType){
////        Log.v("test sessionId",sessionId);
//        mApiService.joinEvent(eventId, arrIdSessions, payType).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()){
//                    Toast.makeText(mContext, "Joined event successfully", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    try {
//                        JSONObject jsonError = new JSONObject(response.errorBody().string());
//                        Toast.makeText(mContext, jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }
}
