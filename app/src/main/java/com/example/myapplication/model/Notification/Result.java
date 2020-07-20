package com.example.myapplication.model.Notification;

import com.example.myapplication.model.ListEvent.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Result {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("receiver")
    @Expose
    private List<String> receiver = null;
    @SerializedName("session")
    @Expose
    private List<String> session = null;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("linkTo")
    @Expose
    private LinkTo linkTo;
    @SerializedName("isRead")
    @Expose
    private Boolean isRead;
    @SerializedName("isDelete")
    @Expose
    private Boolean isDelete;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("users_receiver")
    @Expose
    private User usersReceiver;
    @SerializedName("users_sender")
    @Expose
    private UsersSender usersSender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<String> receiver) {
        this.receiver = receiver;
    }

    public List<String> getSession() {
        return session;
    }

    public void setSession(List<String> session) {
        this.session = session;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LinkTo getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(LinkTo linkTo) {
        this.linkTo = linkTo;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public User getUsersReceiver() {
        return usersReceiver;
    }

    public void setUsersReceiver(User usersReceiver) {
        this.usersReceiver = usersReceiver;
    }

    public UsersSender getUsersSender() {
        return usersSender;
    }

    public void setUsersSender(UsersSender usersSender) {
        this.usersSender = usersSender;
    }
}
