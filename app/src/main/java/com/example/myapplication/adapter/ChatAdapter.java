package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Chat.Result;


import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context mContext;
    List<Result> listChat;
    String myUserId;
    private static int TYPE_MYCHAT = 1;
    private static int TYPE_ADMINCHAT = 2;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    PrettyTime p = new PrettyTime(Locale.ENGLISH);
    Date date = new Date();
    long diff, diffHours;
    long currentTime = date.getTime();

    public ChatAdapter(Context context, List<Result> listVertical, String userId){
        this.mContext = context;
        this.listChat = listVertical;
        this.myUserId = userId;
//        Collections.reverse(listChat);
    }
    @Override
    public int getItemViewType(int position) {
        if (listChat.get(position).getSender().equals(myUserId)) {
            return TYPE_MYCHAT;

        } else {
            return TYPE_ADMINCHAT;
        }
    }
    @Override
    public int getItemCount() {
        return listChat.size();
    }

    class MyChatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_contentMyChat) TextView txt_contentMyChat;
        @BindView(R.id.txt_timeMyChat) TextView txt_timeMyChat;
        MyChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void setMyChatDetail(Result myChatDetail) {
            txt_contentMyChat.setText(myChatDetail.getContent());
            long myChatTime = myChatDetail.getCreatedAt().getTime();
            diff = currentTime - myChatTime;
            diffHours = diff/(60 * 60 * 1000);
            if (diffHours<24)
            {
                txt_timeMyChat.setText(p.format(myChatDetail.getCreatedAt()));
            }
            else
            {
                txt_timeMyChat.setText(dateFormat.format(myChatDetail.getCreatedAt()));
            }

        }
    }

    class AdminChatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_contentAdminChat) TextView txt_contentAdminChat;
        @BindView(R.id.txt_timeAdminChat) TextView txt_timeAdminChat;
        AdminChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void setAdminChatDetail(Result adminChatDetail) {
            txt_contentAdminChat.setText(adminChatDetail.getContent());
            long adminChatTime = adminChatDetail.getCreatedAt().getTime();
            diff = currentTime - adminChatTime;
            diffHours = diff/(60 * 60 * 1000);

            if (diffHours<24)
            {
                txt_timeAdminChat.setText(p.format(adminChatDetail.getCreatedAt()));
            }
            else
            {
                txt_timeAdminChat.setText(dateFormat.format(adminChatDetail.getCreatedAt()));
            }
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == TYPE_MYCHAT) { // for call layout
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mychat, viewGroup, false);
            return new MyChatViewHolder(view);

        } else { // for email layout
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_admin_chat, viewGroup, false);
            return new AdminChatViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_MYCHAT) {
            ((MyChatViewHolder) viewHolder).setMyChatDetail(listChat.get(position));
        } else {
            ((AdminChatViewHolder) viewHolder).setAdminChatDetail(listChat.get(position));
        }
    }

}
