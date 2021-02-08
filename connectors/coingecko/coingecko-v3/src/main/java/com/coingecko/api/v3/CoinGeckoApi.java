package com.coingecko.api.v3;

import com.coingecko.api.v3.coins.Coin;
import com.coingecko.api.v3.coins.Market;
import feign.Param;
import feign.RequestLine;
import java.util.List;

public interface CoinGeckoApi {

  @RequestLine("GET /coins/list")
  List<Coin> list();

  @RequestLine("GET /coins/markets?vs_currency={vs_currency}&"
      + "ids={ids}&"
      + "order={order}&"
      + "sparkline={sparkline}&"
      + "price_change_percentage={price_change_percentage}&"
      + "page={page}&per_page={per_page}")
  List<Market> markets(@Param("vs_currency") String vsCurrency,
      @Param("ids") String ids,
      @Param("order") String order,
      @Param("sparkline") Boolean sparkline,
      @Param("price_change_percentage") String priceChangePercentage,
      @Param("page") Integer page, @Param("per_page") Integer perPage);
}