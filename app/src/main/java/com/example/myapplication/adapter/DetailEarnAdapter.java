package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.EarnedMoney.AmountSession;
import com.example.myapplication.model.ListEvent.Session;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailEarnAdapter extends RecyclerView.Adapter<DetailEarnAdapter.MyView>{
    Context mContext;
    List<Session> sessions ;
    List<AmountSession> amountSessions;
    public DetailEarnAdapter(Context context, List<Session> verticalList1, List<AmountSession> listVertical2)
    {
        this.mContext = context;
        this.sessions = verticalList1;
        this.amountSessions = listVertical2;
    }
    @Override
    public DetailEarnAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session_money, parent, false);
        // return itemView
        return new DetailEarnAdapter.MyView(itemView);
    }
    @Override
    public void onBindViewHolder(DetailEarnAdapter.MyView holder, int position)
    {

        final Session earnItem = sessions.get(position);
        final AmountSession amountSessionItem = amountSessions.get(position);

        holder.txt_sessionNameEarn.setText(earnItem.getName());
        holder.txt_amountSessionEarn.setText(Integer.toString(amountSessionItem.getTotal()));
    }
    public int getItemCount()
    {
        return sessions.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        // Text View
        @BindView(R.id.txt_sessionNameEarn)
        TextView txt_sessionNameEarn;
        @BindView(R.id.txt_amountSessionEarn) TextView txt_amountSessionEarn;

        public MyView(View view)
        {
            super(view);
            // initialise TextView with id
            ButterKnife.bind(this, view);
        }
    }
}
