package io.coinapi.rest.model;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@CompiledJson
public class Asset extends DataVolume {

  private String asset_id;
  private String name;
  private Integer type_is_crypto;
  private Double price_usd;
}