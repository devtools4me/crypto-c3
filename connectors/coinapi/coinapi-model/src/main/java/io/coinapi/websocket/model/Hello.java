package io.coinapi.websocket.model;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@CompiledJson
public class Hello {

  private String type = "hello";
  private String apikey;
  private Boolean heartbeat = true;
  private String[] subscribe_data_type;
  private String[] subscribe_filter_symbol_id;
  private String[] subscribe_filter_asset_id;
  private String[] subscribe_filter_period_id;
  private String[] subscribe_filter_exchange_id;
  private Integer subscribe_update_limit_ms_quote;
  private Integer subscribe_update_limit_ms_book_snapshot;
}
