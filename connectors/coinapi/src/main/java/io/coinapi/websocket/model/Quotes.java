package io.coinapi.websocket.model;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;

import java.time.OffsetDateTime;

@CompiledJson
public class Quotes extends MessageBase {

    private String symbolId;
    private Integer sequence;
    private OffsetDateTime timeExchange;
    private OffsetDateTime timeCoinApi;
    private Double askPrice;
    private Double askSize;
    private Double bidPrice;
    private Double bidSize;

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

    public OffsetDateTime getTimeExchange() {
        return timeExchange;
    }

    @JsonAttribute(name = "time_exchange")
    public void setTimeExchange(OffsetDateTime timeExchange) {
        this.timeExchange = timeExchange;
    }

    public OffsetDateTime getTimeCoinApi() {
        return timeCoinApi;
    }

    @JsonAttribute(name = "time_coinapi")
    public void setTimeCoinApi(OffsetDateTime timeCoinApi) {
        this.timeCoinApi = timeCoinApi;
    }

    public Double getAskPrice() {
        return askPrice;
    }

    @JsonAttribute(name = "ask_price")
    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Double getAskSize() {
        return askSize;
    }

    @JsonAttribute(name = "ask_size")
    public void setAskSize(Double askSize) {
        this.askSize = askSize;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    @JsonAttribute(name = "bid_price")
    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Double getBidSize() {
        return bidSize;
    }

    @JsonAttribute(name = "bid_size")
    public void setBidSize(Double bidSize) {
        this.bidSize = bidSize;
    }
}
