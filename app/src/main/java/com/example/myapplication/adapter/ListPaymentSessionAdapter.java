package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ListPaymentSession.Result;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListPaymentSessionAdapter extends RecyclerView.Adapter<ListPaymentSessionAdapter.MyView> {
    Context mContext;
    List<com.example.myapplication.model.ListPaymentSession.Result> listPayment ;
    public PaymentAdapteListenner onClickListener;
    public ListPaymentSessionAdapter(Context context, List<com.example.myapplication.model.ListPaymentSession.Result> verticalList,
                                     PaymentAdapteListenner listener)
    {
        this.mContext = context;
        this.listPayment = verticalList;
        this.onClickListener = listener;
    }
    @Override
    public ListPaymentSessionAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_session, parent, false);
        // return itemView
        return new ListPaymentSessionAdapter.MyView(itemView);
    }
    @Override
    public void onBindViewHolder(ListPaymentSessionAdapter.MyView holder, int position)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final Result paymentItem = listPayment.get(position);
        if(!listPayment.get(position).getSender().getAvatar().equals("")){
            Picasso.get().load(listPayment.get(position).getSender().getAvatar()).into(holder.img_payer);
        }
        holder.txt_namePayer.setText(listPayment.get(position).getSender().getFullName());
        holder.txt_payerMethod.setText(listPayment.get(position).getPayType());
        holder.txt_payerDay.setText(dateFormat.format(listPayment.get(position).getCreatedAt()));
    }
    public int getItemCount()
    {
        return listPayment.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        // Text View
        @BindView(R.id.txt_namePayer) TextView txt_namePayer;
        @BindView(R.id.txt_payerDay) TextView txt_payerDay;
        @BindView(R.id.txt_payerMethod) TextView txt_payerMethod;
        @BindView(R.id.img_payer) ImageView img_payer;

        public MyView(View view)
        {
            super(view);
            // initialise TextView with id
            ButterKnife.bind(this, view);
            img_payer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.getProfileUser(v, getAdapterPosition());
                }
            });
            txt_namePayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.getProfileUser(v, getAdapterPosition());
                }
            });
        }
    }
    public interface PaymentAdapteListenner {
        void getProfileUser(View v, int position);
    }
}
