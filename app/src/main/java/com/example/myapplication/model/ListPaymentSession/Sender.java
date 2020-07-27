package com.example.myapplication.model.ListPaymentSession;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sender {
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("fullName")
    @Expose
    private String fullName;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
