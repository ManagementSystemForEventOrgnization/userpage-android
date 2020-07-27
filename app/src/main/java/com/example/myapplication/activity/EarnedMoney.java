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
import com.example.myapplication.adapter.EarnMoneyAdapter;
import com.example.myapplication.model.EarnedMoney.Example;
import com.example.myapplication.model.EarnedMoney.Result;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.RecyclerItemClickListener;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarnedMoney extends AppCompatActivity {
    @BindView(R.id.rvEarnMoney) RecyclerView rvEarnMoney;
    @BindView(R.id.txt_amountRevenue) TextView txt_amountRevenue;
    @BindView(R.id.startDate) EditText startDate;
    @BindView(R.id.endDate) EditText endDate;

    Calendar myCalendar, endCalendar;
    Date start, end;
    String myFormat = "dd/MM/yyyy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    int amount = 0;
    Context mContext;
    BaseApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earned_money);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);

        myCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

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
        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(mContext, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext, enddate, endCalendar
                        .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
                if(start!=null && end!=null){
                    getListEarnMoney();
                }
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
        mApiService.getListRevenus(start,end).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful())
                {
                    List<Result> earnList = response.body().getResult();
                    for (int i=0; i<earnList.size();i++)
                    {
                        amount +=earnList.get(i).getTotalSession();
                    }
                    txt_amountRevenue.setText("0 VND");
                    txt_amountRevenue.setText(Integer.toString(amount) + " VND");
                    rvEarnMoney.setAdapter(new EarnMoneyAdapter(mContext, earnList));
                    rvEarnMoney.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Result items = earnList.get(position);
                            String id = items.getId();
                            Intent detailEarnMoney = new Intent(mContext, DetailEarnMoney.class);
                            detailEarnMoney.putExtra(Constants.KEY_EARNMONEYID,id);
//                            detailEarnMoney.putExtra(Constants.KEY_STARTDATE, items.getSessionId().get(0).getDay());
//                            detailEarnMoney.putExtra(Constants.KEY_ENDDATE, items.getSessionId().get(items.getSessionId().size()-1).getDay());
                            detailEarnMoney.putExtra(Constants.KEY_STARTDATE, start);
                            detailEarnMoney.putExtra(Constants.KEY_ENDDATE, end);
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
    private void updateLabel() {

        startDate.setText(sdf.format(myCalendar.getTime()));
        start = myCalendar.getTime();
    }
    private void updateLabel1(){
        endDate.setText(sdf.format(endCalendar.getTime()));
        end = endCalendar.getTime();
    }
}
