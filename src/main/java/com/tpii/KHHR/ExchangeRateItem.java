package com.tpii.KHHR;

import java.math.BigDecimal;

public class ExchangeRateItem {
    private Long id;
    private String currencyCodeFrom;
    private String currencyCodeTo;
    private BigDecimal exchangeRate;
    private String effectiveTime;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCodeFrom() {
        return currencyCodeFrom;
    }

    public void setCurrencyCodeFrom(String currencyCodeFrom) {
        this.currencyCodeFrom = currencyCodeFrom;
    }

    public String getCurrencyCodeTo() {
        return currencyCodeTo;
    }

    public void setCurrencyCodeTo(String currencyCodeTo) {
        this.currencyCodeTo = currencyCodeTo;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    @Override
    public String toString() {
        return "ExchangeRateItem{" +
                "id=" + id +
                ", currencyCodeFrom='" + currencyCodeFrom + '\'' +
                ", currencyCodeTo='" + currencyCodeTo + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", effectiveTime='" + effectiveTime + '\'' +
                '}';
    }
}
