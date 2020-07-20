package com.example.myapplication.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
//import com.example.myapplication.model.ListAllEventResponse;
import com.example.myapplication.model.ListEvent.Result;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryParticipation extends RecyclerView.Adapter<HistoryParticipation.HistoryParticipationHolder> {
    List<Result> listAllEventResponses;
    String price, time1,time2, address;
    String time = "12:00";

    public HistoryParticipation( List<Result> allEventList){
        listAllEventResponses = allEventList;
    }

    @Override
    public HistoryParticipationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        return new HistoryParticipationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryParticipationHolder holder, int position) {
         Result listAllEventitem = listAllEventResponses.get(position);
         if (listAllEventitem.getIsSellTicket()==true){
             price = listAllEventitem.getTicket().getPrice().toString();
         }
         else {
             price = "FREE";
         }

         if (listAllEventitem.getSession().size()==0){
             time ="";
             time1= "";
             address = "";
         }
         else
         {
//             SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
//             Date today  = listAllEventitem.getSession().get(0).getDay();
//             time = dateFormat.format(today).toString();

             if (listAllEventitem.getSession().get(0).getDetail().size()>0){
                 time=listAllEventitem.getSession().get(0).getDetail().get(0).getFrom().substring(0,5);
             }

             //        format date 1
             SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd\nMMM");
             Date today1  = listAllEventitem.getSession().get(0).getDay();
             time1 = dateFormat1.format(today1).toString();
             SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
             time2 = dateFormat2.format(today1);
             address = listAllEventitem.getSession().get(0).getAddress().getLocation();
         }

//        address, price,
//        banner
//        holder.imageView.setColorFilter(Color.BLACK);
        Picasso.get().load(listAllEventitem.getBannerUrl()).into(holder.imageView);

        holder.txt_dateEvent.setText(time1);
        holder.txt_eventName.setText(listAllEventitem.getName());

        holder.txt_address.setText(address);
        holder.txt_price.setText(price);
        holder.txt_category.setText(listAllEventitem.getEventCategory().getName());
        holder.txt_time.setText(time );
    }

    @Override
    public int getItemCount() {
        return listAllEventResponses.size();
    }

    public class HistoryParticipationHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView) ImageView imageView;
        @BindView(R.id.txt_eventName) TextView txt_eventName;
        @BindView(R.id.txt_category) TextView txt_category;
        @BindView(R.id.txt_time) TextView txt_time;
        @BindView(R.id.txt_price) TextView txt_price;
        @BindView(R.id.txt_address) TextView txt_address;
        @BindView(R.id.txt_dateEvent) TextView txt_dateEvent;

        public HistoryParticipationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
