package com.tpii.KHHR;

import java.util.Arrays;

public class ExchangeRateData {
    private String responseCode;
    private String responseMessage;
    private String authId;
    private String mchName;
    private ExchangeRateItem[] list;

    // Getters and setters

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
    }

    public ExchangeRateItem[] getList() {
        return list;
    }

    public void setList(ExchangeRateItem[] list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ExchangeRateData{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", authId='" + authId + '\'' +
                ", mchName='" + mchName + '\'' +
                ", list=" + Arrays.toString(list) +
                '}';
    }
}

