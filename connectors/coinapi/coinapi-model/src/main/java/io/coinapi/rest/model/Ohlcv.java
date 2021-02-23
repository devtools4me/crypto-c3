package io.coinapi.rest.model;

import com.dslplatform.json.CompiledJson;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@CompiledJson
public class Ohlcv {

  OffsetDateTime time_period_start;
  OffsetDateTime time_period_end;
  OffsetDateTime time_open;
  OffsetDateTime time_close;
  Double price_open;
  Double price_high;
  Double price_low;
  Double price_close;
  Double volume_traded;
  Integer trades_count;
}