package com.example.myapplication.model.PaymentHistory;

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
    private com.example.myapplication.model.ListEvent.Result eventId;
    @SerializedName("receiver")
    @Expose
    private Receiver receiver;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("payType")
    @Expose
    private String payType;
    @SerializedName("cardId")
    @Expose
    private String cardId;
    @SerializedName("chargeId")
    @Expose
    private String chargeId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("updatedAt")
    @Expose
    private Date updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("zptransId")
    @Expose
    private String zptransId;

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

    public com.example.myapplication.model.ListEvent.Result getEventId() {
        return eventId;
    }

    public void setEventId(com.example.myapplication.model.ListEvent.Result eventId) {
        this.eventId = eventId;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getZptransId() {
        return zptransId;
    }

    public void setZptransId(String zptransId) {
        this.zptransId = zptransId;
    }
}
