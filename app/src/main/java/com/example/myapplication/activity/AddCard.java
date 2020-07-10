package com.example.myapplication.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCard extends AppCompatActivity {
    @BindView(R.id.card_multiline_widget) CardMultilineWidget mCardMultilineWidget;
    @BindView(R.id.btn_saveCard) Button btn_saveCard;
    private Stripe stripe;
    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        btn_saveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCard();
            }
        });
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