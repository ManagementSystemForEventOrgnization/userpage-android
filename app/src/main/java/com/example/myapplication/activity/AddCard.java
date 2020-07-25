package com.example.myapplication.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;
import com.stripe.android.ApiResultCallback;

import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCard extends AppCompatActivity {
    @BindView(R.id.card_multiline_widget) CardMultilineWidget mCardMultilineWidget;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;
    @BindView(R.id.toolbar_right) TextView btn_saveCard;

    private Stripe stripe;
    Context mContext;
    BaseApiService mApiService;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);

        toolbar_title.setText("Add card");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                startActivity(new Intent(mContext, ListCard.class));
            }
        });
        btn_saveCard.setText("Save");
        btn_saveCard.setVisibility(View.VISIBLE);
        btn_saveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCard();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void saveCard(){
        Card cardToSave = mCardMultilineWidget.getCard();
        Log.e("test","cardToSave" + cardToSave);
        if (cardToSave == null) {
            Log.e("test","card to save null");
            return;
        }

        stripe = new Stripe(mContext, "pk_test_pahNUQKG2WVBshWCDSeY5opJ00IFrwZjO3");

        stripe.createCardToken(cardToSave, new ApiResultCallback<Token>() {
            @Override
            public void onSuccess(Token token) {
                Log.e("test","token object:"+ token);
                mApiService.addCard(token.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Added card successfully", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if (response.code()==600) {
                                JSONObject jsonError = null;
                                try {
                                    jsonError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(mContext, jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
            @Override
            public void onError(@NotNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
