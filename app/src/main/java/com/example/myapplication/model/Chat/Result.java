package com.example.myapplication.model.Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Result {

    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("receiver")
    @Expose
    private String fullName;
    @SerializedName("fullName")
    @Expose
    private String receiver;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Result(String sender, String receiver, String fullName, String content, Date today) {
        this.sender = sender;
        this.receiver = receiver;
        this.fullName = fullName;
        this.content = content;
        this.createdAt = today;
    }
}
