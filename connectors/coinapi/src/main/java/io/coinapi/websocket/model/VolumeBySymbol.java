package io.coinapi.websocket.model;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;

@CompiledJson
public class VolumeBySymbol {

    private String symbolId;
    private String assetIdBase;
    private String assetIdQuote;
    private Double volumeBase;
    private Double volumeQuote;

    public String getSymbolId() {
        return symbolId;
    }

    @JsonAttribute(name = "symbol_id")
    public void setSymbolId(String symbolId) {
        this.symbolId = symbolId;
    }

    public String getAssetIdBase() {
        return assetIdBase;
    }

    @JsonAttribute(name = "asset_id_base")
    public void setAssetIdBase(String assetIdBase) {
        this.assetIdBase = assetIdBase;
    }

    public String getAssetIdQuote() {
        return assetIdQuote;
    }

    @JsonAttribute(name = "asset_id_quote")
    public void setAssetIdQuote(String assetIdQuote) {
        this.assetIdQuote = assetIdQuote;
    }

    public Double getVolumeBase() {
        return volumeBase;
    }

    @JsonAttribute(name = "volume_base")
    public void setVolumeBase(Double volumeBase) {
        this.volumeBase = volumeBase;
    }

    public Double getVolumeQuote() {
        return volumeQuote;
    }

    @JsonAttribute(name = "volume_quote")
    public void setVolumeQuote(Double volumeQuote) {
        this.volumeQuote = volumeQuote;
    }
}
