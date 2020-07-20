package com.example.myapplication.adapter;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ListEvent.Document;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.MyView> {
    Context mContext;
    List<Document> documentSession ;
    public FileAdapter(Context context, List<Document> verticalList)
    {
        this.mContext = context;
        this.documentSession = verticalList;
    }
    @Override
    public FileAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
        // return itemView
        return new MyView(itemView);
    }
    @Override
    public void onBindViewHolder(FileAdapter.MyView holder, int position)
    {
        final Document documentItem = documentSession.get(position);
        holder.txt_file.setText(documentItem.getUrl());
    }
    public int getItemCount()
    {
        return documentSession.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        // Text View
        @BindView(R.id.txt_file) TextView txt_file;
        public MyView(View view)
        {
            super(view);
            // initialise TextView with id
            ButterKnife.bind(this, view);
            txt_file.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
