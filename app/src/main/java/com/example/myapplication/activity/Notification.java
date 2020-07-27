package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NotificationAdapter;
import com.example.myapplication.model.Notification.Example;
import com.example.myapplication.model.Notification.Result;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.RecyclerItemTouchHelper;
import com.example.myapplication.util.SwipeHelper;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity  {
    @BindView(R.id.rvListNotification) RecyclerView rvListNotification;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;

    Context mContext;
    BaseApiService mApiService;
    List<Result>  notificationList;
    int pageNumber = 1;
    NotificationAdapter mAdapter;

    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_notification);
        ButterKnife.bind(this);

        toolbar_title.setText("Notification");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, HomeActivity.class));
            }
        });

        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        notificationList = new ArrayList<>();
        mAdapter = new NotificationAdapter(mContext, notificationList, new NotificationAdapter.MyAdapterListener() {
            @Override
            public void onclickItemNotification(View v, int position) {
                if (notificationList.get(position).getLinkTo().getKey().equals("EventDetail")){
                    Intent detailEvent = new Intent(mContext, DetailEvent.class);
                    detailEvent.putExtra(Constants.KEY_ID, notificationList.get(position).getLinkTo().getId());
                    startActivity(detailEvent);
                }
                if (notificationList.get(position).getLinkTo().getKey().equals("PaymentInfo")){
                    Intent detailPayment = new Intent(mContext, DetailPayment.class);
                    detailPayment.putExtra(Constants.KEY_PAYMENTID, notificationList.get(position).getLinkTo().getId());
                    startActivity(detailPayment);
                }
                mApiService.setReadNotification(notificationList.get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
            }
        });

        rvListNotification.setLayoutManager(new LinearLayoutManager(this));
        rvListNotification.setItemAnimator(new DefaultItemAnimator());
        rvListNotification.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvListNotification.setAdapter(mAdapter);
        getNotification(pageNumber);
        initScrollListener();
        SwipeHelper swipeHelper = new SwipeHelper(this, rvListNotification) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                String notificationId = notificationList.get(viewHolder.getAdapterPosition()).getId();
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                mApiService.setDeleteNotification(notificationId).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if(response.isSuccessful())
                                        {
                                            mAdapter.removeItem(viewHolder.getAdapterPosition());
                                            Toast.makeText(mContext, "Deleted successfully!!", Toast.LENGTH_SHORT).show();
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
                        }
                ));

                if (!notificationList.get(viewHolder.getAdapterPosition()).getIsRead()){
                    underlayButtons.add(new SwipeHelper.UnderlayButton(
                            "Mark read",
                            0,
                            Color.parseColor("#FF9502"),
                            new SwipeHelper.UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    mApiService.setReadNotification(notificationId).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            ((NotificationAdapter.NotificationHolder) viewHolder).viewForeground.setBackgroundColor(Color.parseColor("#111111"));
                                            Toast.makeText(mContext, "Marked successfully!!", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                    ));
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(mContext, HomeActivity.class));
        finish();
    }

    private void getNotification( int pageNum){
        mApiService.getListNotification(pageNum).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.isSuccessful())
                {
//                    notificationList = response.body().getResult();
                    List<Result> notificationItems = response.body().getResult();
                    notificationList.clear();
                    for(int i=0;i<notificationItems.size();i++){
                        notificationList.add(notificationItems.get(i));
                    }


//                    rvListNotification.setAdapter(new NotificationAdapter(mContext, notificationList));
                    mAdapter.notifyDataSetChanged();

                }
                else{
                    Toast.makeText(mContext, "Failed to load notifications", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
    private void initScrollListener() {
        rvListNotification.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == 10) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }
    private void loadMore(){
        notificationList.add(null);
        mAdapter.notifyItemInserted(notificationList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notificationList.remove(notificationList.size() - 1);
                int scrollPosition = notificationList.size();
                mAdapter.notifyItemRemoved(scrollPosition);
                pageNumber +=1;

                getNotification(pageNumber);
//                int currentSize = scrollPosition;
//                int nextLimit = currentSize + 10;
//
//                while (currentSize - 1 < nextLimit) {
//                    rowsArrayList.add("Item " + currentSize);
//                    currentSize++;
//                }

                mAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);

    }
}
