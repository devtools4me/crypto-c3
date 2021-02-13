package io.coinapi.rest.model;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@CompiledJson
public class AssetIcon {

  private String asset_id;
  private String url;
}