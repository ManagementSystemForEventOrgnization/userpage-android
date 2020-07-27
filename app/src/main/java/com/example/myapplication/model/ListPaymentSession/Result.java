package com.example.myapplication.model.ListPaymentSession;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Result {
    @SerializedName("sessionRefunded")
    @Expose
    private List<Object> sessionRefunded = null;
    @SerializedName("session")
    @Expose
    private List<String> session = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("sender")
    @Expose
    private Sender sender;
    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("receiver")
    @Expose
    private String receiver;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("payType")
    @Expose
    private String payType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("zptransId")
    @Expose
    private String zptransId;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<Object> getSessionRefunded() {
        return sessionRefunded;
    }

    public void setSessionRefunded(List<Object> sessionRefunded) {
        this.sessionRefunded = sessionRefunded;
    }

    public List<String> getSession() {
        return session;
    }

    public void setSession(List<String> session) {
        this.session = session;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZptransId() {
        return zptransId;
    }

    public void setZptransId(String zptransId) {
        this.zptransId = zptransId;
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

}
