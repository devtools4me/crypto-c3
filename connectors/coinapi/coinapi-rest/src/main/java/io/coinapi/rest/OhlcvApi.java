package io.coinapi.rest;

import feign.Param;
import feign.RequestLine;
import io.coinapi.rest.model.Ohlcv;
import java.util.List;

public interface OhlcvApi {

  @RequestLine("GET /v1/ohlcv/{symbol_id}/latest?"
      + "period_id={period_id}&"
      + "include_empty_items={include_empty_items}&"
      + "limit={limit}")
  List<Ohlcv> latest(@Param("symbol_id") String symbol_id,
      @Param("period_id") String period_id,
      @Param("include_empty_items") Boolean include_empty_items,
      @Param("limit") Integer limit);
}