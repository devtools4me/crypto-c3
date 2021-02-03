package io.coinapi.rest;

import feign.Param;
import feign.RequestLine;
import io.coinapi.rest.model.Asset;
import io.coinapi.rest.model.AssetIcon;
import io.coinapi.rest.model.Exchange;
import io.coinapi.rest.model.ExchangeIcon;
import io.coinapi.rest.model.Symbol;
import java.util.List;

public interface MetadataApi {

  @RequestLine("GET /v1/exchanges")
  List<Exchange> exchanges();

  @RequestLine("GET /v1/exchanges/{exchange_id}")
  List<Exchange> exchange(@Param("exchange_id") String exchange_id);

  @RequestLine("GET /v1/exchanges?filter_exchange_id={filter_exchange_id}")
  List<Exchange> exchanges(@Param("filter_exchange_id") String filter_exchange_id);

  @RequestLine("GET /v1/exchanges/icons/{iconSize}")
  List<ExchangeIcon> exchangesIcons(@Param("iconSize") Integer iconSize);

  @RequestLine("GET /v1/assets")
  List<Asset> assets();

  @RequestLine("GET /v1/assets/{asset_id}")
  List<Asset> asset(@Param("asset_id") String asset_id);

  @RequestLine("GET /v1/assets?filter_asset_id={filter_asset_id}")
  List<Asset> assets(@Param("filter_asset_id") String filter_asset_id);

  @RequestLine("GET /v1/assets/icons/{iconSize}")
  List<AssetIcon> assetsIcons(@Param("iconSize") Integer iconSize);

  @RequestLine("GET /v1/symbols/{exchange_id}")
  List<Symbol> symbols(@Param("exchange_id") String exchange_id);

  @RequestLine("GET /v1/symbols?"
      + "filter_symbol_id={filter_symbol_id}&"
      + "filter_exchange_id={filter_exchange_id}&"
      + "filter_asset_id={filter_asset_id}")
  List<Symbol> symbols(
      @Param("filter_symbol_id") String filter_symbol_id,
      @Param("filter_exchange_id") String filter_exchange_id,
      @Param("filter_asset_id") String filter_asset_id);
}