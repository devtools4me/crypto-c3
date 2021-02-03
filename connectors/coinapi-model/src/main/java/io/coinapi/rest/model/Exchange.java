package io.coinapi.rest.model;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@CompiledJson
public class Exchange extends DataVolume {

  private String exchange_id;
  private String website;
  private String name;
}