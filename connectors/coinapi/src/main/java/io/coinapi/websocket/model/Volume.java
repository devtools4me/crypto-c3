package io.coinapi.websocket.model;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;

import java.time.OffsetDateTime;
import java.util.List;

@CompiledJson
public class Volume extends MessageBase {

    private String periodId;
    private OffsetDateTime timeCoinApi;
    private List<VolumeBySymbol> volumeBySymbol;

    public String getPeriodId() {
        return periodId;
    }

    @JsonAttribute(name = "period_id")
    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public OffsetDateTime getTimeCoinApi() {
        return timeCoinApi;
    }

    @JsonAttribute(name = "time_coinapi")
    public void setTimeCoinApi(OffsetDateTime timeCoinApi) {
        this.timeCoinApi = timeCoinApi;
    }

    public List<VolumeBySymbol> getVolumeBySymbol() {
        return volumeBySymbol;
    }

    @JsonAttribute(name = "volume_by_symbol")
    public void setVolumeBySymbol(List<VolumeBySymbol> volumeBySymbol) {
        this.volumeBySymbol = volumeBySymbol;
    }
}
