package com.example.myapplication.model.ListEvent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Session {
    @SerializedName("joinNumber")
    @Expose
    private Integer joinNumber;
    @SerializedName("documents")
    @Expose
    private List<Document> documents = null;
    @SerializedName("detail")
    @Expose
    private List<Detail> detail = null;
    @SerializedName("_id")
    @Expose
    private String id;

//    get id session real
    @SerializedName("id")
    @Expose
    private String idSession;

    @SerializedName("day")
    @Expose
    private Date day;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("limitNumber")
    @Expose
    private Integer limitNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isCancel")
    @Expose
    private Boolean isCancel;
//    27/06
//    add new field
    @SerializedName("isRefund")
    @Expose
    private Boolean isRefund;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("isConfirm")
    @Expose
    private Boolean isConfirm;
    @SerializedName("isReject")
    @Expose
    private Boolean isReject;

    public Integer getJoinNumber() {
        return joinNumber;
    }

    public void setJoinNumber(Integer joinNumber) {
        this.joinNumber = joinNumber;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSession(){
        return idSession;
    }
    public void setIdSession(String idSession){
        this.idSession = idSession;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(Integer limitNumber) {
        this.limitNumber = limitNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Boolean isCancel) {
        this.isCancel = isCancel;
    }

//    add new field

    public Boolean getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Boolean isRefund) {
        this.isRefund = isRefund;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(Boolean isConfirm) {
        this.isConfirm = isConfirm;
    }

    public Boolean getIsReject() {
        return isReject;
    }

    public void setIsReject(Boolean isReject) {
        this.isReject = isReject;
    }

}

