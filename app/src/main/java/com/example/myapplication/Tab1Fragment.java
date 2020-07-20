package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.activity.DetailEvent;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.adapter.HistoryParticipation;
//import com.example.myapplication.model.BaseListAllRespone;
//import com.example.myapplication.model.ListAllEventResponse;
import com.example.myapplication.model.ListEvent.Example;
import com.example.myapplication.model.ListEvent.Result;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.RecyclerItemClickListener;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tab1Fragment extends Fragment {
    BaseApiService mApiService;
//    List<ListAllEventResponse> listAllEventResponses ;
    HistoryParticipation historyParticipationAdapter;
    public Tab1Fragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Initial
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
//        layout item date event
        mApiService = UtilsApi.getAPIService(getActivity());
//        listAllEventResponses = new ArrayList<>();
        // 1. get a reference to recyclerView
        RecyclerView rvListAll = (RecyclerView) rootView.findViewById(R.id.rvListAll);

        // 2. set layoutManger
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvListAll.setLayoutManager(mLayoutManager);
        // this is data for recycler view

        mApiService.getHistoryParticipate("ALL").enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.isSuccessful())
                {
                    final List<Result> listAllEventItems = response.body().getResult();

                    rvListAll.setAdapter(new HistoryParticipation(listAllEventItems));
//                    historyParticipationAdapter.notifyDataSetChanged();
                    // 5. set item animator to DefaultAnimator
//                    rvListAll.setItemAnimator(new DefaultItemAnimator());
                   rvListAll.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                       @Override
                       public void onItemClick(View view, int position) {
                           Result items = listAllEventItems.get(position);
                           String id = items.getId();
                           Intent detailEvent = new Intent(getActivity(), DetailEvent.class);
                           detailEvent.putExtra(Constants.KEY_ID,id);
                           detailEvent.putExtra(Constants.KEY_STATUS, "ALL");
                           startActivity(detailEvent);
                       }
                   }));
                }
                else{
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Log.e("debug", "onFailure: ERROR 600 > " + jsonError.getJSONObject("error").getString("message") );
                        Toast.makeText(getActivity(), jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
            }
        });

        // 3. create an adapter
//        historyParticipationAdapter = new HistoryParticipation(listAllEventResponses);
//        // 4. set adapter
//        rvListAll.setAdapter(historyParticipationAdapter);
//        // 5. set item animator to DefaultAnimator
//        rvListAll.setItemAnimator(new DefaultItemAnimator());

        return rootView;

    }
//    private void initDataIntent(final List<ListAllEventResponse> listAllEventResponses){
//
//
//    }
}
