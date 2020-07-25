package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Notification.Result;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    Context mContext;
    List<Result> notificationItemList;
    public MyAdapterListener onClickListener;
    public NotificationAdapter(Context context, List<Result> notificationList, MyAdapterListener listener) {
        this.mContext = context;
        notificationItemList = notificationList;
        onClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new NotificationHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof NotificationHolder) {
            populateItemRows((NotificationHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }
    @Override
    public int getItemCount() {
        return notificationItemList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return notificationItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
    public void removeItem(int position){

        notificationItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position, notificationItemList.size());
    }
    public class NotificationHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_notification) TextView txt_notification;
        @BindView(R.id.img_notification) ImageView img_notification;
        @BindView(R.id.txt_time) TextView txt_time;
        @BindView(R.id.view_item_notification) FrameLayout view_item_notification;
        public LinearLayout viewBackground;
        public LinearLayout viewForeground;


        public NotificationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);

            view_item_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewForeground.setBackgroundColor(Color.parseColor("#111111"));
                    onClickListener.onclickItemNotification(v, getAdapterPosition());
                }
            });
        }
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
        viewHolder.progressBar.setPressed(true);

    }
    private void populateItemRows(NotificationHolder holder, int position) {
        final Result notificationItem = notificationItemList.get(position);
        PrettyTime p = new PrettyTime();

        String fullname,title, notiContent, type_noti,time;
        String url_icon_noti;
//        notificationList add to new list;
        if (notificationItem.getIsRead()){
            holder.viewForeground.setBackgroundColor(Color.parseColor("#111111"));
        }
        if(notificationItem.getIsDelete()){
            holder.view_item_notification.setVisibility(View.GONE);
        }
        title = notificationItem.getTitle();
        if (title.contains("{sender}")){
            fullname = notificationItem.getUsersSender().getFullName();
            notiContent = title.replace("{sender}",fullname);
        }
        else
        {
            notiContent = title;
        }

        type_noti = notificationItem.getType();
        time = p.format(notificationItem.getCreatedAt());
        //        get suitable icon for each notification
        switch (type_noti){
            case "CREDIT_REFUND_SUCCESS":
                url_icon_noti="https://res.cloudinary.com/eventinyourhand/image/upload/v1592035622/NotifyIcon/credit-card-refund-pngrepo-com_jom3oh.png";
                break;
            case "CREDIT_REFUND_FAILED":
                url_icon_noti="https://res.cloudinary.com/eventinyourhand/image/upload/v1592035614/NotifyIcon/Failed-Payment-Limit-300x300_lxf4q7.png";
                break;
            case "SESSION_CANCEL":
                url_icon_noti="https://res.cloudinary.com/eventinyourhand/image/upload/v1592035650/NotifyIcon/calendar-cancel-512_f9syk9.png";
                break;
            case "EVENT_REJECT":
                url_icon_noti="https://res.cloudinary.com/eventinyourhand/image/upload/v1592035636/NotifyIcon/time_event_calendar_remove_cancel_delete-512_w3oyse.png";
                break;
            case "EVENT_CANCEL":
                url_icon_noti="https://res.cloudinary.com/eventinyourhand/image/upload/v1592035629/NotifyIcon/icons8-cancel_oighvs.png";
                break;
            case "ZALOPAY_REFUND_SUCCESS":
                url_icon_noti="https://res.cloudinary.com/eventinyourhand/image/upload/v1592035663/NotifyIcon/1024x1024bb_vxlebi.png";
                break;
            case "ZALOPAY_REFUND_FAILED":
                url_icon_noti ="https://res.cloudinary.com/eventinyourhand/image/upload/v1592035656/NotifyIcon/unnamed_hgy4pg.png";
                break;
            case "JOINED_EVENT":
                url_icon_noti ="https://res.cloudinary.com/eventinyourhand/image/upload/v1592035642/NotifyIcon/icons8-tear_off_calendar_snrtfo.png";
                break;
            default:
                url_icon_noti ="https://res.cloudinary.com/eventinyourhand/image/upload/v1592467292/NotifyIcon/icons8-notification-64_qa0dfh.png";
        }
        Picasso.get().load(url_icon_noti).into(holder.img_notification);
        holder.txt_notification.setText(notiContent);
        holder.txt_time.setText(time);

    }
    public interface MyAdapterListener {
        void onclickItemNotification(View v, int position);
    }

}
