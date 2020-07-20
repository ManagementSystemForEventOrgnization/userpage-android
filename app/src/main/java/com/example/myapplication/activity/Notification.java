package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NotificationAdapter;
import com.example.myapplication.model.Notification.Example;
import com.example.myapplication.model.Notification.Result;
import com.example.myapplication.util.RecyclerItemTouchHelper;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    @BindView(R.id.rvListNotification) RecyclerView rvListNotification;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;
    Context mContext;
    BaseApiService mApiService;
    List<Result>  notificationList;
    NotificationAdapter mAdapter;

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
        mAdapter = new NotificationAdapter(mContext, notificationList);

        rvListNotification.setLayoutManager(new LinearLayoutManager(this));
        rvListNotification.setItemAnimator(new DefaultItemAnimator());
        rvListNotification.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvListNotification.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvListNotification);
        getNotification();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(mContext, HomeActivity.class));
        finish();
    }

    private void getNotification(){
        mApiService.getListNotification().enqueue(new Callback<Example>() {
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof NotificationAdapter.NotificationHolder) {
            // get the removed item name to display it in snack bar
            String notificationId = notificationList.get(viewHolder.getAdapterPosition()).getId();
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
            // remove the item from recycler view
//            mAdapter.removeItem(viewHolder.getAdapterPosition());
        }
    }
}
