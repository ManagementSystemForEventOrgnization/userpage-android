package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.PaymentHistory.Result;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<Result> listPayment;
    String myUserId;
    private static int TYPE_PAYMENT = 1;
    private static int TYPE_REFUND = 2;

//    format date&time
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    PrettyTime p = new PrettyTime(Locale.ENGLISH);
    Date date = new Date();
    long diff, diffHours;
    long currentTime = date.getTime();


    public PaymentAdapter(Context context, List<Result> listVertical, String userId) {
        this.mContext = context;
        this.listPayment = listVertical;
        this.myUserId = userId;
    }

    @Override
    public int getItemViewType(int position) {
        if (listPayment.get(position).getSessionRefunded().size() == 0) {
            return TYPE_PAYMENT;

        } else {
            return TYPE_REFUND;
        }
    }

    @Override
    public int getItemCount() {
        return listPayment.size();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_senderName) TextView txt_senderName;
        @BindView(R.id.txt_amountPayment) TextView txt_amountPayment;
        @BindView(R.id.txt_contentPayment) TextView txt_contentPayment;
        @BindView(R.id.txt_timePayment) TextView txt_timePayment;
        @BindView(R.id.img_cardPayment) ImageView img_cardPayment;
        @BindView(R.id.txt_statuspayment) TextView txt_statuspayment;
        PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setPaymentDetails(Result paymentDetails) {
            String sessionName = "";
            String sessionId, senderName, eventName,time,receiverName;

            sessionId = paymentDetails.getSession().get(0);
            senderName = paymentDetails.getSender().getFullName();
            receiverName = paymentDetails.getReceiver().getFullName();
            eventName = paymentDetails.getEventId().getName();
//            time = p.format(paymentDetails.getCreatedAt());

            long paymentTime = paymentDetails.getCreatedAt().getTime();
            diff = currentTime - paymentTime;
            diffHours = diff/(60 * 60 * 1000);
            time = diffHours<24 ? p.format(paymentDetails.getCreatedAt()) : dateFormat.format(paymentDetails.getCreatedAt());
//            if (diffHours<24)
//            {
//                time = p.format(paymentDetails.getCreatedAt());
//            }
//            else
//            {
//                time = dateFormat.format(paymentDetails.getCreatedAt());
//            }

            for(int i=0; i<paymentDetails.getEventId().getSession().size();i++){
                if(sessionId.equals(paymentDetails.getEventId().getSession().get(i).getIdSession())){
                    sessionName = paymentDetails.getEventId().getSession().get(i).getName();
                }
            }
//            another way
            if (paymentDetails.getSender().getId().equals(myUserId)){
                txt_senderName.setText("You");
                txt_amountPayment.setText("-" + paymentDetails.getAmount()+" VND");
                txt_amountPayment.setTextColor(Color.parseColor("#fa0532"));
                txt_contentPayment.setText("You " + "paid for " + receiverName +" in session " + sessionName + " of event " + eventName);
                txt_timePayment.setText(time);
            }
            else {
                txt_senderName.setText(senderName);
                txt_amountPayment.setText("+" + paymentDetails.getAmount()+" VND");
                txt_amountPayment.setTextColor(Color.parseColor("#08c915"));
                txt_contentPayment.setText(senderName + " sent for you in session " + sessionName + " of event " + eventName);
                txt_timePayment.setText(time);
            }
            if (!paymentDetails.getStatus().equals("PAID")){
                txt_statuspayment.setVisibility(View.VISIBLE);
                txt_statuspayment.setText(paymentDetails.getStatus());
                txt_amountPayment.setTextColor(Color.parseColor("#aba6a6"));
            }
            if (paymentDetails.getPayType().equals("ZALOPAY")){
                Picasso.get().load("https://i.ibb.co/jrBG8yw/zalo-pay.png").into(img_cardPayment);
            }
        }
    }

    class RefundViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_senderName) TextView txt_senderName;
        @BindView(R.id.txt_amountPayment) TextView txt_amountPayment;
        @BindView(R.id.txt_contentPayment) TextView txt_contentPayment;
        @BindView(R.id.txt_timePayment) TextView txt_timePayment;
        @BindView(R.id.txt_contentPaymentRefund) TextView txt_contentPaymentRefund;
        @BindView(R.id.txt_amountPaymentRefund) TextView txt_amountPaymentRefund;
        @BindView(R.id.txt_timePaymentRefund) TextView txt_timePaymentRefund;
        @BindView(R.id.img_cardRefund) ImageView img_cardRefund;
        RefundViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setRefundDetails(Result refundDetails) {
            String sessionName = "";
            String sessionId, senderName, eventName,time, timeUpdate, receiverName;
            sessionId = refundDetails.getSession().get(0);
            senderName = refundDetails.getSender().getFullName();
            receiverName = refundDetails.getReceiver().getFullName();
            eventName = refundDetails.getEventId().getName();

//            time = p.format(refundDetails.getCreatedAt());
//            timeUpdate = p.format(refundDetails.getUpdatedAt());

            long refundTime = refundDetails.getCreatedAt().getTime();
            long updateTime = refundDetails.getUpdatedAt().getTime();
            diff = currentTime - refundTime;
            long diff1 = currentTime - updateTime;

            diffHours = diff/(60 * 60 * 1000);
            long diffHours1 = diff1/(60 * 60 * 1000);

            time = diffHours<24 ? p.format(refundDetails.getCreatedAt()) : dateFormat.format(refundDetails.getCreatedAt());
            timeUpdate = diffHours1<24 ? p.format(refundDetails.getUpdatedAt()) : dateFormat.format(refundDetails.getUpdatedAt());

            for(int i=0; i<refundDetails.getEventId().getSession().size();i++){
                if(sessionId.equals(refundDetails.getEventId().getSession().get(i).getIdSession())){
                    sessionName = refundDetails.getEventId().getSession().get(i).getName();
                }
            }
            if (refundDetails.getSender().getId().equals(myUserId)){
                txt_senderName.setText("You");
                txt_amountPayment.setText("-" + refundDetails.getAmount()+" VND");
                txt_amountPayment.setTextColor(Color.parseColor("#fa0532"));
                txt_contentPayment.setText("You " + "paid for " + receiverName +" in session " + sessionName + " of event " + eventName);
                txt_timePayment.setText(time);
                txt_contentPaymentRefund.setText(receiverName + " refunded for you" + " in session " + sessionName + " of event " + eventName );
                txt_amountPaymentRefund.setText("+" + refundDetails.getAmount()+" VND");
                txt_amountPaymentRefund.setTextColor(Color.parseColor("#08c915"));
                txt_timePaymentRefund.setText(timeUpdate);
            }
            else {
                txt_senderName.setText(senderName);
                txt_amountPayment.setText("+" + refundDetails.getAmount()+" VND");
                txt_amountPayment.setTextColor(Color.parseColor("#08c915"));
                txt_contentPayment.setText(senderName + " sent for you in session " + sessionName + " of event " + eventName);
                txt_timePayment.setText(time);
                txt_contentPaymentRefund.setText("You refunded for " + senderName + " in session " + sessionName + " of event " + eventName );
                txt_amountPaymentRefund.setText("-" + refundDetails.getAmount()+" VND");
                txt_amountPaymentRefund.setTextColor(Color.parseColor("#fa0532"));
                txt_timePaymentRefund.setText(timeUpdate);
            }
//            another way
            if (refundDetails.getPayType().equals("ZALOPAY")){
                Picasso.get().load("https://i.ibb.co/jrBG8yw/zalo-pay.png").into(img_cardRefund);
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == TYPE_PAYMENT) { // for call layout
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_payment, viewGroup, false);
            return new PaymentViewHolder(view);

        } else { // for email layout
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_payment_refund, viewGroup, false);
            return new RefundViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_PAYMENT) {
            ((PaymentViewHolder) viewHolder).setPaymentDetails(listPayment.get(position));

        } else {
            ((RefundViewHolder) viewHolder).setRefundDetails(listPayment.get(position));
        }
    }

}