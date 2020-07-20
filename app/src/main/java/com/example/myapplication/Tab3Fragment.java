package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.activity.DetailEvent;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tab3Fragment extends Fragment {
    BaseApiService mApiService;
    List<Result> listAllEventResponses;
    HistoryParticipation historyParticipation;
    public Tab3Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab3 ,container, false);
        mApiService = UtilsApi.getAPIService(getActivity());
        listAllEventResponses = new ArrayList<>();
        RecyclerView rvListPast = (RecyclerView) rootView.findViewById(R.id.rvListPast);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvListPast.setLayoutManager(mLayoutManager);

//        data
        mApiService.getHistoryParticipate("PAST").enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.isSuccessful())
                {
                    final List<Result> listPastEventItems = response.body().getResult();
                    rvListPast.setAdapter(new HistoryParticipation(listPastEventItems));
                    rvListPast.setItemAnimator(new DefaultItemAnimator());
                    rvListPast.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Result items = listPastEventItems.get(position);
                            String id = items.getId();
                            Intent detailEvent = new Intent(getActivity(), DetailEvent.class);
                            detailEvent.putExtra(Constants.KEY_STATUS, "PAST");
                            detailEvent.putExtra(Constants.KEY_ID, id);
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
        return rootView;
    }
}
