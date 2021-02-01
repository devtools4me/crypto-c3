package com.crypto.c3.coinapi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class CryptoData {

  private String data_start;
  private String data_end;
  private String data_quote_start;
  private String data_quote_end;
  private String data_orderbook_start;
  private String data_orderbook_end;
  private String data_trade_start;
  private String data_trade_end;
  private Long data_symbols_count;
}