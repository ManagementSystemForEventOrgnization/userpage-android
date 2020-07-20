package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ListEvent.Session;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SessionEvent extends RecyclerView.Adapter<SessionEvent.MyView> {
    Context mContext;
    private List<Session> listSession;
    public int selected_position = 0;
    public MyAdapterListener onClickListener;
    public SessionEvent(Context context, List<Session> horizontalList, MyAdapterListener listener)
    {
        this.mContext = context;
        this.listSession = horizontalList;
        this.onClickListener = listener;
    }
    @Override
    public SessionEvent.MyView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_session, parent, false);
        // return itemView
        return new MyView(itemView);
    }
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(SessionEvent.MyView holder, int position)
    {
        String blueImage = "https://wallpaperset.com/w/full/f/e/4/98293.jpg";
        String blackImage = "https://wallpaperplay.com/walls/full/0/c/3/85143.jpg";
        final Session sessionItem = listSession.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd\nMMM");
        Date today = sessionItem.getDay();
        String dateTime = dateFormat.format(today);
        holder.txt_date.setText(dateTime);
        Picasso.get().load(selected_position == position? blueImage :  blackImage).into(holder.img_itemDateSession);
    }
    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return listSession.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        // Text View
        @BindView(R.id.txt_date) TextView txt_date;
        @BindView(R.id.img_itemDateSession) CircleImageView img_itemDateSession;
        public MyView(View view)
        {
            super(view);
            // initialise TextView with id
            ButterKnife.bind(this, view);

            txt_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                    // Updating old as well as new positions
                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);
                    onClickListener.itemListSessionDateOnClick(v, getAdapterPosition());
                }
            });
        }
    }
    public interface MyAdapterListener {
        void itemListSessionDateOnClick(View v, int position);
    }
}
