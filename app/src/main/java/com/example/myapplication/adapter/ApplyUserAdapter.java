package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.UserJoinEvent.Result;
import com.example.myapplication.util.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ApplyUserAdapter extends RecyclerView.Adapter <ApplyUserAdapter.MyView>{
    Context mContext;
    List<Result> userJoinEvent ;
//    String typeTab;
    String organizer ="org";
    String user = "user";
    public ApplyAdapteListenner onClickListener;

    public ApplyUserAdapter(Context context, String organizer, String userId, List<Result> verticalList,ApplyAdapteListenner listenner )
    {
        this.mContext = context;
        this.userJoinEvent = verticalList;
        this.onClickListener = listenner;
        this.organizer = organizer;
        this.user = userId;
    }
    @Override
    public ApplyUserAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apply_user, parent, false);
        // return itemView
        return new MyView(itemView);
    }
    @Override
    public void onBindViewHolder(ApplyUserAdapter.MyView holder, int position)
    {
        final Result applyUserItem = userJoinEvent.get(position);
        // Recycler view with the list items
        if (!applyUserItem.getFullName().equals(""))
        {
            holder.txt_fullname_userApply.setText(applyUserItem.getFullName());
        }
        if (!applyUserItem.getAvatar().equals(""))
        {
            Picasso.get().load(applyUserItem.getAvatar()).into(holder.img_user_apply);
        }
        else {
            Picasso.get().load("https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png").into(holder.img_user_apply);
        }
    }
    public int getItemCount()
    {
        return userJoinEvent.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        // Text View
        @BindView(R.id.img_user_apply) CircleImageView img_user_apply;
        @BindView(R.id.txt_fullname_userApply) TextView txt_fullname_userApply;
        @BindView(R.id.btn_reject_user_apply) Button btn_reject_user_apply;

        public MyView(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
//            edit typetab
//            if (typeTab.equals("SELF")){
            if (organizer.equals(user)){
                btn_reject_user_apply.setVisibility(View.VISIBLE);
                btn_reject_user_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.rejectButtonOnClick(v, getAdapterPosition());
                    }
                });
            }
            img_user_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.getProfileUser(v, getAdapterPosition());
                }
            });
            txt_fullname_userApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.getProfileUser(v, getAdapterPosition());
                }
            });
        }
    }
    public interface ApplyAdapteListenner {
        void rejectButtonOnClick(View v, int position);
        void getProfileUser(View v, int position);
    }
}
