package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.ProfileUser;
import com.example.myapplication.model.ListCard.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyView> {
    Context mContext;
    List<Result> listCard;
    String typeCardUrl;
    public MyAdapterListener onClickListener;

    public CardAdapter(Context context, List<Result> listVertical, MyAdapterListener listener){
        this.mContext = context;
        this.listCard = listVertical;
        onClickListener = listener;
    }

    @Override
    public CardAdapter.MyView onCreateViewHolder (ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder (CardAdapter.MyView holder, int position){
        Result itemCard = listCard.get(position);
        if(getItemCount()>0)
        {

            if(itemCard.getBrand().equals("MasterCard"))
            {
                Picasso.get().load("https://i.ibb.co/z2k0hSs/icons8-mastercard-logo-48.png").into(holder.img_typeCard);
            }
            if(itemCard.getBrand().equals("Visa"))
            {
                Picasso.get().load("https://i.ibb.co/tqVG435/icons8-visa-48.png").into(holder.img_typeCard);
            }

            holder.txt_numberCard.setText("**** **** **** "+ itemCard.getLast4());
            holder.txt_expDate.setText("Expired Date:" + itemCard.getExpMonth()+ "/" + itemCard.getExpYear());
        }
    }

    public int getItemCount(){
        return listCard.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        @BindView(R.id.img_typeCard) ImageView img_typeCard;
        @BindView(R.id.txt_numberCard) TextView txt_numberCard;
        @BindView(R.id.txt_expDate) TextView txt_expDate;
        @BindView(R.id.btn_delCard) ImageButton btn_delCard;
        @BindView(R.id.cardItem) LinearLayout cardItem;

        public MyView(View view){
            super(view);
            ButterKnife.bind(this, view);
            btn_delCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.iconImageButtonOnClick(v, getAdapterPosition());
                }
            });
        cardItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Set card default");
                builder.setMessage("Are you sure to set this card default?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        onClickListener.clickYes(v, getAdapterPosition());
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onClickListener.clickNo(v, getAdapterPosition());
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
        }
    }
    public interface MyAdapterListener {
        void iconImageButtonOnClick(View v, int position);
        void clickYes(View v, int position);
        void clickNo(View v, int position);
    }
}
