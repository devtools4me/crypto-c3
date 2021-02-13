package io.coinapi.rest.model;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@CompiledJson
public class Symbol extends DataVolume {

  private String symbol_id;
  private String exchange_id;
  private String symbol_type;
  private String asset_id_base;
  private String asset_id_quote;
  private Double price;
}