package com.example.myapplication.model.PaymentHistory;

import com.example.myapplication.model.Notification.Bank;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Receiver {
    @SerializedName("bank")
    @Expose
    private Bank bank;
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
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("orgDes")
    @Expose
    private String orgDes;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("discription")
    @Expose
    private String discription;
    @SerializedName("orgName")
    @Expose
    private String orgName;
    @SerializedName("orgPhone")
    @Expose
    private String orgPhone;
    @SerializedName("orgEmail")
    @Expose
    private String orgEmail;
    @SerializedName("orgWeb")
    @Expose
    private String orgWeb;
    @SerializedName("google_id")
    @Expose
    private String googleId;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("userReport")
    @Expose
    private List<Object> userReport = null;

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

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

    public String getOrgDes() {
        return orgDes;
    }

    public void setOrgDes(String orgDes) {
        this.orgDes = orgDes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgWeb() {
        return orgWeb;
    }

    public void setOrgWeb(String orgWeb) {
        this.orgWeb = orgWeb;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public List<Object> getUserReport() {
        return userReport;
    }

    public void setUserReport(List<Object> userReport) {
        this.userReport = userReport;
    }

}
