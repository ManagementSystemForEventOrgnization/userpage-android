package com.example.myapplication.model.EarnedMoney;

import com.example.myapplication.model.ListEvent.Session;
import com.example.myapplication.model.ListEvent.Ticket;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("urlWeb")
    @Expose
    private String urlWeb;
    @SerializedName("ticket")
    @Expose
    private Ticket ticket;
    @SerializedName("bannerUrl")
    @Expose
    private String bannerUrl;
    @SerializedName("sessionId")
    @Expose
    private List<Session> sessionId = null;
    @SerializedName("SumAmount")
    @Expose
    private Integer sumAmount;
    @SerializedName("amountSession")
    @Expose
    private List<AmountSession> amountSession = null;
    @SerializedName("totalSession")
    @Expose
    private Integer totalSession;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public void setUrlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public List<Session> getSessionId() {
        return sessionId;
    }

    public void setSessionId(List<Session> sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Integer sumAmount) {
        this.sumAmount = sumAmount;
    }

    public List<AmountSession> getAmountSession() {
        return amountSession;
    }

    public void setAmountSession(List<AmountSession> amountSession) {
        this.amountSession = amountSession;
    }

    public Integer getTotalSession() {
        return totalSession;
    }

    public void setTotalSession(Integer totalSession) {
        this.totalSession = totalSession;
    }

}
