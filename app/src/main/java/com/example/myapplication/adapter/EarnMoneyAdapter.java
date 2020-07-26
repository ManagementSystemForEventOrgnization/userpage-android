package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.EarnedMoney.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EarnMoneyAdapter  extends RecyclerView.Adapter<EarnMoneyAdapter.MyView>{
    Context mContext;
    List<Result> earnMoney ;
    public EarnMoneyAdapter(Context context, List<Result> verticalList)
    {
        this.mContext = context;
        this.earnMoney = verticalList;
    }
    @Override
    public EarnMoneyAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_earn_money, parent, false);
        // return itemView
        return new MyView(itemView);
    }
    @Override
    public void onBindViewHolder(EarnMoneyAdapter.MyView holder, int position)
    {
        final Result earnItem = earnMoney.get(position);
        int price = earnItem.getTicket().getDiscount()==0 ? earnItem.getTicket().getPrice() : earnItem.getTicket().getDiscount();
        holder.txt_priceEarn.setText(Integer.toString(price) + " VND");
        holder.txt_amountEarn.setText(Integer.toString(earnItem.getTotalSession()) + " VND");
        holder.txt_eventNameEarn.setText(earnItem.getName());
        Picasso.get().load(earnItem.getBannerUrl()).into(holder.img_bannerEarnMoney);
    }
    public int getItemCount()
    {
        return earnMoney.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        // Text View
        @BindView(R.id.txt_eventNameEarn) TextView txt_eventNameEarn;
        @BindView(R.id.txt_amountEarn) TextView txt_amountEarn;
        @BindView(R.id.txt_priceEarn) TextView txt_priceEarn;
        @BindView(R.id.img_bannerEarnMoney) ImageView img_bannerEarnMoney;

        public MyView(View view)
        {
            super(view);
            // initialise TextView with id
            ButterKnife.bind(this, view);
        }
    }

}
