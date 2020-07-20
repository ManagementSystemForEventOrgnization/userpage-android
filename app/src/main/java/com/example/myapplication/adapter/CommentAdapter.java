package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Comment.Result;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    Context mContext;
    List<Result> listComment;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public CommentAdapter(Context context, List<Result> verticalList)
    {
        this.mContext = context;
        listComment = verticalList;
    }
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentHolder(itemView);
    }
    @Override
    public void onBindViewHolder (CommentAdapter.CommentHolder holder, int position){
        Result commentItem = listComment.get(position);
        if (getItemCount()>0)
        {
            if (!commentItem.getUsersComment().getAvatar().equals(""))
            {
                Picasso.get().load(commentItem.getUsersComment().getAvatar()).into(holder.avatar_user);
            }
            else {
                Picasso.get().load("https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png").into(holder.avatar_user);
            }
            Picasso.get().load(commentItem.getUsersComment().getAvatar()).into(holder.avatar_user);
            holder.txt_userName.setText(commentItem.getUsersComment().getFullName());
            holder.txt_contentComment.setText(commentItem.getContent());
            String time = dateFormat.format(commentItem.getCreatedAt());
            holder.txt_timeMyComment.setText(time);
        }

    }
    @Override
    public int getItemCount(){
        return listComment.size();
    }
    public class CommentHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txt_userName) TextView txt_userName;
        @BindView(R.id.txt_contentComment) TextView txt_contentComment;
        @BindView(R.id.avatar_user) CircleImageView avatar_user;
        @BindView(R.id.txt_timeMyComment) TextView txt_timeMyComment;

        public CommentHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
