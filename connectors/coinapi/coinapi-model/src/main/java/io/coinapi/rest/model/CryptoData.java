package io.coinapi.rest.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class CryptoData {

  private LocalDate data_start;
  private LocalDate data_end;
  private OffsetDateTime data_quote_start;
  private OffsetDateTime data_quote_end;
  private OffsetDateTime data_orderbook_start;
  private OffsetDateTime data_orderbook_end;
  private OffsetDateTime data_trade_start;
  private OffsetDateTime data_trade_end;
  private Long data_symbols_count;
}