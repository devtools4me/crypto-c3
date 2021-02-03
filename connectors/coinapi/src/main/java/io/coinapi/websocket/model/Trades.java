package io.coinapi.websocket.model;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;

import java.time.OffsetDateTime;
import lombok.ToString;

@ToString
@CompiledJson
public class Trades extends MessageBase {

    private String symbolId;
    private Integer sequence;
    private OffsetDateTime timeExchange;
    private OffsetDateTime timeCoinApi;
    private String uuid;
    private Double price;
    private Double size;
    private String takerSide;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getTakerSide() {
        return takerSide;
    }

    @JsonAttribute(name = "taker_side")
    public void setTakerSide(String takerSide) {
        this.takerSide = takerSide;
    }
}
