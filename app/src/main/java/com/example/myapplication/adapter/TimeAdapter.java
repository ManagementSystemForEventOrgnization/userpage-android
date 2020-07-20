package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ListEvent.Detail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyView>{
    Context mContext;
    List<Detail> detailSession ;
    public TimeAdapter(Context context, List<Detail> verticalList)
    {
        this.mContext = context;
        this.detailSession = verticalList;
    }
    @Override
    public TimeAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session, parent, false);
        // return itemView
        return new MyView(itemView);
    }
    @Override
    public void onBindViewHolder(TimeAdapter.MyView holder, int position)
    {
        final Detail detailItem = detailSession.get(position);
        holder.txt_timeItem.setText(detailItem.getFrom().substring(0,5) + " - " + detailItem.getTo().substring(0,5));
    }
    public int getItemCount()
    {
        return detailSession.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        // Text View
        @BindView(R.id.txt_timeItem) TextView txt_timeItem;
        public MyView(View view)
        {
            super(view);
            // initialise TextView with id
            ButterKnife.bind(this, view);        }
    }

}
