package com.example.myapplication.model.ListEvent;

import com.example.myapplication.model.AccessToken;
import com.example.myapplication.model.Notification.Bank;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("isReported")
    @Expose
    private Boolean isReported;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("hashPass")
    @Expose
    private String hashPass;
    @SerializedName("dateCreate")
    @Expose
    private String dateCreate;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("TOKEN")
    @Expose
    private String tOKEN;
    @SerializedName("birthday")
    @Expose
    private Date birthday;
    @SerializedName("discription")
    @Expose
    private String discription;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
//    add 22/7
    @SerializedName("bank")
    @Expose
    private Bank bank;
    @SerializedName("accessToken")
    @Expose
    private AccessToken accessToken;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIsReported() {
        return isReported;
    }

    public void setIsReported(Boolean isReported) {
        this.isReported = isReported;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHashPass() {
        return hashPass;
    }

    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getTOKEN() {
        return tOKEN;
    }

    public void setTOKEN(String tOKEN) {
        this.tOKEN = tOKEN;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
//    add 22/7
public Bank getBank() {
    return bank;
}

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
