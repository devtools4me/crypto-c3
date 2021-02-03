package io.coinapi.websocket.model;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;

import java.time.OffsetDateTime;

@CompiledJson
public class OHLCV extends MessageBase {

    private String symbolId;
    private Integer sequence;
    private String periodId;
    private OffsetDateTime timePeriodStart;
    private OffsetDateTime timePeriodEnd;
    private OffsetDateTime timeOpen;
    private OffsetDateTime timeClose;
    private Double priceOpen;
    private Double priceHigh;
    private Double priceLow;
    private Double priceClose;
    private Double volumeTraded;
    private Integer tradesCount;

    public String getSymbolId() {
        return symbolId;
    }

    @JsonAttribute(name = "symbol_id")
    public void setSymbolId(String symbolId) {
        this.symbolId = symbolId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getPeriodId() {
        return periodId;
    }

    @JsonAttribute(name = "period_id")
    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public OffsetDateTime getTimePeriodStart() {
        return timePeriodStart;
    }

    @JsonAttribute(name = "time_period_start")
    public void setTimePeriodStart(OffsetDateTime timePeriodStart) {
        this.timePeriodStart = timePeriodStart;
    }

    public OffsetDateTime getTimePeriodEnd() {
        return timePeriodEnd;
    }

    @JsonAttribute(name = "time_period_end")
    public void setTimePeriodEnd(OffsetDateTime timePeriodEnd) {
        this.timePeriodEnd = timePeriodEnd;
    }

    public OffsetDateTime getTimeOpen() {
        return timeOpen;
    }

    @JsonAttribute(name = "time_open")
    public void setTimeOpen(OffsetDateTime timeOpen) {
        this.timeOpen = timeOpen;
    }

    public OffsetDateTime getTimeClose() {
        return timeClose;
    }

    @JsonAttribute(name = "time_close")
    public void setTimeClose(OffsetDateTime timeClose) {
        this.timeClose = timeClose;
    }

    public Double getPriceOpen() {
        return priceOpen;
    }

    @JsonAttribute(name = "price_open")
    public void setPriceOpen(Double priceOpen) {
        this.priceOpen = priceOpen;
    }

    public Double getPriceHigh() {
        return priceHigh;
    }

    @JsonAttribute(name = "price_high")
    public void setPriceHigh(Double priceHigh) {
        this.priceHigh = priceHigh;
    }

    public Double getPriceLow() {
        return priceLow;
    }

    @JsonAttribute(name = "price_low")
    public void setPriceLow(Double priceLow) {
        this.priceLow = priceLow;
    }

    public Double getPriceClose() {
        return priceClose;
    }

    @JsonAttribute(name = "price_close")
    public void setPriceClose(Double priceClose) {
        this.priceClose = priceClose;
    }

    public Double getVolumeTraded() {
        return volumeTraded;
    }

    @JsonAttribute(name = "volume_traded")
    public void setVolumeTraded(Double volumeTraded) {
        this.volumeTraded = volumeTraded;
    }

    public Integer getTradesCount() {
        return tradesCount;
    }

    @JsonAttribute(name = "trades_count")
    public void setTradesCount(Integer tradesCount) {
        this.tradesCount = tradesCount;
    }
}
