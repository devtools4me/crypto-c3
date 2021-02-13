package io.coinapi.rest.model;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@CompiledJson
public class ExchangeIcon {

  private String exchange_id;
  private String url;
}