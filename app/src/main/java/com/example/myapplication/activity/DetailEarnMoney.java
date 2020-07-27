package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DetailEarnAdapter;
import com.example.myapplication.model.EarnedMoney.AmountSession;
import com.example.myapplication.model.EarnedMoney.Example;
import com.example.myapplication.model.EarnedMoney.Result;
import com.example.myapplication.model.ListEvent.Session;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.RecyclerItemClickListener;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEarnMoney extends AppCompatActivity {
    @BindView(R.id.rvSessionMoney) RecyclerView rvSessionMoney;
    @BindView(R.id.txt_amountRevenue) TextView txt_amountRevenue;
//    @BindView(R.id.toolbar_back) TextView toolbar_back;
//    @BindView(R.id.toolbar_title) TextView toolbar_title;
    @BindView(R.id.startDateDetail) EditText startDay;
    @BindView(R.id.endDateDetail) EditText endDay;

    Calendar myCalendar, endCalendar;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    Context mContext;
    BaseApiService mApiService;
    String eventId;
    int amount = 0;
    String start,end;
    Date startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_earn_money);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);

        myCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        Intent myIntent = getIntent();
        eventId = myIntent.getStringExtra(Constants.KEY_EARNMONEYID);
        start = myIntent.getStringExtra(Constants.KEY_STARTDATE);
        end = myIntent.getStringExtra(Constants.KEY_ENDDATE);
        if(start==null || end==null)
        {
            endDate = new Date();
            endCalendar.add(Calendar.MONTH, -1);
            startDate= endCalendar.getTime();
        }
        else
        {
            try {
                startDate = formatter.parse(start);
                endDate = formatter.parse(end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        startDay.setText(formatter.format(startDate));
        endDay.setText(formatter.format(endDate));

//        toolbar_title.setText("Earned Money");
//        toolbar_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        DatePickerDialog.OnDateSetListener enddate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, monthOfYear);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };
        startDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(mContext, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext, enddate, endCalendar
                        .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
                if(start!=null && end!=null){
                    getDetailEarnMoney(startDate, endDate);
                }
            }

        });

        rvSessionMoney.setLayoutManager(new LinearLayoutManager(this));
        rvSessionMoney.setItemAnimator(new DefaultItemAnimator());
        getDetailEarnMoney(startDate, endDate);
    }
    private void getDetailEarnMoney(Date start, Date end){
        mApiService.getReportRevenus(start, end, eventId).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                List<Result> earnByEvent = response.body().getResult();
                List<Session> listSession = earnByEvent.get(0).getSessionId();
                List<AmountSession> amountSessions = earnByEvent.get(0).getAmountSession();
                for (int i=0; i<amountSessions.size();i++)
                {
                    amount +=amountSessions.get(i).getTotal();
                }
                txt_amountRevenue.setText("0 VND");
                txt_amountRevenue.setText(Integer.toString(amount));
                rvSessionMoney.setAdapter(new DetailEarnAdapter(mContext, listSession, amountSessions));
                rvSessionMoney.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String sessionId = listSession.get(position).getIdSession();
                        Intent listPayment = new Intent(mContext, ListPaymentSession.class);
                        listPayment.putExtra(Constants.KEY_EVENTID, eventId);
                        listPayment.putExtra(Constants.KEY_SESSIONID, sessionId);
                        listPayment.putExtra(Constants.KEY_SESSIONNAME, listSession.get(position).getName());
                        startActivity(listPayment);
                    }
                }));
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
    private void updateLabel() {

        startDay.setText(formatter.format(myCalendar.getTime()));
        startDate = myCalendar.getTime();
    }
    private void updateLabel1(){
        endDay.setText(formatter.format(endCalendar.getTime()));
        endDate = endCalendar.getTime();
    }
}
