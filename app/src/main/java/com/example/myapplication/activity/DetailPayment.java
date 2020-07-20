package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.DetailPayment.Example;
import com.example.myapplication.model.DetailPayment.Result;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPayment extends AppCompatActivity {
    Context mContext;
    String idPayment, idUser, sessionName, eventName, receiverName, senderName;
    Date sessionDay, createAt, updateAt;
    BaseApiService mApiService;
    @BindView(R.id.timePaymentDetail) TextView timePaymentDetail;
    @BindView(R.id.contentPaymentDetail) TextView contentPaymentDetail;
    @BindView(R.id.txt_amountPaymentDetail) TextView txt_amountPaymentDetail;
    @BindView(R.id.txt_statusPaymentDetail) TextView txt_statusPaymentDetail;
    @BindView(R.id.txt_sessionDayPaymentDetail) TextView txt_sessionDayPaymentDetail;

    @BindView(R.id.img_bannerPayment) ImageView img_bannerPayment;
    @BindView(R.id.img_typePayment) CircleImageView img_typePayment;

    @BindView(R.id.itemRefund) LinearLayout itemRefund;

    @BindView(R.id.timeRefundDetail) TextView timeRefundDetail;
    @BindView(R.id.contentRefundDetail) TextView contentRefundDetail;
    @BindView(R.id.txt_amountRefundDetail) TextView txt_amountRefundDetail;
    @BindView(R.id.txt_statusRefundDetail) TextView txt_statusRefundDetail;
    @BindView(R.id.txt_sessionDayRefundDetail) TextView txt_sessionDayRefundDetail;

    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_payment);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);

        toolbar_title.setText("Detail payment");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent myIntent = getIntent();
        idPayment = myIntent.getStringExtra(Constants.KEY_PAYMENTID);
        idUser = myIntent.getStringExtra(Constants.KEY_USERID);
        getDetailPayment();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getDetailPayment(){
        mApiService.get_payment_info(idPayment).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code()==200)
                {
                    Result detailPayment = response.body().getResult();
                    txt_amountPaymentDetail.setText(detailPayment.getAmount().toString()+ " VND");
                    txt_statusPaymentDetail.setText(detailPayment.getStatus());

                    String urlBanner = detailPayment.getEventId().getBannerUrl();
                    String sessionId = detailPayment.getSession().get(0);
                    if (!urlBanner.equals("")){
                    Picasso.get().load(urlBanner).into(img_bannerPayment);
                }
                    if(detailPayment.getPayType().equals("ZALOPAY")){
                        Picasso.get().load("https://i.ibb.co/jrBG8yw/zalo-pay.png").into(img_typePayment);
                    }

                    Log.e("sessionId:",detailPayment.getSession().get(0));
                    for(int i=0; i<detailPayment.getEventId().getSession().size();i++){
                        if(sessionId.equals(detailPayment.getEventId().getSession().get(i).getIdSession())){
                            sessionName = detailPayment.getEventId().getSession().get(i).getName();
                            Log.e("sessionName:",detailPayment.getEventId().getSession().get(i).getName());
                            sessionDay  = (Date) detailPayment.getEventId().getSession().get(i).getDay();
                        }
                    }
                    createAt = detailPayment.getCreatedAt();
                    updateAt = detailPayment.getUpdatedAt();
                    eventName = detailPayment.getEventId().getName();
                    senderName = detailPayment.getSender().getFullName();
                    receiverName = detailPayment.getReceiver().getFullName();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    txt_sessionDayPaymentDetail.setText(dateFormat.format(sessionDay));

                    SimpleDateFormat datetimeFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                    timePaymentDetail.setText(datetimeFormat.format(createAt));

//                    content
                    if (detailPayment.getSender().getId().equals(idUser)){
                        contentPaymentDetail.setText("You " + "paid for " + receiverName +" in session " + sessionName + " of event " + eventName);
                    }
                    else {
                        contentPaymentDetail.setText(senderName + " sent for you in session " + sessionName + " of event " + eventName);
                    }
                    if (detailPayment.getSessionRefunded().size()>0){
                        itemRefund.setVisibility(View.VISIBLE);
                        timeRefundDetail.setText(datetimeFormat.format(updateAt));
                        txt_amountRefundDetail.setText(detailPayment.getAmount().toString() + " VND");
                        txt_statusRefundDetail.setText(detailPayment.getStatus());
                        txt_sessionDayRefundDetail.setText(dateFormat.format(sessionDay));
                        if (detailPayment.getSender().getId().equals(idUser)){
                            contentRefundDetail.setText(receiverName + " refunded for you" + " in session " + sessionName + " of event " + eventName);
                        }
                        else {
                            contentRefundDetail.setText("You refunded for " + senderName + " in session " + sessionName + " of event " + eventName);
                        }
                        }
                }
                else
                {
                    Log.e("error phi:","is not 200");
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
}
